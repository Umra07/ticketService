package com.jfb.tickets.controller;

import com.jfb.tickets.enums.TicketType;
import com.jfb.tickets.model.Ticket;
import com.jfb.tickets.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable UUID ticketId) {
        Optional<Ticket> optionalTicket = ticketService.getTicketById(ticketId);
        return optionalTicket.map(ticket -> new ResponseEntity<>(ticket, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/userId")
    public List<Ticket> getTicketsByUserId(@PathVariable UUID userId) {
        return ticketService.getTicketsByUserId(userId);
    }

    @GetMapping("")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PostMapping("")
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.saveTicket(ticket);
    }

    @PutMapping("/${ticketId}/ticketType")
    public void updateTicketType(@PathVariable UUID ticketId, @RequestParam TicketType ticketType) {
        ticketService.updateTicketType(ticketId, ticketType);
    }
}
