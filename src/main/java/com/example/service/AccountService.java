package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountCreationException;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.UnauthorizedLoginException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        if (account.getUsername().length() == 0
                || account.getPassword().length() < 4) {
            throw new AccountCreationException();
        }
        boolean nameTaken = accountRepository
                .findByUsername(account.getUsername())
                .isPresent();
        if (nameTaken) {
            throw new DuplicateUsernameException(account.getUsername());
        }
        return accountRepository.save(account);
    }

    public Account login(Account account) {
        return accountRepository.findByUsernameAndPassword(
            account.getUsername(),
            account.getPassword())
            .orElseThrow(() -> new UnauthorizedLoginException());
    }

    public List<Account> findAll() {
        return (List<Account>)accountRepository.findAll();
    }
}
