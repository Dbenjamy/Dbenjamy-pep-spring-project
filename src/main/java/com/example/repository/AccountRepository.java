package com.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.entity.Account;
import com.example.exception.AccountCreationException;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.UnauthorizedLoginException;
import com.example.util.ConnectionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {
    @Autowired
    ConnectionUtil connectionUtil;

    public Account registerAccount(Account account) {
        String accountName = account.getUsername();
        String accountPass = account.getPassword();

        if (accountName == "" || accountPass.length() < 4) {
            throw new AccountCreationException();
        } else if (getAccountByName(accountName) != null) {
            throw new DuplicateUsernameException(accountName);
        }

        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "INSERT INTO account (username,password) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountName);
            preparedStatement.setString(2, accountPass);
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
            Connection connection = connectionUtil.getConnection();
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account login(Account account) {
        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Account(
                    rs.getInt("accountId"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            } else {
                throw new UnauthorizedLoginException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // public List<Account> findAll() {
    //     List<Account> accounts = new ArrayList<>();
    //     try {
    //         Connection connection = connectionUtil.getConnection();
    //         String sql = "SELECT * FROM account;";
    //         PreparedStatement preparedStatement = connection.prepareStatement(sql);
    //         ResultSet rs = preparedStatement.executeQuery();
    //         while (rs.next()) {
    //             accounts.add(new Account(
    //                 rs.getInt("accountId"),
    //                 rs.getString("username"),
    //                 rs.getString("password")
    //             ));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return accounts;
    // }

    // public int save(int num) {
    //     return 1;
    // }
}
