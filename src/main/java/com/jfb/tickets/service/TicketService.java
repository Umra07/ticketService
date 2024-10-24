package com.jfb.tickets.service;

import com.jfb.tickets.DatabaseConnection;
import com.jfb.tickets.dao.TicketDAO;
import com.jfb.tickets.dao.UserDAO;
import com.jfb.tickets.enums.*;
import com.jfb.tickets.model.*;
import com.jfb.tickets.structures.CustomArrayList;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TicketService {

    public static void main(String[] args) throws SQLException {

//
        Ticket emptyTicket = new Ticket(UUID.randomUUID(), Instant.now());
//        Ticket limitedTicket = new Ticket("TAURON", 378);
//        Ticket fullTicket = new Ticket("NARODOWY", 948, Instant.parse("2025-06-23T00:00:00Z"), true, StadiumSector.B, 21, new BigDecimal("123.99"));
//
        User client1 = new User(UUID.randomUUID(), Instant.now());
//        Admin adm = new Admin();
//
//        System.out.println(emptyTicket.getCreationTime());
//        System.out.println(client1.getCreationTime());
//        System.out.println(adm.getCreationTime());

        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAll();
        User user = userDAO.get(users.get(0).getId());
        userDAO.delete(users.get(0).getId());
        System.out.println(user.toString());
    }


}

