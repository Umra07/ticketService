package com.jfb.tickets;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jfb.tickets.config.TicketsContextConfiguration;
import com.jfb.tickets.dao.TicketDAO;
import com.jfb.tickets.dao.UserDAO;
import com.jfb.tickets.model.*;
import com.jfb.tickets.service.TicketService;
import com.jfb.tickets.service.UserService;
import com.jfb.tickets.structures.CustomArrayList;
import com.jfb.tickets.structures.CustomHashSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.SQLOutput;
import java.util.*;

public class Main {

    public static void main(String[] args) {

//        testOOPTask();
//        testCollectionsTask();
//        testDAOTask();
//        testSpringTask();
        testTransactionAndResourcesTask();

    }

    private static void testTransactionAndResourcesTask() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(TicketsContextConfiguration.class);

        UserService userService = ctx.getBean(UserService.class);

        User user = new User();
        Ticket ticket = new Ticket();

        System.out.println("Transaction open? : " + TransactionSynchronizationManager.isActualTransactionActive());
        userService.saveUser(user);
        userService.updateUserStatusAndCreateTicket(user.getId(), false, ticket);
        System.out.println("Transaction open? : " + TransactionSynchronizationManager.isActualTransactionActive());

        TicketService ticketService = ctx.getBean(TicketService.class);

        List<BusTicket> tickets = ticketService.getTicketFromLocalFile();

        System.out.println(tickets);
    }

    private static void testSpringTask() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(TicketsContextConfiguration.class);

        UserService userService = ctx.getBean(UserService.class);
        TicketService ticketService = ctx.getBean(TicketService.class);

        User user = new User();
        UUID userId = user.getId();

        userService.saveUser(user);
        System.out.println(userService.getUsers());
        System.out.println(userService.getUser(userId));
        userService.deleteUser(userId);
        System.out.println(userService.getUser(userId));

        Ticket empty = new Ticket();
        UUID emptyId = empty.getId();

        ticketService.saveTicket(empty, userId);
        System.out.println(ticketService.getAllTickets());
        System.out.println(ticketService.getTicketsByUserId(userId));
        System.out.println(ticketService.getTicket(emptyId));
        ticketService.updateTicketType(emptyId,"YEAR");
        System.out.println(ticketService.getTicket(emptyId));

        System.out.println(userService);
        System.out.println(ticketService);
    }

    private static void testOOPTask() {
        Ticket emptyTicket = new Ticket();
//        Ticket limitedTicket = new Ticket("TAURON", 378);
//        Ticket fullTicket = new Ticket("NARODOWY", 948, Instant.parse("2025-06-23T00:00:00Z"), true, StadiumSector.B, 21, new BigDecimal("123.99"));

        System.out.println(emptyTicket);
//        System.out.println(limitedTicket);
//        System.out.println(fullTicket);
    }

    private static void testCollectionsTask() {
        CustomArrayList<String> words = new CustomArrayList<>(10);

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

    private static void testDAOTask(JdbcTemplate jdbcTemplate) {
        Ticket emptyTicket = new Ticket();
        Ticket anotherEmptyTicket = new Ticket();

        User client1 = new User();
        User newClient = new User();
        Admin adm = new Admin();

        //User dao tests
        UserDAO userDAO = new UserDAO(jdbcTemplate);
        List<User> users = userDAO.getAll();
        userDAO.save(newClient);
        User user = userDAO.get(users.get(0).getId());
        userDAO.delete(users.get(0).getId());
        System.out.println(user.toString());

        //Ticket dao tests
        TicketDAO ticketDAO = new TicketDAO(jdbcTemplate);
        List<Ticket> tickets = ticketDAO.getAll();
        List<Ticket> userTickets = ticketDAO.getAllByUserId(client1.getId());
        Ticket ticket = ticketDAO.get(emptyTicket.getId());
        ticketDAO.save(anotherEmptyTicket, newClient.getId());
        ticketDAO.updateTicketType(anotherEmptyTicket.getId(), "YEAR");
    }
}
