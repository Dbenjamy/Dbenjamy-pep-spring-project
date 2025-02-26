package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @PostMapping("register")
    public Account registerAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    @PostMapping("login")
    public Account login(@RequestBody Account account) {
        return accountService.login(account);
    }

    @PostMapping("messages")
    public Message createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @GetMapping("messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("messages/{messageId}")
    public Message getMessageById(@PathVariable int messageId) {
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("messages/{messageId}")
    public Integer deleteMessageById(@PathVariable int messageId) {
        return messageService.deleteMessageById(messageId);
    }

    @PatchMapping("messages/{messageId}")
    public int updateMessageById(@PathVariable int messageId,
            @RequestBody Map<String, String> messageText) {
        return messageService.updateMessageById(
            messageId,
            messageText.get("messageText"));
    }

    @GetMapping("accounts/{accountId}/messages")
    public List<Message> getAllMessagesFromAccount(@PathVariable int accountId) {
        return messageService.getAllMessagesFromAccount(accountId);
    }
}
