package com.jfb.tickets.model;

import java.time.*;
import java.util.UUID;

public abstract class BaseModel implements Printable {
    private UUID id = UUID.randomUUID();
    private Instant creationTime = Instant.now();

    public BaseModel() {}

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public void print() {
        System.out.println("Print content in console.");
    }
}
