package com.digitalMoneyHouse.accountsservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Transaction {

    private Long id;
    private int originAccountId;
    private int destinyAccountId;
    private Double amountOfMoney;
    private LocalDate date;
}
