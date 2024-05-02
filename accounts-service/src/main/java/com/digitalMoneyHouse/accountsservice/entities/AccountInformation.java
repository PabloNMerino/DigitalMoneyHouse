package com.digitalMoneyHouse.accountsservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountInformation {
    private Long userId;
    private Double balance;
    private String cvu;
    private String alias;
}
