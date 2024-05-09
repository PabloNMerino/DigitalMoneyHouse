package com.dh.digitalMoneyHouse.usersservice.controller;

import com.dh.digitalMoneyHouse.usersservice.entities.AccessKeycloak;
import com.dh.digitalMoneyHouse.usersservice.entities.Login;
import com.dh.digitalMoneyHouse.usersservice.entities.UserIdRequest;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.NewAliasRequest;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.NewPasswordRequest;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.UserDTO;
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
/*
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUsername(username));
    }
*/
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

    @PatchMapping("/update-alias")
    public ResponseEntity<?> updateAlias(@RequestBody NewAliasRequest newAlias) throws BadRequestException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateAlias(kcId,newAlias);
        return ResponseEntity.ok("Alias updated succesfully");
    }

    @PatchMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws Exception {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO userUpdated = userService.updateUser(kcId, userRegistrationDTO);

        if(userUpdated == null) {
            ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody NewPasswordRequest passwordRequest) throws BadRequestException {
        String kcId = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updatePassword(kcId, passwordRequest);

        return ResponseEntity.ok("Password updated succesfully");
    }

    @GetMapping("/keycloak-id/{kcId}")
    public ResponseEntity<?> getUserByKeycloakId(@PathVariable String kcId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserIdByKcId(kcId));
    }
}

