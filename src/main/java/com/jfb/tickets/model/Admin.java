package main.java.com.jfb.tickets.model;

import main.java.com.jfb.tickets.enums.Role;

public class Admin extends UserRole {
    public Admin() {
        this.role = Role.Admin;
    }

    public boolean checkTicket(Ticket ticket) {
        return ticket != null;
    }
}
