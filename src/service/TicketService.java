package service;

import enums.StadiumSector;
import model.Admin;
import model.Client;
import model.Ticket;
import structures.CustomArrayList;
import structures.CustomHashSet;

import java.math.BigDecimal;
import java.time.Instant;

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

        CustomHashSet<Integer> customHashSet = new CustomHashSet<>();

        customHashSet.add(1);
        customHashSet.add(2);
        System.out.println(customHashSet.add(3));
        System.out.println(customHashSet.add(3));

        System.out.println(customHashSet.size());
        System.out.println(customHashSet.contains(2));
        System.out.println(customHashSet.remove(2));
        System.out.println(customHashSet.contains(2));
        customHashSet.iterate();
    }
}

