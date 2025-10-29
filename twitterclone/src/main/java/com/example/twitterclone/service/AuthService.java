package com.example.twitterclone.service;

import com.example.twitterclone.model.User;
import com.example.twitterclone.repository.UserRepository;
//import com.example.twitterclone.security.JwtUtil;
import com.example.twitterclone.security.Jwtutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Jwtutil jwtUtil;


    public String signup(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "User already exists!";  // friendly message, 200 OK
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "User registered successfully!";
    }

    public String login(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            System.out.println("DB username: " + user.getUsername());
            System.out.println("DB password (hashed): " + user.getPassword());
            System.out.println("Raw password: " + password);
            System.out.println("Manual bcrypt matches: " + passwordEncoder.matches(password, user.getPassword()));

//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            boolean matches = encoder.matches(password, user.getPassword());
//            System.out.println("Manual bcrypt matches: " + matches);

            if (passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                return "Bearer " + token;
            } else {
                System.out.println("Password mismatch");
                // Debug
            }
        } else {
            System.out.println("User not found in DB");
            // Debug
        }

//
// throw new RuntimeException("Invalid username or password");
        return "Invalid username or password";
    }
}


