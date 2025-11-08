package com.example.twitterclone.service;

import com.example.twitterclone.model.Tweet;
import com.example.twitterclone.model.User;
import com.example.twitterclone.repository.TweetRepository;
import com.example.twitterclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetService {
    @Autowired private TweetRepository tweetRepository;
    @Autowired private UserRepository userRepository;

    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    public Page<Tweet> findAll(Pageable pageable) {
        return tweetRepository.findAll(pageable);
    }

    public Tweet findById(Long id) {
        return tweetRepository.findById(id).orElse(null);
    }

    public void delete(long id) {
        tweetRepository.deleteById(id);
    }

    public List<Tweet> getUserTweets(Long userId){
        return tweetRepository.findAllByUserId(userId);
    }

    public List<Tweet> findRepliesByParentId(Long parentId){
        return tweetRepository.findByParentTweetId(parentId);
    }
    public Tweet createTweet(Long userId, String content){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setCreatedAt(LocalDateTime.now());
        tweet.setContent(content);
        return tweetRepository.save(tweet);
    }

//    public List<Tweet> getUserTweets(Long userId) {
//        return tweetRepository.findAllByUserId(userId);
//    }

}
