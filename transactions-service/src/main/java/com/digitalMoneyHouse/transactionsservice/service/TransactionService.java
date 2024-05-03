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

    public Optional<Account> getAccount(Long userId) {
        try{
            Account account = feignAccountRepository.getAccountById(userId);
            return Optional.of(account);
        } catch (Exception e) {
            return Optional.empty();
        }

    }

/*
    public List<Transaction> getLastFiveTransactionsByUserId(Long userId) throws ResourceNotFoundException {
        List<Transaction> lastFiveTransactions = transactionRepository.getLastFiveTransactionsByUserId(userId);
        if(lastFiveTransactions==null || lastFiveTransactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found");
        }
        return lastFiveTransactions;
    }
*/
    public void updateAccount(Account account) {
        feignAccountRepository.updateBalance(account, account.getId());
    }


}
