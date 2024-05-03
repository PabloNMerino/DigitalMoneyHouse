package com.digitalMoneyHouse.transactionsservice.repository;

import com.digitalMoneyHouse.transactionsservice.entities.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = " FROM transactions WHERE (senderId= ?1 OR receiverId= ?1) ORDER BY date DESC")
    List<Transaction> getLastFiveTransactionsByUserId(Long userId, Pageable pageable);

}
