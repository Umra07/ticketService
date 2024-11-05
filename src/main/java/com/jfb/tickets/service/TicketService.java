package com.jfb.tickets.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfb.tickets.dao.TicketDAO;
import com.jfb.tickets.model.BusTicket;
import com.jfb.tickets.model.Ticket;
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

    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public Ticket getTicket(UUID ticketId) {
        return ticketDAO.get(ticketId);
    }

    public List<Ticket> getAllTickets() {
        return ticketDAO.getAll();
    }

    public List<Ticket> getTicketsByUserId(UUID userId) {
        return ticketDAO.getAllByUserId(userId);
    }

    @Transactional
    public void saveTicket(Ticket ticket, UUID userId) {
        ticketDAO.save(ticket, userId);
    }

    @Transactional

    public void updateTicketType(UUID ticketId, String ticketType) {
        ticketDAO.updateTicketType(ticketId, ticketType);
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

