package com.example.twitterclone.Controller;


import com.example.twitterclone.model.Tweet;
import com.example.twitterclone.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
    @Autowired private TweetService tweetService;

    @PostMapping("/{userId}")
    public Tweet createTweet(@PathVariable Long userId, @RequestBody String content) {
        return tweetService.createTweet(userId, content);
    }



    @GetMapping("/{userId}")
    public List<Tweet> getUserTweets(@PathVariable Long userId) {
        return tweetService.getUserTweets(userId);
    }
}
