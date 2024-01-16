package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.exception.MessageException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) throws Exception {
        return messageRepository.findById(messageId)
            .orElseThrow(() -> new MessageException("No message found with id: " + messageId));
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepository.getMessagesByAccountId(accountId);
    }

    public Message createMessage(Message message) {
        try {
            return messageRepository.save(message);
        } catch (Exception e) {
            throw new MessageException("Error creating message: " + e.getMessage());
        }
    }

    public Integer deleteMessage(int messageId) {
        try {
            Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageException("No message found with id: " + messageId));
            messageRepository.delete(message);
            return 1;
        } catch (Exception e) {
            throw new MessageException("Error deleting message: " + e.getMessage());
        }
    }

    public Integer updateMessage(int messageId, String messageText) {
        try {
            int updatedRows = messageRepository.updateMessage(messageId, messageText);
            if (updatedRows == 0) {
                throw new MessageException("No message found with id: " + messageId);
            }
            return updatedRows;
        } catch (Exception e) {
            throw new MessageException("Error updating message: " + e.getMessage());
        }
    }
    // Helper methods
    public boolean doesMessageExist(Integer messageId) {
        return messageRepository.existsById(messageId);
    }

    public boolean isMessageTextBlank(Message message) {
        return message.getMessage_text().trim().isEmpty();
    }

    public boolean isMessageGreaterThan255(Message message) {
        return message.getMessage_text().length() > 255;
    }
}