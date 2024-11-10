package com.jfb.tickets.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jfb.tickets.enums.TicketType;

import com.jfb.tickets.exceptions.ResourceNotFoundException;

import com.jfb.tickets.model.Ticket;
import com.jfb.tickets.model.BusTicket;

import com.jfb.tickets.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TicketService {

    @Value("classpath:ticketData.txt")
    private Resource resource;

    private final TicketRepository ticketRepository;

    public Ticket getTicketById(UUID ticketId) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);

        return ticketOptional.orElseThrow(() -> new ResourceNotFoundException("Ticket with id:" + ticketId + " was not found", HttpStatus.NOT_FOUND));
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsByUserId(UUID userId) {
        return ticketRepository.findByUserId(userId);
    }

    @Transactional
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Transactional
    public void updateTicketType(UUID ticketId, TicketType ticketType) {
        ticketRepository.updateTicketTypeById(ticketId, ticketType);
    }

    public List<BusTicket> getTicketFromLocalFile() {
        List<BusTicket> tickets = new ArrayList<>();

        try(BufferedReader bufferedTickets = new BufferedReader(new FileReader(resource.getFile()))) {
            String line;

            while((line = bufferedTickets.readLine()) != null) {
                line = line.replace("“", "\"");

                BusTicket busTicket = new ObjectMapper().readValue(line, BusTicket.class);

                tickets.add(busTicket);
            }

        } catch (JsonProcessingException e) {
            System.out.println("File with tickets not found. " + e.getMessage());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return tickets;
    }
}

