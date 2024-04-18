package com.dh.digitalMoneyHouse.usersservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {


    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
}
