package com.dh.digitalMoneyHouse.usersservice.service;

import com.dh.digitalMoneyHouse.usersservice.entities.*;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.NewAliasRequest;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.NewPasswordRequest;
import com.dh.digitalMoneyHouse.usersservice.entities.dto.UserDTO;
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

        checkUserRequest(userInformation);

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

        //register user in KC:
        User userKc = keycloakService.createUser(newUser);
        newUser.setKeycloakId(userKc.getKeycloakId());

        //register in database
        User userSaved = userRepository.save(newUser);

        //create account for user
        feignAccountRepository.createAccount(new AccountRequest(userSaved.getId()));

        return new UserDTO(userInformation.name(), userInformation.lastName(), userInformation.username(), userInformation.email(), userInformation.phoneNumber(), newCvu, newAlias);
    }

    private void checkUserRequest(UserRegistrationDTO userInformation) throws BadRequestException {
        String phoneNumberPattern = "\\b\\d+\\b";
        String emailPattern = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";
        if(userInformation.name().isEmpty()     ||
           userInformation.lastName().isEmpty() ||
           userInformation.username().isEmpty() ||
           !userInformation.email().matches(emailPattern) ||
           !userInformation.phoneNumber().matches(phoneNumberPattern) ||
           userInformation.password().isEmpty()) {
            throw new BadRequestException("Field wrong or missing");
        }
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

    public void updateAlias(String kcId, NewAliasRequest newAlias) throws BadRequestException {
        String aliasRequest = newAlias.getAlias();

        checkAliasField(aliasRequest);

        Optional<User> userOptional = userRepository.findByKeycloakId(kcId);

        if(userOptional.isEmpty()) {
            throw  new ResourceNotFoundException("User not found");
        }

        User userFound = userOptional.get();

        Optional<User> aliasOptional = userRepository.findByAlias(aliasRequest);
        if(aliasOptional.isPresent()) {
            throw new BadRequestException("Alias already being used");
        } else {
            userFound.setAlias(aliasRequest);
        }

        userRepository.save(userFound);
    }

    public UserDTO updateUser(String id, UserRegistrationDTO userRegistrationDTO) throws BadRequestException {
        Optional<User> userOptional = userRepository.findByKeycloakId(id);

        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        User userFound = userOptional.get();

        userFound.setName(userRegistrationDTO.name());
        userFound.setLastName(userRegistrationDTO.lastName());
        userFound.setEmail(userRegistrationDTO.email());
        userFound.setPhoneNumber(userRegistrationDTO.phoneNumber());
        userFound.setPassword(userRegistrationDTO.password());

        userRepository.save(userFound);
        keycloakService.updateUser(userOptional.get(), userFound);

        return new UserDTO(userFound.getName(), userFound.getLastName(), userFound.getUsername(), userFound.getEmail(), userFound.getPhoneNumber(), userFound.getCvu(), userFound.getAlias());
    }

    private void checkAliasField (String alias) throws BadRequestException {

        String pattern = "\\b(?:[a-zA-Z]+\\.?)+\\b";

        if (alias == null || alias.length() == 0) {
            throw new BadRequestException("No alias found");
        }

        if (!alias.matches(pattern)) {
            throw new BadRequestException("Alias can't contain numbers");
        }

        if(alias.trim().length()<=3) {
            throw  new BadRequestException("alias must have at least 4 characters");
        }

        if(userRepository.findByAlias(alias).isPresent()) {
            throw  new BadRequestException("alias already exists");
        }
    }

    public void updatePassword(String kcId, NewPasswordRequest passwordRequest) throws BadRequestException {
        if(!passwordRequest.getPassword().equals(passwordRequest.getPasswordRepeated())) {
            throw new BadRequestException("Passwords must be equals");
        }
        Optional<User> userOptional = userRepository.findByKeycloakId(kcId);

        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("user not found");
        } else {
            User userFound = userOptional.get();
            userFound.setPassword(passwordRequest.getPassword());

            userRepository.save(userFound);
            keycloakService.updateUser(userOptional.get(), userFound);
        }
    }

    public Long getUserIdByKcId(String kcId) {
        Optional<User> userOptional = userRepository.findByKeycloakId(kcId);
        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        } else {
            return userOptional.get().getId();
        }
    }
    public Long getUserIdByAlias(String alias) {
        Optional<User> userOptional = userRepository.findByAlias(alias);
        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        } else {
            return userOptional.get().getId();
        }
    }

    public Long getUserIdByCvu(String cvu) {
        Optional<User> userOptional = userRepository.findByCvu(cvu);
        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        } else {
            return userOptional.get().getId();
        }
    }

}
