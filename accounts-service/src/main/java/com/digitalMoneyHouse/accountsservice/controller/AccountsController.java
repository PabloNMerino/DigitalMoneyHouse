package com.digitalMoneyHouse.accountsservice.controller;

import com.digitalMoneyHouse.accountsservice.entities.Account;
import com.digitalMoneyHouse.accountsservice.entities.AccountRequest;
import com.digitalMoneyHouse.accountsservice.entities.Card;
import com.digitalMoneyHouse.accountsservice.entities.CardRequest;
import com.digitalMoneyHouse.accountsservice.exceptions.BadRequestException;
import com.digitalMoneyHouse.accountsservice.exceptions.ResourceNotFoundException;
import com.digitalMoneyHouse.accountsservice.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAccount(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.getAccountInformation(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@RequestBody Account account, @PathVariable Long id) {
        return new ResponseEntity<>(accountsService.updateAccount(account), HttpStatus.OK);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getLastFiveTransactions (@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.getLastFiveTransactions(id));
    }

    @PostMapping("/register-card")
    public ResponseEntity<?> registerNewCard(CardRequest card) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountsService.registerCard(card));
    }

    @GetMapping("/cards")
    public ResponseEntity<?> getAllCards() throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.getAllCards());
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<?> getCardById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountsService.getCardById(id));
    }

    @DeleteMapping("/delete-card/{id}")
    public ResponseEntity<?> deleteCardById(@PathVariable Long id) throws ResourceNotFoundException {
        accountsService.deleteCardById(id);
        return ResponseEntity.ok().build();
    }

}
