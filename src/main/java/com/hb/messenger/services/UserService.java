package com.hb.messenger.services;

import com.hb.messenger.exceptions.ErrorCode;
import com.hb.messenger.exceptions.MessengerException;
import com.hb.messenger.models.entities.UserEntity;
import com.hb.messenger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;

    public void createUser(String username, String password) {
       UserEntity userEntity= userRepository.findByUsername(username);
       if(userEntity!=null){
           throw MessengerException.error(ErrorCode.DUPLICATE_USER);
       }
        userRepository.save(UserEntity.builder().
                username(username)
                .passcode(password)
                .build());
    }

    public List<String> fetchAllUsers(){
        return userRepository.findAll().stream().map(UserEntity::getUsername).collect(Collectors.toList());
    }

}
