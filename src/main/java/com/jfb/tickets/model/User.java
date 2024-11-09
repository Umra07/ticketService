package com.jfb.tickets.model;

import com.jfb.tickets.enums.Role;

import java.time.Instant;
import java.util.*;

public class User extends UserRole {
    private boolean status = false;
    private List<Ticket> tickets = new ArrayList<>();

    public User() {
        this.role = Role.Client;
    }

    public List<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTicket(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
