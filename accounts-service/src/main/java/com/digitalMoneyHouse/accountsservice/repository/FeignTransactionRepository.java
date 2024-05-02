package com.digitalMoneyHouse.accountsservice.repository;

import com.digitalMoneyHouse.accountsservice.entities.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "transactions-service", url = "localhost:8083/transactions")
public interface FeignTransactionRepository {

    @GetMapping("/lastTransactions/{userId}")
    List<Transaction> getLastFiveTransactions(@PathVariable Long userId);
}
