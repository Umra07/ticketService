package com.jfb.tickets.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jfb.tickets.dao.TicketDAO;
import com.jfb.tickets.dao.UserDAO;
import com.jfb.tickets.service.TicketService;
import com.jfb.tickets.service.UserService;

import javax.sql.DataSource;

@Configuration
public class TicketsContextConfiguration {
    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("my_secure_password");
        return dataSource;
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
