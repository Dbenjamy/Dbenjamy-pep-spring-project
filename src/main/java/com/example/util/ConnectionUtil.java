package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;

public class ConnectionUtil {
    @Value("spring.datasource.url")
    private static String url;
    @Value("spring.datasource.username")
    private static String username;
    @Value("spring.datasource.password")
    private static String password;

    private static Connection connection = null;

    public static Connection getConnection(){
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
}
