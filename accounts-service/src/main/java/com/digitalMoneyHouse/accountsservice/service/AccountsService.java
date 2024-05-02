package com.digitalMoneyHouse.accountsservice.service;

import com.digitalMoneyHouse.accountsservice.entities.Account;
import com.digitalMoneyHouse.accountsservice.entities.AccountInformation;
import com.digitalMoneyHouse.accountsservice.entities.User;
import com.digitalMoneyHouse.accountsservice.exceptions.BadRequestException;
import com.digitalMoneyHouse.accountsservice.repository.AccountsRepository;
import com.digitalMoneyHouse.accountsservice.repository.FeignUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private FeignUserRepository feignUserRepository;

    public void createAccount(Long userId) {
        Account account = new Account(userId);
        accountsRepository.save(account);
    }

    public AccountInformation getAccountInformation(Long userId) throws BadRequestException {
        Optional<Account> accountOptional = accountsRepository.findByUserId(userId);
        if(accountOptional.isPresent()) {
            Account accountFound = accountOptional.get();
            User feignUser = feignUserRepository.getUserById(userId);
            return new AccountInformation(accountFound.getBalance(), feignUser.getAlias(), feignUser.getCvu());
        } else {
            throw new BadRequestException("Account not found");
        }
    }
}
