package com.jfb.tickets.model;

import com.jfb.tickets.enums.Role;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User extends UserRole {
    private Ticket ticket;

    @Column(name = "name")
    private String name;

    public User() {
        this.role = Role.Client;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
