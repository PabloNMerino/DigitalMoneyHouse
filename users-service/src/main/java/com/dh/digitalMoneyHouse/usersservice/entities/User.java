package com.dh.digitalMoneyHouse.usersservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "Name",nullable = false)
    private String name;

    @Column(name = "Last Name",nullable = false)
    private String lastName;

    @Column(name = "Email",nullable = false)
    private String email;

    @Column(name = "Phone Number",nullable = false)
    private String phoneNumber;

    @Column(name = "CVU",nullable = false)
    private String cvu;

    @Column(name = "Alias",nullable = false)
    private String alias;

    @Column(name = "Password",nullable = false)
    private String password;

}
