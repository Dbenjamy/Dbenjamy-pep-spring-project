package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.MessageCreationException;
import com.example.exception.UpdateMessageException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) {
        if (message.getMessageText().length() == 0
                || message.getMessageText().length() > 255
                || !accountRepository.existsById(message.getPostedBy())) {
            throw new MessageCreationException();
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return (List<Message>)messageRepository.findAll();
    }

    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public boolean deleteMessageById(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    public void updateMessageById(int messageId, String messageText) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isEmpty()
                || messageText.length()>255
                || messageText.length()==0) {
            throw new UpdateMessageException();
        }
        Message newMessage = message.get();
        newMessage.setMessageText(messageText);
        messageRepository.save(newMessage);
    }

    public List<Message> getAllMessagesFromAccount(int accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }
}
