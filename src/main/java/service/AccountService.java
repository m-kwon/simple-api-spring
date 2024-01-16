package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.exception.AccountException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new AccountException("Error registering account: " + e.getMessage());
        }
    }

    public Account loginAccount(String username, String password) {
        try {
            return accountRepository.loginAccount(username, password);
        } catch (Exception e) {
            throw new AccountException("Error logging in: " + e.getMessage());
        }
    }

    // Helper methods
    public boolean doesAccountExist(Integer userId) {
        return accountRepository.existsById(userId);
    }

    public boolean isUsernameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }

    public boolean isPasswordValid(String password) {
        return password != null && password.length() >= 4;
    }
}