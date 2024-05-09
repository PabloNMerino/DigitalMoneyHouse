package com.digitalMoneyHouse.accountsservice.repository;

import com.digitalMoneyHouse.accountsservice.entities.User;
import com.digitalMoneyHouse.accountsservice.entities.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service", url = "localhost:8081/user")
public interface FeignUserRepository {

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id);
/*
    @GetMapping("/keycloak-id/{kcId}")
    User getUserByKeycloakId(@PathVariable String kcId);

    @GetMapping("/username/{username}")
    Long getUserByUsername(@PathVariable String username);
*/
    @GetMapping("/keycloak-id/{kcId}")
    Long getUserByKeycloakId(@PathVariable String kcId);

}
