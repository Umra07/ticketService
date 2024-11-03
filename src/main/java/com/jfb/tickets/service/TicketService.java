package com.jfb.tickets.service;

import com.jfb.tickets.dao.TicketDAO;
import com.jfb.tickets.model.Ticket;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TicketService {
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

    public void saveTicket(Ticket ticket, UUID userId) {
        ticketDAO.save(ticket, userId);
    }

    public void updateTicketType(UUID ticketId, String ticketType) {
        ticketDAO.updateTicketType(ticketId, ticketType);
    }

}

