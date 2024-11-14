package com.jfb.tickets.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
  private String url;
  private String user;
  private String password;

  public DatabaseConnection() {
    loadProperties();
  }

  private void loadProperties() {
    Properties properties = new Properties();

    try (FileInputStream fis =
        new FileInputStream("src/main/java/resources/application.properties")) {
      properties.load(fis);
      url = properties.getProperty("db.url");
      user = properties.getProperty("db.user");
      password = properties.getProperty("db.password");
    } catch (IOException e) {
      throw new RuntimeException("Failed to load database properties", e);
    }
    ;
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }
}
