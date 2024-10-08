package service;

import enums.StadiumSector;
import model.Ticket;

import java.math.BigDecimal;
import java.time.Instant;

public class TicketService {
    public static void main(String[] args) {


        Ticket emptyTicket = new Ticket();
        Ticket limitedTicket = new Ticket("TAURON", 378);
        Ticket fullTicket = new Ticket("NARODOWY", 948, Instant.parse("2025-06-23T00:00:00Z"), true, StadiumSector.B, 21, new BigDecimal("123.99"));

        System.out.println(emptyTicket);
        System.out.println(limitedTicket);
        System.out.println(fullTicket);
    }
}

