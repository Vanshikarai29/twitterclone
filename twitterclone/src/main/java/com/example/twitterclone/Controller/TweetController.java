package com.example.twitterclone.Controller;

import com.example.twitterclone.dto.TweetDto;
import com.example.twitterclone.model.Tweet;
import com.example.twitterclone.model.User;
import com.example.twitterclone.repository.TweetRepository;
import com.example.twitterclone.repository.UserRepository;
import com.example.twitterclone.service.TweetLikeService;
import com.example.twitterclone.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins={"https://127.0.1:5500","https://localhost:5500"})
@RequestMapping("/api/tweets")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetLikeService tweetLikeService;


    @PostMapping("")
    public ResponseEntity<Tweet> createTweet(@RequestBody Tweet tweet,Principal principal) {
        String username = principal.getName();
        Optional<User> user=userRepository.findByUsername(username);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User use=user.get();
        tweet.setUser(use);
//        tweetRepository.save(tweet);
      tweet.setCreatedAt(LocalDateTime.now());


        if(tweet.getParentTweetId()!=null){
            Tweet parentTweet=tweetService.findById(tweet.getParentTweetId());
            tweet.setTweet(parentTweet);
        }
//        Tweet savedTweet=tweetService.createTweet(user.get().getId(),tweet.getContent());
        Tweet savedTweet=tweetRepository.save(tweet);
        return ResponseEntity.ok(savedTweet);
    }

    @GetMapping("")
    public Page<Tweet>  getAllTweets(Pageable pageable) {
        return tweetService.findAll(pageable);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getTweetById(@PathVariable Long id){
        Tweet tweet = tweetService.findById(id);
        if(tweet == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tweet);
    }

//    @PostMapping("")
//    public ResponseEntity<Tweet> addTweet(@RequestBody Tweet tweet, Principal principal){
//        Tweet savedTweet = tweetService.save(tweet);
//        return ResponseEntity.ok(savedTweet);
//    }

    @GetMapping("/user/{userId}")
    public  List<Tweet> getTweetsByUserId(@PathVariable Long userId){
        return tweetService.getUserTweets(userId);
    }

    @GetMapping("/replies/{parentId}")
    public List<Tweet> findReplies(@PathVariable Long parentId){
        return tweetService.findRepliesByParentId(parentId);
    }


    // --- Like a tweet ---
    @PostMapping("/{id}/like")
    public ResponseEntity<?> likeTweet(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        boolean success = tweetLikeService.likeTweet(id, user.get().getId());
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    // --- Unlike a tweet ---
    @DeleteMapping("/{id}/like")
    public ResponseEntity<?> unlikeTweet(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        boolean success = tweetLikeService.unlikeTweet(id, user.get().getId());
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    // --- Edit tweet ---
    @PutMapping("/{id}")
    public ResponseEntity<Tweet> editTweet(@PathVariable Long id, @RequestBody Tweet updatedTweet, Principal principal) {
        Tweet tweet = tweetService.findById(id);
        if (tweet == null || !tweet.getUser().getUsername().equals(principal.getName()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        tweet.setContent(updatedTweet.getContent());
        tweetRepository.save(tweet);
        return ResponseEntity.ok(tweet);
    }

    // --- Delete tweet ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id, Principal principal) {
        Tweet tweet = tweetService.findById(id);
        if (tweet == null || !tweet.getUser().getUsername().equals(principal.getName()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        tweetRepository.delete(tweet);
        return ResponseEntity.noContent().build();
    }

    // --- Tweet likes count (optional endpoint) ---
    @GetMapping("/{id}/likes")
    public ResponseEntity<Integer> getLikesCount(@PathVariable Long id) {
        int count = tweetLikeService.countLikes(id);
        return ResponseEntity.ok(count);
    }

}
//    @Autowired private TweetRepository tweetRepository;
//    @Autowired private UserRepository userRepository;
//
//    // Correct paginated method!
//    public Page<Tweet> findAll(Pageable pageable) {
//        return tweetRepository.findAll(pageable);
//    }
//
//    public Tweet save(Tweet tweet) {
//        return tweetRepository.save(tweet);
//    }
//
//    public Tweet findById(Long id) {
//        return tweetRepository.findById(id).orElse(null);
//    }
//
//    public void delete(Long id) {
//        tweetRepository.deleteById(id);
//    }
//
//    public List<Tweet> getUserTweets(Long userId) {
//        return tweetRepository.findAllByUserId(userId);
//    }
//
//    public List<Tweet> findRepliesByParentId(Long parentId) {
//        return tweetRepository.findByParentTweetId(parentId);
//    }
//}





//package com.example.twitterclone.Controller;
//
//import com.example.twitterclone.model.Tweet;
//import com.example.twitterclone.model.User;
//import com.example.twitterclone.service.TweetService;
//import com.example.twitterclone.service.UserService;
//import com.example.twitterclone.dto.TweetDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/tweets")
//public class TweetController {
//
//    @Autowired private TweetService tweetService;
//    @Autowired private UserService userService;
//
//    // Create tweet (advanced—uses principal and DTO, supports replies)
//    @PostMapping("")
//    public ResponseEntity<?> createTweet(@RequestBody TweetDto tweetDto, Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//        Tweet tweet = new Tweet();
//        tweet.setContent(tweetDto.getContent());
//        tweet.setUser(user);
//        tweet.setParentTweetId(tweetDto.getParentTweetId());
//        tweetService.save(tweet);
//        return ResponseEntity.ok(tweet);
//    }
//
//    // Get paginated tweets for main feed
//    @GetMapping("")
//    public Page<Tweet> getTweets(@RequestParam int page, @RequestParam int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        return tweetService.findAll(pageable);
//    }
//
//    // Edit tweet
//    @PutMapping("/{id}")
//    public ResponseEntity<?> editTweet(@PathVariable Long id, @RequestBody TweetDto tweetDto, Principal principal) {
//        Tweet tweet = tweetService.findById(id);
//        if (tweet.getUser().getUsername().equals(principal.getName())) {
//            tweet.setContent(tweetDto.getContent());
//            tweetService.save(tweet);
//            return ResponseEntity.ok(tweet);
//        }
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//    }
//
//    // Delete tweet
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteTweet(@PathVariable Long id, Principal principal) {
//        Tweet tweet = tweetService.findById(id);
//        if (tweet.getUser().getUsername().equals(principal.getName())) {
//            tweetService.delete(id);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//    }
//
//    // Get all tweets by a user (you already had this)
//    @GetMapping("/user/{userId}")
//    public List<Tweet> getUserTweets(@PathVariable Long userId) {
//        return tweetService.getUserTweets(userId);
//    }
//
//    // Get replies for a tweet (threaded conversations)
//    @GetMapping("/{id}/replies")
//    public List<Tweet> getReplies(@PathVariable Long id) {
//        return tweetService.findRepliesByParentId(id);
//    }
//}






//package com.example.twitterclone.Controller;
//
//
//import com.example.twitterclone.model.Tweet;
//
//import com.example.twitterclone.service.TweetService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/tweets")
//public class TweetController {
//    @Autowired private TweetService tweetService;
//
//
//    @PostMapping("/{userId}")
//    public Tweet createTweet(@PathVariable Long userId, @RequestBody String content) {
//       return tweetService.createTweet(userId, content);
//    }
//
//
//
//    @GetMapping("/{userId}")
//    public List<Tweet> getUserTweets(@PathVariable Long userId) {
//        return tweetService.getUserTweets(userId);
//    }
////edit tweet
//
//}
