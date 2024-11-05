package com.jfb.tickets.dao;

import com.jfb.tickets.model.Ticket;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;
import java.util.List;

import java.time.Instant;
import java.sql.Timestamp;

public class TicketDAO {
    private static final String SELECT_TICKET_BY_ID_QUERY = "SELECT * FROM tickets WHERE id = ?";
    private static final String SELECT_ALL_TICKETS_QUERY = "SELECT * FROM tickets";
    private static final String SELECT_ALL_TICKETS_BY_USER_ID_QUERY = "SELECT * FROM tickets WHERE user_id = ?";
    private static final String INSERT_TICKET_QUERY = "INSERT INTO tickets (id, user_id, ticket_type, creation_time) VALUES (?, ?, ?::ticket_type, ?)";
    private static final String UPDATE_TICKET_TYPE_QUERY = "UPDATE tickets SET ticket_type = ?::ticket_type WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public TicketDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Ticket> ticketRowMapper = (resultSet, rowNum) -> {
        UUID uuid = UUID.fromString(resultSet.getString("id"));
        Instant creationTime = resultSet.getTimestamp("creation_time").toInstant();

        Ticket newTicket = new Ticket();
        newTicket.setId(uuid);
        newTicket.setCreationTime(creationTime);

        return newTicket;
    };

    public Ticket get(UUID id) {
        return jdbcTemplate.queryForObject(SELECT_TICKET_BY_ID_QUERY, ticketRowMapper, id);
    }

    public List<Ticket> getAll() {
        return jdbcTemplate.query(SELECT_ALL_TICKETS_QUERY, ticketRowMapper);
    }

    public List<Ticket> getAllByUserId(UUID userId) {
        return jdbcTemplate.query(SELECT_ALL_TICKETS_BY_USER_ID_QUERY, ticketRowMapper, userId);
    }

    public void save(Ticket ticket, UUID userId) {
        UUID ticketId = ticket.getId();
        String ticketType = "DAY";
        Timestamp creationTime = Timestamp.from(ticket.getCreationTime());

        jdbcTemplate.update(INSERT_TICKET_QUERY, ticketId, userId, ticketType, creationTime);
    }

    public void updateTicketType(UUID ticketId, String ticketType) {
        jdbcTemplate.update(UPDATE_TICKET_TYPE_QUERY, ticketId, ticketType);
    }

}
