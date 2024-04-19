package com.dh.digitalMoneyHouse.usersservice.service;

import com.dh.digitalMoneyHouse.usersservice.entities.User;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.UserRegistrationDTO;
import com.dh.digitalMoneyHouse.usersservice.exceptions.BadRequestException;
import com.dh.digitalMoneyHouse.usersservice.repository.UserRepository;
import com.dh.digitalMoneyHouse.usersservice.utils.AliasCvuGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private AliasCvuGenerator generator;

    public UserRegistrationDTO createUser (UserRegistrationDTO userInformation) throws Exception {

        Optional<User> userEmailOptional = userRepository.findByEmail(userInformation.email());
        Optional<User> userUsernameOptional = userRepository.findByUsername(userInformation.username());
        List<User> users = userRepository.findAll();
        String newCvu= "";
        String newAlias= "";
        String finalNewCvu = newCvu;
        String finalNewAlias = newAlias;


        if(userEmailOptional.isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        if(userUsernameOptional.isPresent()) {
            throw new BadRequestException("Username already exists");
        }


        //check if cvu exists in DB and creates a new one
        do {
            newCvu = generator.generateCvu();
        } while (users.stream().anyMatch(user -> user.getCvu().equals(finalNewCvu)));

        //check if alias exists in DB and creates a new one
        do {
           newAlias= generator.generateAlias();
        } while (users.stream().anyMatch(user -> user.getAlias().equals(finalNewAlias)));

        User newUser = new User(
                userInformation.name(),
                userInformation.lastName(),
                userInformation.username(),
                userInformation.email(),
                userInformation.phoneNumber(),
                newCvu,
                newAlias,
                userInformation.password()
        );

        userRepository.save(newUser);

        //register user in KC and create DTO for users retrieved from DB
        return null; //change when function is done
    }
}
