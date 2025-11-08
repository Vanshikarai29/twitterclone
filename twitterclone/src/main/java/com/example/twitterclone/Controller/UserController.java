package com.example.twitterclone.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.twitterclone.model.User;  // User entity
import com.example.twitterclone.service.UserService;  // Service layer for user

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(user);
    }
}



//package com.example.twitterclone.Controller;
//
//import com.example.twitterclone.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    // Follow user endpoint
//    @PostMapping("/{id}/follow")
//    public ResponseEntity<?> followUser(@PathVariable Long id, Principal principal) {
//        String username = principal.getName();
//        boolean success = userService.followUser(id, username);
//        if (success) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.badRequest().body("Unable to follow user");
//        }
//    }
//
//    // Unfollow user endpoint
//    @DeleteMapping("/{id}/follow")
//    public ResponseEntity<?> unfollowUser(@PathVariable Long id, Principal principal) {
//        String username = principal.getName();
//        boolean success = userService.unfollowUser(id, username);
//        if (success) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.badRequest().body("Unable to unfollow user");
//        }
//    }
//
//
//}
