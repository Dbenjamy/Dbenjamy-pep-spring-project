package com.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;
import com.example.exception.MessageCreationException;
import com.example.exception.UpdateMessageException;
import com.example.util.ConnectionUtil;

@Repository
public class MessageRepository {
    @Autowired
    ConnectionUtil connectionUtil;

    public Message createMessage(Message message) {
        int postedBy = message.getPostedBy();
        String messageText = message.getMessageText();
        if (messageText == ""
                || messageText.length() > 255
                || !userExists(postedBy)) {
            throw new MessageCreationException();
        }
        Long timePostedEpoch = message.getTimePostedEpoch();

        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "INSERT INTO message (postedBy,messageText,timePostedEpoch) VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, postedBy);
            preparedStatement.setString(2, messageText);
            preparedStatement.setLong(3, timePostedEpoch);
            int rowsChanged = preparedStatement.executeUpdate();
            if (rowsChanged == 1) {
                return getMessageByText(messageText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>(){};
        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("messageId"),
                    rs.getInt("postedBy"),
                    rs.getString("messageText"),
                    rs.getLong("timePostedEpoch")
                    ));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public List<Message> getAllMessagesFromAccount(int accountId) {
        List<Message> messages = new ArrayList<>(){};
        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE postedBy = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("messageId"),
                    rs.getInt("postedBy"),
                    rs.getString("messageText"),
                    rs.getLong("timePostedEpoch")
                    ));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public Message getMessageById(int messageId) {
        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE messageId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Message(
                    rs.getInt("messageId"),
                    rs.getInt("postedBy"),
                    rs.getString("messageText"),
                    rs.getLong("timePostedEpoch")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer deleteMessageById(int messageId) {
        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "DELETE FROM message WHERE messageId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            int rowsChanged = preparedStatement.executeUpdate();
            if (rowsChanged == 1) {
                return rowsChanged;
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updateMessageById(int messageId, String messageText) {
        if (messageText.length() == 0
                || messageText.length() > 255
                || !messageExists(messageId)) {
            throw new UpdateMessageException();
        }
        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "UPDATE message SET messageText = ? WHERE messageId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, messageText);
            preparedStatement.setInt(2, messageId);
            int rowsChanged = preparedStatement.executeUpdate();
            if (rowsChanged != 1) {
                throw new UpdateMessageException();
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private Message getMessageByText(String messageText) {
        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE messageText = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, messageText);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Message(
                    rs.getInt("messageId"),
                    rs.getInt("postedBy"),
                    rs.getString("messageText"),
                    rs.getLong("timePostedEpoch")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean userExists(int postedBy) {
        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "SELECT * FROM account WHERE accountId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, postedBy);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean messageExists(int messageId) {
        try {
            Connection connection = connectionUtil.getConnection();
            String sql = "SELECT * FROM message WHERE messageId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
