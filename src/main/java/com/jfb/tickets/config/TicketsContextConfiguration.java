package com.jfb.tickets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.jfb.tickets.dao.TicketDAO;
import com.jfb.tickets.dao.UserDAO;
import com.jfb.tickets.service.TicketService;
import com.jfb.tickets.service.UserService;

@Configuration
public class TicketsContextConfiguration {
    @Bean
    @Scope("singleton")
    public DatabaseConnection dataSource() {
        return new DatabaseConnection();
    }

    @Bean
    public TicketDAO ticketDAO() {
        return new TicketDAO(dataSource());
    }

    @Bean
    public TicketService ticketService() {
        return new TicketService(ticketDAO());
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO(dataSource());
    }

    @Bean
    public UserService userService() {
        return new UserService(userDAO());
    }
}
