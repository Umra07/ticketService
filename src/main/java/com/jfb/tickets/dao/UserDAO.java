package com.jfb.tickets.dao;

import com.jfb.tickets.DatabaseConnection;
import com.jfb.tickets.model.User;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import java.time.Instant;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class UserDAO {
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT_USER_QUERY = "INSERT INTO users (id, name, creation_time) VALUES (?, ?, ?)";
    private static final String DELETE_USER_BY_ID_QUERY = "DELETE FROM users USING tickets WHERE users.id = ? AND tickets.user_id = users.id";

    private final DatabaseConnection databaseConnection;

    public UserDAO() {
        databaseConnection = new DatabaseConnection();
    }

    public User get(UUID id) {
        try(PreparedStatement statement = getPreparedStatement(SELECT_USER_BY_ID_QUERY)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createUser(resultSet);
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try(PreparedStatement statement = getPreparedStatement(SELECT_ALL_USERS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                User newUser = createUser(resultSet);

                users.add(newUser);
            }

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void save(User user) {

        try (PreparedStatement statement = getPreparedStatement(INSERT_USER_QUERY)) {
            statement.setObject(1, user.getId());
            statement.setString(2, user.getRole().toString());
            statement.setTimestamp(3, Timestamp.from(user.getCreationTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(UUID userId) {

        try (PreparedStatement statement = getPreparedStatement(DELETE_USER_BY_ID_QUERY)) {
            statement.setObject(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        UUID uuid = UUID.fromString(resultSet.getString("id"));
        Instant creationTime = resultSet.getTimestamp("creation_time").toInstant();

        User newUser = new User();
        newUser.setId(uuid);
        newUser.setCreationTime(creationTime);

        return newUser;
    }

    private PreparedStatement getPreparedStatement(String sql) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        return connection.prepareStatement(sql);
    }
}
