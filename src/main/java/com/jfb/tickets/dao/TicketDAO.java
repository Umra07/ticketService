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
    private final DatabaseConnection databaseConnection;

    public TicketDAO() {
        databaseConnection = new DatabaseConnection();
    }

    public Ticket get(UUID id) {
        String sql = "SELECT * FROM tickets WHERE id = ?";

        try(PreparedStatement statement = getPreparedStatement(sql)) {
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
        String sql = "SELECT * FROM tickets";

        try(PreparedStatement statement = getPreparedStatement(sql)) {
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

    public List<Ticket> getAll(UUID userId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE user_id = ?";

        try(PreparedStatement statement = getPreparedStatement(sql)) {
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
        String sql = "INSERT INTO tickets (id, user_id, ticket_type, creation_time) VALUES (?, ?, ?::ticket_type, ?)";

        try (PreparedStatement statement = getPreparedStatement(sql)) {
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
        String sql = "UPDATE tickets SET ticket_type = ?::ticket_type WHERE id = ?";

        try(PreparedStatement statement = getPreparedStatement(sql)) {
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

        return new Ticket(uuid, creationTime);
    }

    private PreparedStatement getPreparedStatement(String sql) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        return connection.prepareStatement(sql);
    }
}
