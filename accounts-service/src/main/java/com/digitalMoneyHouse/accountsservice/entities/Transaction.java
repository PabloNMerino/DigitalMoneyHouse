package com.digitalMoneyHouse.accountsservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Transaction {

    private Long id;
    private int senderId;
    private int receiverId;
    private Double amountOfMoney;
    private LocalDate date;

    public Transaction(int senderId, int receiverId, Double amountOfMoney, LocalDate date) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amountOfMoney = amountOfMoney;
        this.date = date;
    }
}
