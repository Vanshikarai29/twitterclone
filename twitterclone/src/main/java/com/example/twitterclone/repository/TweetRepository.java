package com.example.twitterclone.repository;

import com.example.twitterclone.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TweetRepository extends JpaRepository<Tweet,Long>{
    List<Tweet> findAllByUserId(Long userId);
}

//public class TweetRepository {
//}
