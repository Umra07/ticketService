package com.jfb.tickets.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfb.tickets.enums.TicketType;
import com.jfb.tickets.model.Ticket;
import com.jfb.tickets.model.BusTicket;
import com.jfb.tickets.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

@Service
public class TicketService {

    @Value("classpath:ticketData.txt")
    Resource resource;

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Optional<Ticket> getTicketById(UUID ticketId) {
        return ticketRepository.findById(ticketId);
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

