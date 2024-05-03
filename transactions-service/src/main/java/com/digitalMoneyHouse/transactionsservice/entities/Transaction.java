package com.digitalMoneyHouse.transactionsservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int senderId;

    private int receiverId;

    @Column(name = "Balance")
    private Double amountOfMoney;

    private LocalDate date;

}
