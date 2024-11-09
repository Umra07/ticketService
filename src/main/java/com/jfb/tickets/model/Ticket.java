package com.jfb.tickets.model;

import com.jfb.tickets.enums.StadiumSector;
import com.jfb.tickets.enums.TicketType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Ticket extends BaseModel {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "ticket_type")
    private TicketType ticketType;

    private String concertHall;
    private int eventCode;
    private Instant time;
    private boolean isPromo;
    private StadiumSector stadiumSector;
    private int backpackWeightLimit;
    private BigDecimal price = BigDecimal.ZERO;

    // default constructor
    public Ticket() {
    }

    // limited constructor
    public Ticket(String concertHall, int eventCode) {

        /* validators */
        validateEventConcertHall(concertHall);
        validateEventCode(eventCode);

        this.concertHall = concertHall;
        this.eventCode = eventCode;
    }

    public Ticket(UUID id, Instant creationTime, UUID userId, TicketType ticketType) {

        setId(id);
        setCreationTime(creationTime);
        this.userId = userId;
        this.ticketType = ticketType;
    }

    // full constructor
    public Ticket(UUID userId, String concertHall, int eventCode, Instant time, boolean isPromo, StadiumSector sector, int backpackWeightLimit, BigDecimal price) {

        /* validators */
        validateEventConcertHall(concertHall);
        validateEventCode(eventCode);
        validateEventBackpackWeightLimit(backpackWeightLimit);

        this.userId = userId;
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.time = time;
        this.isPromo = isPromo;
        this.stadiumSector = sector;
        this.backpackWeightLimit = backpackWeightLimit;
        this.price = price;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getConcertHall() {
        return this.concertHall;
    }

    public int getEventCode() {
        return this.eventCode;
    }

    public Instant getTime() {
        return this.time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public boolean getIsPromo() {
        return this.isPromo;
    }

    public StadiumSector getStadiumSector() {
        return this.stadiumSector;
    }

    public void setStadiumSector(StadiumSector stadiumSector) {
        this.stadiumSector = stadiumSector;
    }

    public int getBackpackWeightLimit() {
        return this.backpackWeightLimit;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        Ticket ticket = (Ticket) o;
        return concertHall.equals(ticket.getConcertHall()) &&
                eventCode == ticket.getEventCode() &&
                time.equals(ticket.getTime()) &&
                isPromo == ticket.getIsPromo() &&
                stadiumSector == ticket.getStadiumSector() &&
                backpackWeightLimit == ticket.getBackpackWeightLimit() &&
                price.equals(ticket.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(concertHall, eventCode, time, isPromo, stadiumSector, backpackWeightLimit, price);
    }

    @Override
    public String toString() {
        return "Ticket{\n" +
                "creationTime: " + this.getCreationTime() + "," +
                "\nconcertHall: " + this.concertHall + "," +
                "\neventCode: " + this.eventCode + "," +
                "\ntime: " + this.time + "," +
                "\nisPromo: " + this.isPromo + "," +
                "\nstadiumSector: " + this.stadiumSector + "," +
                "\nbackpackWeightLimit: " + this.backpackWeightLimit + "," +
                "\nprice: " + this.price +
                "\n}";
    }

    public void shared(String phone) {
        System.out.println("Shared by " + phone);
    }

    public void shared(String phone, String email) {
        System.out.println("Shared by " + phone + " and " + email);
    }

    // methods for validating fields
    private void validateEventConcertHall(String concertHall) {
        if(concertHall.length() > 10) {
            throw new IllegalArgumentException("Incorrect concert hall!");
        }
    }

    private void validateEventCode(int eventCode) {
        if(eventCode < 100 || eventCode > 999) {
            throw new IllegalArgumentException("Unknown event!");
        }
    }

    private void validateEventBackpackWeightLimit(int backpackWeight) {
        if(backpackWeight < 0) {
            throw new IllegalArgumentException("Incorrect weight!");
        }
    }

}
