package com.jfb.tickets.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import com.jfb.tickets.dao.TicketDAO;
import com.jfb.tickets.dao.UserDAO;
import com.jfb.tickets.service.TicketService;
import com.jfb.tickets.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class TicketsContextConfiguration {

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public JdbcTransactionManager transactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

    @Bean
    public TicketDAO ticketDAO() {
        return new TicketDAO(jdbcTemplate());
    }

    @Bean
    public TicketService ticketService() {
        return new TicketService(ticketDAO());
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO(jdbcTemplate());
    }

    @Bean
    public UserService userService() {
        return new UserService(userDAO(), ticketDAO());
    }
}
