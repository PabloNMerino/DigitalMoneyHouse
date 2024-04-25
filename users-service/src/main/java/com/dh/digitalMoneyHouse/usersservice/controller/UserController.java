package com.dh.digitalMoneyHouse.usersservice.controller;

import com.dh.digitalMoneyHouse.usersservice.entities.AccessKeycloak;
import com.dh.digitalMoneyHouse.usersservice.entities.Login;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.UserRegistrationDTO;
import com.dh.digitalMoneyHouse.usersservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/hola")
    public String saludar() {
        return "holaaaa";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws Exception {
        userService.createUser(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User " + userRegistrationDTO.username() + " succesfully created");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginData) throws Exception{
        AccessKeycloak credentials = userService.login(loginData);

        if (credentials != null) {
            return ResponseEntity.ok(credentials);
        } else if (userService.findByEmail(loginData.getEmail()).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

