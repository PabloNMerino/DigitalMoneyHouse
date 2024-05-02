package com.digitalMoneyHouse.transactionsservice.controller;

import com.digitalMoneyHouse.transactionsservice.entities.Account;
import com.digitalMoneyHouse.transactionsservice.entities.Transaction;
import com.digitalMoneyHouse.transactionsservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {

        if(!transactionService.isAccountSaved((long) transaction.getSenderId())) {
            return new ResponseEntity("Origin Account does not exists", HttpStatus.BAD_REQUEST);
        }

        if(!transactionService.isAccountSaved((long) transaction.getReceiverId())) {
            return new ResponseEntity("Destiny Account does not exists", HttpStatus.BAD_REQUEST);
        }

        Account originAccount = transactionService.getAccount((long) transaction.getSenderId());
        Account destinyAccount = transactionService.getAccount((long) transaction.getReceiverId());

        if(originAccount.getBalance() < transaction.getAmountOfMoney()) {
            return new ResponseEntity("Not enough money", HttpStatus.BAD_REQUEST);
        }


    }
}
