package com.digitalMoneyHouse.accountsservice.entities;

import lombok.Data;

@Data
public class DepositMoneyRequest {
    private Long cardId;
    private Double amount;
}
