package com.jfb.tickets.controller;

import com.jfb.tickets.enums.TicketType;
import com.jfb.tickets.model.Ticket;
import com.jfb.tickets.service.TicketService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @GetMapping("/{ticketId}")
  public ResponseEntity<Ticket> getTicketById(@PathVariable UUID ticketId) {
    Ticket ticket = ticketService.getTicketById(ticketId);
    return new ResponseEntity<>(ticket, HttpStatus.OK);
  }

  @GetMapping("/ticket/{userId}")
  public List<Ticket> getTicketsByUserId(@PathVariable UUID userId) {
    return ticketService.getTicketsByUserId(userId);
  }

  @GetMapping
  public List<Ticket> getAllTickets() {
    return ticketService.getAllTickets();
  }

  @PostMapping
  public Ticket createTicket(@RequestBody Ticket ticket) {
    return ticketService.saveTicket(ticket);
  }

  @PutMapping("/{ticketId}")
  public void updateTicketType(@PathVariable UUID ticketId, @RequestBody TicketType ticketType) {
    ticketService.updateTicketType(ticketId, ticketType);
  }
}
