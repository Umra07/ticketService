package model;

import enums.StadiumSector;

import java.math.BigDecimal;
import java.time.Instant;

public class Ticket extends BaseModel {
    private final Instant creationTime = Instant.now();

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

    // full constructor
    public Ticket(String concertHall, int eventCode, Instant time, boolean isPromo, StadiumSector sector, int backpackWeightLimit, BigDecimal price) {

        /* validators */
        validateEventConcertHall(concertHall);
        validateEventCode(eventCode);
        validateEventBackpackWeightLimit(backpackWeightLimit);

        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.time = time;
        this.isPromo = isPromo;
        this.stadiumSector = sector;
        this.backpackWeightLimit = backpackWeightLimit;
        this.price = price;
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
