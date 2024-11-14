package com.jfb.tickets.dao;

import com.jfb.tickets.model.User;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDAO {
  private static final String SELECT_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
  private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM users";
  private static final String INSERT_USER_QUERY =
      "INSERT INTO users (id, name, creation_time) VALUES (?, ?, ?)";
  private static final String DELETE_USER_BY_ID_QUERY =
      "DELETE FROM users USING tickets WHERE users.id = ? AND tickets.user_id = users.id";
  private static final String UPDATE_USER_STATUS_QUERY =
      "UPDATE users SET status = ? WHERE users.id = ?";

  private final JdbcTemplate jdbcTemplate;

  public UserDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<User> userRowMapper =
      (resultSet, rowNum) -> {
        UUID uuid = UUID.fromString(resultSet.getString("id"));
        Instant creationTime = resultSet.getTimestamp("creation_time").toInstant();

        User newUser = new User("Johny", false);
        newUser.setId(uuid);
        newUser.setCreationTime(creationTime);

        return newUser;
      };

  public User get(UUID id) {
    return jdbcTemplate.queryForObject(SELECT_USER_BY_ID_QUERY, userRowMapper, id);
  }

  public List<User> getAll() {
    return jdbcTemplate.query(SELECT_ALL_USERS_QUERY, userRowMapper);
  }

  public void save(User user) {
    UUID userId = user.getId();
    String role = "User";
    Timestamp creationTime = Timestamp.from(user.getCreationTime());

    jdbcTemplate.update(INSERT_USER_QUERY, userId, role, creationTime);
  }

  public void delete(UUID userId) {

    jdbcTemplate.update(DELETE_USER_BY_ID_QUERY, userId);
  }

  public void updateUserStatus(UUID userId, boolean status) {
    jdbcTemplate.update(UPDATE_USER_STATUS_QUERY, status, userId);
  }
}
