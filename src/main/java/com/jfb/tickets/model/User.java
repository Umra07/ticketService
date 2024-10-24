package com.jfb.tickets.model;

import com.jfb.tickets.enums.Role;

import java.time.Instant;
import java.util.UUID;

public class User extends UserRole {
    private Ticket ticket;

    public User(UUID id, Instant creationTime) {
        super(id, creationTime);
        this.role = Role.Client;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
