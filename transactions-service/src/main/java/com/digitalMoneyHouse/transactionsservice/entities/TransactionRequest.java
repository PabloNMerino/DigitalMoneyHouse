package com.digitalMoneyHouse.transactionsservice.entities;

import lombok.Data;

import java.time.LocalDate;
@Data
public class TransactionRequest {
    private int senderId;
    private int receiverId;
    private Double amountOfMoney;
    private LocalDate date;
}
