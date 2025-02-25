package com.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.entity.Account;
import com.example.util.ConnectionUtil;

import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

    public AccountRepository() {;
    }
    
    public Account regAccount(Account account) {
        String accountName = account.getUsername();
        String accountPass = account.getPassword();
        
        if (accountName == ""
                || accountPass.length()>3
                || getAccountByName(accountName) != null) {
            return null;
        }

        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "INSERT INTO account (username,password) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountName);
            preparedStatement.setString(1, accountPass);
            int rowsChanged = preparedStatement.executeUpdate();
            if (rowsChanged == 1) {
                return getAccountByName(accountName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountByName(String name) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Account(
                    rs.getInt("accountId"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
