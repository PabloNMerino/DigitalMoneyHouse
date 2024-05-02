package com.digitalMoneyHouse.transactionsservice.service;

import com.digitalMoneyHouse.transactionsservice.entities.Account;
import com.digitalMoneyHouse.transactionsservice.entities.Transaction;
import com.digitalMoneyHouse.transactionsservice.repository.FeignAccountRepository;
import com.digitalMoneyHouse.transactionsservice.repository.TransactionRepository;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FeignAccountRepository feignAccountRepository;

    public Transaction createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
        return transaction;
    }

    public boolean isAccountSaved(Long userId) {
        Optional<Account> accountOptional = Optional.ofNullable(feignAccountRepository.getAccountById(userId));
        return accountOptional.isPresent();
    }

    public Account getAccount(Long userId) {
        Optional<Account> accountOptional = Optional.ofNullable(feignAccountRepository.getAccountById(userId));
        if(accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new BadRequestException("User ID: " + userId + " do not exist");
        }
    }
}
