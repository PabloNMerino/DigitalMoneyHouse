package com.digitalMoneyHouse.cardsservice.controller;

import com.digitalMoneyHouse.cardsservice.entities.Card;
import com.digitalMoneyHouse.cardsservice.exceptions.BadRequestException;
import com.digitalMoneyHouse.cardsservice.exceptions.ResourceNotFoundException;
import com.digitalMoneyHouse.cardsservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/{id}/all-cards")
    public ResponseEntity<?> getAllCardsByAccountId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getAllCardsByAccountId(id));
    }

    @GetMapping("/account/{accountId}/card/{cardId}")
    public ResponseEntity<?> getCardByIdAndAccountId (@PathVariable Long accountId, @PathVariable Long cardId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getCardByIdAndAccountId(cardId, accountId));
    }

    @PostMapping("/register-card")
    public ResponseEntity<?> registerCard( Card card) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.registerCard(card));
    }

    @DeleteMapping("/account/{accountId}/card/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long accountId, @PathVariable Long cardId) throws ResourceNotFoundException {
        cardService.deleteCard(cardId, accountId);
        return ResponseEntity.ok().build();
    }
}
