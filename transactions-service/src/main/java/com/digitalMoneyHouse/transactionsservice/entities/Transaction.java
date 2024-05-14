package com.digitalMoneyHouse.transactionsservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDate;

@Builder
@Entity
@Data
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int senderId;

    private int receiverId;

    @Column(name = "Balance")
    private Double amountOfMoney;

    private LocalDate date;

    public Transaction(int senderId, int receiverId, Double amountOfMoney, LocalDate date) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amountOfMoney = amountOfMoney;
        this.date = date;
    }

}
