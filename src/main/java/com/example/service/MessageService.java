package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {
        return messageRepository.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageRepository.getMessageById(messageId);
    }

    public Integer deleteMessageById(int messageId) {
        return messageRepository.deleteMessageById(messageId);
    }

    public int updateMessageById(int messageId, String messageText) {
        return messageRepository.updateMessageById(messageId, messageText);
    }

    public List<Message> getAllMessagesFromAccount(int accountId) {
        return messageRepository.getAllMessagesFromAccount(accountId);
    }
}
