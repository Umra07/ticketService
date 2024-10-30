package com.jfb.tickets.model;

import jakarta.persistence.*;

import java.time.*;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseModel implements Printable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "creation_time")
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
