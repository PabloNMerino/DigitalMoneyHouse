package com.digitalMoneyHouse.transactionsservice.repository;

import com.digitalMoneyHouse.transactionsservice.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM Transactions WHERE senderId= ?1 OR receiverId= ?1 ORDER BY Date LIMIT 5")
    List<Transaction> getLastFiveTransactionsByUserId(Long userId);
}
