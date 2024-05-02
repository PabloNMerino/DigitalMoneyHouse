package com.dh.digitalMoneyHouse.usersservice.service;

import com.dh.digitalMoneyHouse.usersservice.entities.AccessKeycloak;
import com.dh.digitalMoneyHouse.usersservice.entities.AccountRequest;
import com.dh.digitalMoneyHouse.usersservice.entities.Login;
import com.dh.digitalMoneyHouse.usersservice.entities.User;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.UserDTO;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.UserKeycloak;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.UserRegistrationDTO;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.mapper.UserDTOMapper;
import com.dh.digitalMoneyHouse.usersservice.exceptions.BadRequestException;
import com.dh.digitalMoneyHouse.usersservice.exceptions.ResourceNotFoundException;
import com.dh.digitalMoneyHouse.usersservice.repository.FeignAccountRepository;
import com.dh.digitalMoneyHouse.usersservice.repository.UserRepository;
import com.dh.digitalMoneyHouse.usersservice.utils.AliasCvuGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AliasCvuGenerator generator;

    @Autowired
    KeycloakService keycloakService;
    @Autowired
    private final UserDTOMapper userDTOMapper;

    @Autowired
    private FeignAccountRepository feignAccountRepository;

    public UserService(UserRepository userRepository, AliasCvuGenerator generator, KeycloakService keycloakService, UserDTOMapper userDTOMapper, FeignAccountRepository feignAccountRepository) {
        this.userRepository = userRepository;
        this.generator = generator;
        this.keycloakService = keycloakService;
        this.userDTOMapper = userDTOMapper;
        this.feignAccountRepository = feignAccountRepository;
    }


    public UserDTO createUser (UserRegistrationDTO userInformation) throws Exception {

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

        User userSaved = userRepository.save(newUser);

        feignAccountRepository.createAccount(new AccountRequest(userSaved.getId()));

        //register user in KC:
        keycloakService.createUser(new UserKeycloak(userInformation.name(), userInformation.lastName(), userInformation.username(), userInformation.email(), userInformation.password()));

        return new UserDTO(userInformation.name(), userInformation.lastName(), userInformation.username(), userInformation.email(), userInformation.phoneNumber(), newCvu, newAlias);
    }

    public UserDTO getUserById(Long id) {
       return userRepository.findById(id)
               .map(userDTOMapper)
               .orElseThrow(()-> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    public AccessKeycloak login (Login loginData) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(loginData.getEmail());
        if(optionalUser.isEmpty()) {
            throw new Exception("User not found");
        }
        return keycloakService.login(loginData);
    }

    public Optional<User> findByEmail(String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new Exception("User not found!");
        }
        return user;
    }

    public void logout(String userId) {
        keycloakService.logout(userId);
    }

    public void forgotPassword(String username) {
        keycloakService.forgotPassword(username);
    }

}
