package model;

import java.util.UUID;

public abstract class BaseModel implements Printable {
    private UUID id;

    public BaseModel() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void print() {
        System.out.println("Print content in console.");
    }
}
