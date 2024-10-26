package com.jfb.tickets.service;

import com.jfb.tickets.DatabaseConnection;
import com.jfb.tickets.dao.TicketDAO;
import com.jfb.tickets.dao.UserDAO;
import com.jfb.tickets.enums.*;
import com.jfb.tickets.model.*;
import com.jfb.tickets.structures.CustomArrayList;
import com.jfb.tickets.structures.CustomHashSet;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TicketService {

    public static void main(String[] args) throws SQLException {
        testOOPTask();
        testCollectionsTask();
        testDAOTask();
    }

    private static void testOOPTask() {
        Ticket emptyTicket = new Ticket();
        Ticket limitedTicket = new Ticket("TAURON", 378);
        Ticket fullTicket = new Ticket("NARODOWY", 948, Instant.parse("2025-06-23T00:00:00Z"), true, StadiumSector.B, 21, new BigDecimal("123.99"));

        System.out.println(emptyTicket);
        System.out.println(limitedTicket);
        System.out.println(fullTicket);
    }

    private static void testCollectionsTask() {
        CustomArrayList <String> words = new CustomArrayList<>(10);

        words.add("Andersen");
        words.add("tasks");
        words.add("are");
        words.add("cool");

        System.out.println(words.getSize());
        System.out.println(words.getByIndex(1));
        System.out.println(words.deleteByIndex(1));
        System.out.println(words.getByIndex(1));
        System.out.println(words.getSize());

        CustomHashSet<Integer> numbers = new CustomHashSet<>();

        numbers.size();

        numbers.put(23);
        numbers.put(46);
        numbers.put(23);
        numbers.put(23124);

        System.out.println(numbers.contains(23));
        numbers.remove(23);
        System.out.println(numbers.contains(23));
        System.out.println(numbers.size());

        numbers.iterate();
    }

    private static void testDAOTask() {
        Ticket emptyTicket = new Ticket();
        Ticket anotherEmptyTicket = new Ticket();

        User client1 = new User();
        User newClient = new User();
        Admin adm = new Admin();

        //User dao tests
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAll();
        userDAO.save(newClient);
        User user = userDAO.get(users.get(0).getId());
        userDAO.delete(users.get(0).getId());
        System.out.println(user.toString());

        //Ticket dao tests
        TicketDAO ticketDAO = new TicketDAO();
        List<Ticket> tickets = ticketDAO.getAll();
        List<Ticket> userTickets = ticketDAO.getAllByUserId(client1.getId());
        Ticket ticket = ticketDAO.get(emptyTicket.getId());
        ticketDAO.save(anotherEmptyTicket, newClient.getId());
        ticketDAO.updateTicketType(anotherEmptyTicket.getId(), "YEAR");
    }

}

