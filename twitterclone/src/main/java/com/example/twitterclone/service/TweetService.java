package com.example.twitterclone.service;

import com.example.twitterclone.model.Tweet;
import com.example.twitterclone.model.User;
import com.example.twitterclone.repository.TweetRepository;
import com.example.twitterclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetService {
    @Autowired private TweetRepository tweetRepository;
    @Autowired private UserRepository userRepository;

    public Tweet createTweet(Long userId, String content){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setContent(content);
        return tweetRepository.save(tweet);
    }

    public List<Tweet> getUserTweets(Long userId) {
        return tweetRepository.findAllByUserId(userId);
    }

}
