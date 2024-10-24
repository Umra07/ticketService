package com.jfb.tickets.model;

import com.jfb.tickets.enums.Role;

import java.time.Instant;
import java.util.UUID;

public abstract class UserRole extends BaseModel {
    Role role;

    public UserRole(UUID id, Instant creationTime) {
        super(id, creationTime);
    }

    public Role getRole() {
        return this.role;
    }

    public void printRole() {
        System.out.println("My role is " + this.getRole());
    }
}
