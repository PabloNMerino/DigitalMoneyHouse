package com.digitalMoneyHouse.accountsservice.repository;

import com.digitalMoneyHouse.accountsservice.entities.Transaction;
import com.digitalMoneyHouse.accountsservice.feignCustomExceptions.CustomErrorDecoder;
import com.digitalMoneyHouse.accountsservice.feignCustomExceptions.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "transactions-service", url = "localhost:8083/transaction", configuration = {FeignConfig.class, CustomErrorDecoder.class})
public interface FeignTransactionRepository {

    @GetMapping("/lastTransactions/{userId}")
    List<Transaction> getLastFiveTransactions(@PathVariable Long userId);
}
