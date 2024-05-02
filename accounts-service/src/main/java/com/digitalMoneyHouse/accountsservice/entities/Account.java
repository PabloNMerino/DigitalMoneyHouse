package com.digitalMoneyHouse.accountsservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UserID", nullable = false)
    private Long userId;

    @Column(name = "Balance",nullable = false)
    private Double balance;

    @Transient
    private List<Transaction> transactions;

    public Account(Long userId) {
        this.userId = userId;
        this.balance = 0.0;
    }
}
