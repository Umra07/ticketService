package com.jfb.tickets.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
public abstract class BaseModel implements Printable {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @NonNull
    @Column(name = "creation_time")
    private Instant creationTime = Instant.now();

    @Override
    public void print() {
        System.out.println("Print content in console.");
    }
}
