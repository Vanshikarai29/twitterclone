package com.example.twitterclone.repository;

import com.example.twitterclone.model.TweetLike;
import com.example.twitterclone.model.Tweet;
import com.example.twitterclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface TweetLikeRepository extends JpaRepository<TweetLike,Long> {
    Optional<TweetLike>findByTweetAndUser(Tweet tweet,User user);
    int countByTweet(Tweet tweet);
    void deleteByTweetAndUser(Tweet tweet,User user);

}
