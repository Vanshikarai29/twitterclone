package com.example.twitterclone.Controller;

import com.example.twitterclone.model.User;
import com.example.twitterclone.repository.UserRepository;
import com.example.twitterclone.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins="http://127.0.0.1:5500")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "User already exists!";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        Optional<User> optionalUser = userRepository.findByUsername(loginUser.getUsername());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username");
        }
        User existingUser = optionalUser.get();

        boolean matches = passwordEncoder.matches(loginUser.getPassword(), existingUser.getPassword());
        System.out.println("Manual bcrypt matches: " + matches);

        if (!matches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        //return ResponseEntity.ok("Login successful");
        String token = jwtUtil.generateToken(existingUser.getUsername());
        System.out.println("Generated JWT token: " + token);

        return ResponseEntity.ok(Map.of("token", token));
    }
}



//package com.example.twitterclone.Controller;
//import com.example.twitterclone.model.User;
//import com.example.twitterclone.service.AuthService;
//import jdk.internal.org.commonmark.internal.util.AsciiMatcher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//    @Autowired
//    private AuthService authService;
//
//    @PostMapping("/signup")
//    public String signup(@RequestBody User user) {
//        return authService.signup(user);
//    }
//
////    @PostMapping("/login")
////    public String login(@RequestBody User user) {
////        return authService.login(user.getUsername(), user.getPassword());
////    }
//@PostMapping("/login")
//public ResponseEntity<String> login(@RequestBody User loginUser) {
//    User existingUser = userRepository.findByUsername(loginUser.getUsername());
//    if (existingUser == null) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username");
//    }
//
//    AsciiMatcher passwordEncoder;
//    boolean matches = passwordEncoder.matches(loginUser.getPassword(), existingUser.getPassword());
//    System.out.println("Manual bcrypt matches: " + matches);
//
//    if (!matches) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
//    }
//
//    return ResponseEntity.ok("Login successful");
//}
//
//
//}
