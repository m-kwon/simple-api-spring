package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.AccountException;
import com.example.exception.MessageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping()
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        super();
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.registerAccount(account);
            return new ResponseEntity<>(registeredAccount, HttpStatus.OK);
        } catch (AccountException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        try {
            Account loggedInAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
            if (loggedInAccount != null) {
                return new ResponseEntity<Account>(loggedInAccount, HttpStatus.OK);
            } else {
                throw new AccountException("Unauthorized access");
            }
        } catch (AccountException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            if (!doesAccountExist(message.getPosted_by()) || isMessageTextBlank(message) || isMessageGreaterThan255(message)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Message newMessage = messageService.createMessage(message);
            return new ResponseEntity<>(newMessage, HttpStatus.OK);
        } catch (MessageException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/messages")
    public List<Message> getMessages() {
        try {
            return messageService.getMessages();
        } catch (MessageException e) {
            return Collections.emptyList();
        }
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        try {
            return new ResponseEntity<Message>(messageService.getMessageById(message_id), HttpStatus.OK);
        } catch (MessageException e) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int message_id) {
        try {
            String deletedMessage = messageService.deleteMessage(message_id).toString();
            return new ResponseEntity<String>(deletedMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<String> updateMessage(@PathVariable int message_id, @RequestBody Message message) {
        try {
            if (!doesMessageExist(message_id) || isMessageTextBlank(message) || isMessageGreaterThan255(message)) {
                throw new MessageException("Invalid request parameters");
            }

            String updatedMessage = messageService.updateMessage(message_id, message.getMessage_text()).toString();
            return new ResponseEntity<String>(updatedMessage, HttpStatus.OK);
        } catch (MessageException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("accounts/{account_id}/messages")
    public List<Message> getAllMessagesByAccountId(@PathVariable int account_id) {
        try {
            return messageService.getMessagesByAccountId(account_id);
        } catch (MessageException e) {
            return Collections.emptyList();
        }
    }

    // Helper methods
    private boolean doesAccountExist(Integer accountId) {
        return accountService.doesAccountExist(accountId);
    }

    private boolean isUsernameValid(String username) {
        return accountService.isUsernameValid(username);
    }

    private boolean isPasswordValid(String password) {
        return accountService.isPasswordValid(password);
    }

    private boolean doesMessageExist(Integer messageId) {
        return messageService.doesMessageExist(messageId);
    }

    private boolean isMessageTextBlank(Message message) {
        return messageService.isMessageTextBlank(message);
    }

    private boolean isMessageGreaterThan255(Message message) {
        return messageService.isMessageGreaterThan255(message);
    }
}