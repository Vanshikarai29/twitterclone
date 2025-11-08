package com.example.twitterclone.service;

import com.example.twitterclone.model.User;
import com.example.twitterclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    // aur other user related methods
}
