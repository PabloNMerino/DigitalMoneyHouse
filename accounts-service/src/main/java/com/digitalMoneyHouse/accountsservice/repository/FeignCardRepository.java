package com.digitalMoneyHouse.accountsservice.repository;

import com.digitalMoneyHouse.accountsservice.entities.Card;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cards-service", url = "localhost:8084/cards")
public interface FeignCardRepository {

    @PostMapping("/register-card")
    Card registerCard(Card card);
}
