package com.nenazvan.services.db;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class dbHelper {
  private static final String DB_NAME = "main.sqlite";
  private String databaseUrl = "jdbc:sqlite:" + DB_NAME;
  private ConnectionSource connectionSource;

  public ConnectionSource getConnectionSource() {
    try {
      connectionSource = new JdbcConnectionSource(databaseUrl);
      return connectionSource;
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return null;
  }
}
