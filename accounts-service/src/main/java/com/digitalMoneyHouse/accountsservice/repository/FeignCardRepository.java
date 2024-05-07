package com.digitalMoneyHouse.accountsservice.repository;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "cards-service", url = "localhost:8084/cards")
public interface FeignCardRepository {
}
