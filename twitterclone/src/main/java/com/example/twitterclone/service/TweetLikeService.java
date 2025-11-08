package com.example.twitterclone.service;

import com.example.twitterclone.model.Tweet;
import com.example.twitterclone.model.TweetLike;
import com.example.twitterclone.model.User;
import com.example.twitterclone.repository.TweetLikeRepository;
import com.example.twitterclone.repository.TweetRepository;
import com.example.twitterclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TweetLikeService {

    @Autowired
    private
    TweetLikeRepository tweetLikeRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean likeTweet(Long tweetId, Long userId) {
        Optional<Tweet> tweetOpt = tweetRepository.findById(tweetId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (tweetOpt.isEmpty() || userOpt.isEmpty()) {
            return false;
        }
        Tweet tweet = tweetOpt.get();
        User user = userOpt.get();
        if (tweetLikeRepository.findByTweetAndUser(tweet, user).isPresent()) {
            return false; // Already liked
        }
        tweetLikeRepository.save(new TweetLike(null, tweet, user));
        return true;
    }

    public boolean unlikeTweet(Long tweetId, Long userId) {
        Optional<Tweet> tweetOpt = tweetRepository.findById(tweetId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (tweetOpt.isEmpty() || userOpt.isEmpty()) {
            return false;
        }
        Tweet tweet = tweetOpt.get();
        User user = userOpt.get();
        Optional<TweetLike> likeOpt = tweetLikeRepository.findByTweetAndUser(tweet, user);
        if (likeOpt.isPresent()) {
            tweetLikeRepository.delete(likeOpt.get());
            return true;
        }
        return false; // Not liked yet
    }

    public int countLikes(Long tweetId) {
        return tweetRepository.findById(tweetId)
                .map(tweetLikeRepository::countByTweet)
                .orElse(0);
    }
}
