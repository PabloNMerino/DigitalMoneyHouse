package com.digitalMoneyHouse.transactionsservice.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Sender ID")
    private int senderId;

    @Column(name = "Receiver ID")
    private int receiverId;

    @Column(name = "Balance")
    private Double amountOfMoney;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "Type")
    private String type;
}
