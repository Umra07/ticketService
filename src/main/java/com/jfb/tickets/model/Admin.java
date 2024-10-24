package com.jfb.tickets.model;

import com.jfb.tickets.enums.Role;

import java.time.Instant;
import java.util.UUID;

public class Admin extends UserRole {
    public Admin(UUID id, Instant creationTime) {
        super(id, creationTime);
        this.role = Role.Admin;
    }

    public boolean checkTicket(Ticket ticket) {
        return ticket != null;
    }
}
