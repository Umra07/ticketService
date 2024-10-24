package com.jfb.tickets.model;

import com.jfb.tickets.enums.Role;

public class Client extends UserRole {
    private Ticket ticket;

    public Client() {
        this.role = Role.Client;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
