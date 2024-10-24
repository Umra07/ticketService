package com.jfb.tickets.model;

import java.time.*;
import java.util.UUID;

public abstract class BaseModel implements Printable {
    private final UUID id;
    private final Instant creationTime;

    public BaseModel(UUID id, Instant creationTime) {
        this.id = id;
        this.creationTime = creationTime;
    }

    public UUID getId() {
        return this.id;
    }

    public Instant getCreationTime() {
        return this.creationTime;
    }

    @Override
    public void print() {
        System.out.println("Print content in console.");
    }
}
