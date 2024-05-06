package com.dh.digitalMoneyHouse.usersservice.controller;

import com.dh.digitalMoneyHouse.usersservice.entities.AccessKeycloak;
import com.dh.digitalMoneyHouse.usersservice.entities.Login;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.NewAliasRequest;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.UserRegistrationDTO;
import com.dh.digitalMoneyHouse.usersservice.exceptions.BadRequestException;
import com.dh.digitalMoneyHouse.usersservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userRegistrationDTO));
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println(userId);

        if (userId.isEmpty()) {
            ResponseEntity.notFound().build();
        }

        userService.logout(userId);

        return ResponseEntity.ok("Succesfully logged out");
    }

    @PutMapping("/{username}/forgot-password")
    public void forgotPassword(@PathVariable String username) {
        userService.forgotPassword(username);
    }

    @PatchMapping("/update-alias/{id}")
    public ResponseEntity<?> updateAlias(@PathVariable Long id, @RequestBody NewAliasRequest newAlias) throws BadRequestException {
        userService.updateAlias(id,newAlias);
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK);
    }

}

