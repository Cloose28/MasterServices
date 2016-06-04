package com.nenazvan.services.db;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 * Class for database connection.
 */
public class dbHelper {
  private static final String DB_NAME = "main.sqlite";
  private String databaseUrl = "jdbc:sqlite:" + DB_NAME;
  private ConnectionSource connectionSource;

  /**
   * Obtaining a database connection
   */
  public ConnectionSource getConnectionSource() throws SQLException {
    connectionSource = new JdbcConnectionSource(databaseUrl);
    return connectionSource;
  }
}
