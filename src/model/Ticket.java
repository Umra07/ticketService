package model;

import enums.StadiumSector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Ticket {
    private final static Locale plLocale = Locale.of("pl", "PL");
    private final String creationTime;

    private String id;
    private String concertHall;
    private int eventCode;
    private int duration;
    private boolean isPromo;
    private StadiumSector stadiumSector;
    private BigDecimal backpackWeightLimit;
    private String price;

    // default constructor
    public Ticket() {
        creationTime = getFormattedCreationTime();
    }

    // limited constructor
    public Ticket(String eventId, String eventConcertHall, int eventCodeValue) {
        creationTime = getFormattedCreationTime();

        /* validators */
        validateEventId(eventId);
        validateEventConcertHall(eventConcertHall);
        validateEventCode(eventCodeValue);

        id = eventId;
        concertHall = eventConcertHall;
        eventCode = eventCodeValue;
    }

    // full constructor
    public Ticket(String eventId, String eventConcertHall, int eventCodeValue, int eventDuration, boolean isEventPromo, StadiumSector eventSector, float eventBackpackWeightLimit, float eventPrice) {
        creationTime = getFormattedCreationTime();

        /* validators */
        validateEventId(eventId);
        validateEventConcertHall(eventConcertHall);
        validateEventCode(eventCodeValue);
        validateEventBackpackWeightLimit(eventBackpackWeightLimit);

        id = eventId;
        concertHall = eventConcertHall;
        eventCode = eventCodeValue;
        duration = eventDuration;
        isPromo = isEventPromo;
        stadiumSector = eventSector;
        backpackWeightLimit = new BigDecimal(eventBackpackWeightLimit).setScale(3, RoundingMode.HALF_UP);
        price = NumberFormat.getCurrencyInstance(plLocale).format(eventPrice);
    }

    private void validateEventId(String id) {
        if(id.length() > 4) {
            throw new IllegalArgumentException("Invalid id!");
        }
    }

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

    private void validateEventBackpackWeightLimit(float backpackWeight) {
        if(backpackWeight < 0) {
            throw new IllegalArgumentException("Incorrect weight!");
        }
    }

    private String getFormattedCreationTime() {
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM).withLocale(plLocale).withZone(zone);

        return formatter.format(Instant.now());
    }

    public String toString() {
        return String.format("""
                        Ticket Details:
                        --------------------
                        ID: %s
                        Concert Hall: %s
                        Event Code: %d
                        Duration: %d seconds
                        Is Promo: %b
                        Stadium Sector: %s
                        Max Backpack Weight: %.3f kg
                        Price: %s
                        Creation Time: %s""",
                id,
                concertHall,
                eventCode,
                duration,
                isPromo,
                stadiumSector,
                backpackWeightLimit,
                price,
                creationTime);
    }
}
