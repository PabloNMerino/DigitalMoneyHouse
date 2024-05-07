package com.digitalMoneyHouse.cardsservice.service;

import com.digitalMoneyHouse.cardsservice.entities.Card;
import com.digitalMoneyHouse.cardsservice.exceptions.BadRequestException;
import com.digitalMoneyHouse.cardsservice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card registerCard(Card card) throws BadRequestException {
        checkCardData(card);
        return cardRepository.save(card);
    }

    private void checkCardData(Card card) throws BadRequestException {
        if(card.getAccountId() == null) {
            throw new BadRequestException("No account associated");
        }
        if(card.getHolder()==null || card.getHolder().equals("")) {
            throw new BadRequestException("Holder missing");
        }
        if(card.getNumber() == null || card.getNumber().length()!=16) {
            throw new BadRequestException("Number missing");
        }
        if(card.getExpirationDate() == null) {
            throw new BadRequestException("Expiration date missing");
        }
        if(card.getExpirationDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Card already expired");
        }
        if(card.getCvv() == null || card.getCvv().equals("")) {
            throw new BadRequestException("CVV missing");
        }
    }

    public Card getCardByAccountIdAndCardId(Long accountId, Long cardId) {

    }

}
