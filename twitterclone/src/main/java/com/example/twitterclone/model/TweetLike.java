package com.example.twitterclone.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tweet_likes",uniqueConstraints = @UniqueConstraint(columnNames={"tweet_id","user_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TweetLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    private Tweet tweet;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
