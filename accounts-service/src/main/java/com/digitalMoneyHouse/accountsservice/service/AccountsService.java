package com.digitalMoneyHouse.accountsservice.service;

import com.digitalMoneyHouse.accountsservice.entities.*;
import com.digitalMoneyHouse.accountsservice.exceptions.BadRequestException;
import com.digitalMoneyHouse.accountsservice.exceptions.ResourceNotFoundException;
import com.digitalMoneyHouse.accountsservice.repository.AccountsRepository;
import com.digitalMoneyHouse.accountsservice.repository.FeignCardRepository;
import com.digitalMoneyHouse.accountsservice.repository.FeignTransactionRepository;
import com.digitalMoneyHouse.accountsservice.repository.FeignUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private FeignUserRepository feignUserRepository;

    @Autowired
    private FeignTransactionRepository feignTransactionRepository;

    @Autowired
    private FeignCardRepository feignCardRepository;

    public void createAccount(Long userId) {
        Account account = new Account(userId);
        accountsRepository.save(account);
    }

    public AccountInformation getAccountInformation(Long userId) throws BadRequestException {
        Optional<Account> accountOptional = accountsRepository.findByUserId(userId);
        if(accountOptional.isPresent()) {
            Account accountFound = accountOptional.get();
            User feignUser = feignUserRepository.getUserById(userId);
            return new AccountInformation(accountFound.getId(), accountFound.getUserId() ,accountFound.getBalance(), feignUser.getAlias(), feignUser.getCvu());
        } else {
            throw new BadRequestException("Account not found");
        }
    }

    public Account updateAccount(Account account) {
        return accountsRepository.save(account);
    }

    public List<Transaction> getLastFiveTransactions(Long userId) throws ResourceNotFoundException {
         List<Transaction> transactions = feignTransactionRepository.getLastFiveTransactions(userId);
         if(transactions.isEmpty()){
             throw new ResourceNotFoundException("No transactions found");
         }
         return transactions;
    }

    public Card registerCard(CardRequest card) throws ResourceNotFoundException {
        Long userId = feignUserRepository.getUserId();
        Optional<Account> accountOptional = accountsRepository.findByUserId(userId);
        if(accountOptional.isEmpty()) {
            throw new ResourceNotFoundException("Account not found");
        } else {
            Account account = accountOptional.get();
            Card newCard = new Card(account.getId(), card.getHolder(), card.getNumber(), card.getExpirationDate(), card.getCvv());
            return feignCardRepository.registerCard(newCard);
        }
    }

    public List<Card> getAllCards() throws ResourceNotFoundException {
        Long userId = feignUserRepository.getUserId();
        Optional<Account> accountOptional = accountsRepository.findByUserId(userId);
        if(accountOptional.isEmpty()) {
            throw new ResourceNotFoundException("Account not found");
        } else {
            Account account = accountOptional.get();
            return feignCardRepository.getAllCardsByAccountId(account.getId());
        }
    }

    public Card getCardById(Long cardId) throws ResourceNotFoundException {
        Long userId = feignUserRepository.getUserId();
        Optional<Account> accountOptional = accountsRepository.findByUserId(userId);
        if(accountOptional.isEmpty()) {
            throw new ResourceNotFoundException("Account not found");
        } else {
            Account account = accountOptional.get();
            return feignCardRepository.getCardByIdAndAccountId(account.getId(), cardId);
        }
    }

    public void deleteCardById(Long cardId) throws ResourceNotFoundException {
        Long userId = feignUserRepository.getUserId();
        Optional<Account> accountOptional = accountsRepository.findByUserId(userId);
        if(accountOptional.isEmpty()) {
            throw new ResourceNotFoundException("Account not found");
        } else {
            Account account = accountOptional.get();
            feignCardRepository.deleteCard(account.getId(), cardId);
        }
    }

}
