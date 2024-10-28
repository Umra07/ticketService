package com.jfb.tickets.dao;

import com.jfb.tickets.DatabaseConnection;
import com.jfb.tickets.model.Ticket;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import java.time.Instant;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Connection;

public class TicketDAO {
    private static final String SELECT_TICKET_BY_ID_QUERY = "SELECT * FROM tickets WHERE id = ?";
    private static final String SELECT_ALL_TICKETS_QUERY = "SELECT * FROM tickets";
    private static final String SELECT_ALL_TICKETS_BY_USER_ID_QUERY = "SELECT * FROM tickets WHERE user_id = ?";
    private static final String INSERT_TICKET_QUERY = "INSERT INTO tickets (id, user_id, ticket_type, creation_time) VALUES (?, ?, ?::ticket_type, ?)";
    private static final String UPDATE_TICKET_TYPE_QUERY = "UPDATE tickets SET ticket_type = ?::ticket_type WHERE id = ?";

    private final DatabaseConnection databaseConnection;

    public TicketDAO() {
        databaseConnection = new DatabaseConnection();
    }

    public Ticket get(UUID id) {

        try(PreparedStatement statement = getPreparedStatement(SELECT_TICKET_BY_ID_QUERY)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createTicket(resultSet);
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<>();

        try(PreparedStatement statement = getPreparedStatement(SELECT_ALL_TICKETS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Ticket newTicket = createTicket(resultSet);

                tickets.add(newTicket);
            }

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return tickets;
    }

    public List<Ticket> getAllByUserId(UUID userId) {
        List<Ticket> tickets = new ArrayList<>();

        try(PreparedStatement statement = getPreparedStatement(SELECT_ALL_TICKETS_BY_USER_ID_QUERY)) {
            statement.setObject(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Ticket newTicket = createTicket(resultSet);

                tickets.add(newTicket);
            }

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return tickets;
    }

    public void save(Ticket ticket, UUID userId) {

        try (PreparedStatement statement = getPreparedStatement(INSERT_TICKET_QUERY)) {
            statement.setObject(1, ticket.getId());
            statement.setObject(2, userId);
            statement.setString(3, "DAY");
            statement.setTimestamp(4, Timestamp.from(ticket.getCreationTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTicketType(UUID ticketId, String ticketType) {

        try(PreparedStatement statement = getPreparedStatement(UPDATE_TICKET_TYPE_QUERY)) {
            statement.setString(1, ticketType);
            statement.setObject(2, ticketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Ticket createTicket(ResultSet resultSet) throws SQLException {
        UUID uuid = UUID.fromString(resultSet.getString("id"));
        Instant creationTime = resultSet.getTimestamp("creation_time").toInstant();

        Ticket newTicket = new Ticket();
        newTicket.setId(uuid);
        newTicket.setCreationTime(creationTime);

        return newTicket;
    }

    private PreparedStatement getPreparedStatement(String sql) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        return connection.prepareStatement(sql);
    }
}
