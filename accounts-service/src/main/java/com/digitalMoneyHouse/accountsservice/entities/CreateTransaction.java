package com.digitalMoneyHouse.accountsservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class CreateTransaction {
    private int senderId;
    private int receiverId;
    private Double amountOfMoney;
    private LocalDate date;
}