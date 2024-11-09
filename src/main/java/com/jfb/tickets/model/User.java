package com.jfb.tickets.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.*;

@Entity
public class User extends BaseModel {

    @Column(name = "status")
    private boolean status = false;
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

    public User() {
    }

    public User(UUID id, Instant creationTime, boolean status, String name, List<Ticket> tickets) {
        setId(id);
        setCreationTime(creationTime);
        this.status = status;
        this.name = name;
        this.tickets = tickets;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
