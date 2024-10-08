package service;

import enums.StadiumSector;
import model.Admin;
import model.Client;
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

        Client client1 = new Client();
        Admin adm = new Admin();

        client1.printRole();
        adm.printRole();

        System.out.println(client1.getTicket());
        System.out.println(adm.checkTicket(client1.getTicket()));

        client1.setTicket(fullTicket);
        System.out.println(client1.getTicket());
        System.out.println(adm.checkTicket(client1.getTicket()));
    }
}

