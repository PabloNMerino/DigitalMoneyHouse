package com.digitalMoneyHouse.accountsservice.controller;

import com.digitalMoneyHouse.accountsservice.entities.*;
import com.digitalMoneyHouse.accountsservice.exceptions.BadRequestException;
import com.digitalMoneyHouse.accountsservice.exceptions.ResourceNotFoundException;
import com.digitalMoneyHouse.accountsservice.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountsController {
    @Autowired
    private AccountsService accountsService;
    @PostMapping("/create")
    public void createAccount(@RequestBody AccountRequest accountRequest) {
        accountsService.createAccount(accountRequest.getUserId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.getAccountInformation(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@RequestBody Account account, @PathVariable Long id) {
        return new ResponseEntity<>(accountsService.updateAccount(account), HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getLastFiveTransactions () throws ResourceNotFoundException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId=  accountsService.getUserIdByKcId(kcId);
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.getLastFiveTransactions(userId));
    }

    @GetMapping("/activity")
    public ResponseEntity<?> getAllTransactions() throws ResourceNotFoundException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId=  accountsService.getUserIdByKcId(kcId);
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.getAllTransactions(userId));
    }

    @GetMapping("/activity/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable Long transactionId) throws ResourceNotFoundException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId=  accountsService.getUserIdByKcId(kcId);
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.getTransaction(userId, transactionId));
    }

    @PostMapping("/register-card")
    public ResponseEntity<?> registerNewCard(@RequestBody CardRequest card) throws ResourceNotFoundException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId=  accountsService.getUserIdByKcId(kcId);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountsService.registerCard(card, userId));
    }

    @GetMapping("/cards")
    public ResponseEntity<?> getAllCards() throws ResourceNotFoundException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId=  accountsService.getUserIdByKcId(kcId);
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.getAllCards(userId));
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<?> getCardById(@PathVariable Long id) throws ResourceNotFoundException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId=  accountsService.getUserIdByKcId(kcId);
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.getCardById(id, userId));
    }

    @DeleteMapping("/delete-card/{id}")
    public ResponseEntity<?> deleteCardById(@PathVariable Long id) throws ResourceNotFoundException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId=  accountsService.getUserIdByKcId(kcId);
        accountsService.deleteCardById(id, userId);
        return ResponseEntity.ok().build();
    }

}
