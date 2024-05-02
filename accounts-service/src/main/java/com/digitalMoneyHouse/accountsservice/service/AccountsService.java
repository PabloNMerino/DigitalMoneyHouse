package com.digitalMoneyHouse.accountsservice.service;

import com.digitalMoneyHouse.accountsservice.entities.Account;
import com.digitalMoneyHouse.accountsservice.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    public void createAccount(Long userId) {
        Account account = new Account(userId);
        accountsRepository.save(account);
    }

}
