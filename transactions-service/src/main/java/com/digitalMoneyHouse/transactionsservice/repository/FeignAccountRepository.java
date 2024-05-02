package com.digitalMoneyHouse.transactionsservice.repository;

import com.digitalMoneyHouse.transactionsservice.entities.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "accounts-service", url = "localhost:8082/accounts")
public interface FeignAccountRepository {

    @GetMapping("/{id}")
    Account getAccountById(@PathVariable Long id);
}
