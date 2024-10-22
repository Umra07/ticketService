package com.jfb.tickets.service;

import com.jfb.tickets.enums.*;
import com.jfb.tickets.model.*;
import com.jfb.tickets.structures.*;

import java.math.*;
import java.time.*;

public class TicketService {
    public static void main(String[] args) {


        Ticket emptyTicket = new Ticket();
        Ticket limitedTicket = new Ticket("TAURON", 378);
        Ticket fullTicket = new Ticket("NARODOWY", 948, Instant.parse("2025-06-23T00:00:00Z"), true, StadiumSector.B, 21, new BigDecimal("123.99"));

        Client client1 = new Client();
        Admin adm = new Admin();

        client1.printRole();
        adm.printRole();
        System.out.println();

        System.out.println(client1.getTicket());
        System.out.println(adm.checkTicket(client1.getTicket()));
        System.out.println();

        client1.setTicket(fullTicket);
        System.out.println(client1.getTicket());
        System.out.println(adm.checkTicket(client1.getTicket()));
        System.out.println();

        Ticket fullTicket1 = new Ticket("NARODOWY", 948, Instant.parse("2025-06-23T00:00:00Z"), true, StadiumSector.B, 21, new BigDecimal("123.99"));
        System.out.println(fullTicket.equals(fullTicket1));
        System.out.println(fullTicket.hashCode() == fullTicket1.hashCode());
        System.out.println();
        System.out.println(fullTicket);

        CustomArrayList<String> arr = new CustomArrayList<>(3);

        arr.add("andersen");
        arr.add("homeworks");
        arr.add("are");
        arr.add("cool!");

        System.out.println(arr.getSize());

        System.out.println(arr.getByIndex(1));
        System.out.println(arr.deleteByIndex(1));

        System.out.println(arr.getSize());

        CustomHashSet<Ticket> customHashSet = new CustomHashSet<>();

        customHashSet.put(fullTicket);
        customHashSet.put(emptyTicket);
        customHashSet.put(limitedTicket);
        customHashSet.iterate();
        customHashSet.put(fullTicket);
        customHashSet.iterate();


        System.out.println(customHashSet.size());
        System.out.println(customHashSet.contains(fullTicket));
        customHashSet.remove(limitedTicket);
        System.out.println(customHashSet.contains(limitedTicket));
        customHashSet.iterate();

    }
}

