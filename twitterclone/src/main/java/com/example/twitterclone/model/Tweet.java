package com.example.twitterclone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Data@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tweets")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
//    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="parent_tweet_id")
    private Long parentTweetId;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;
}
