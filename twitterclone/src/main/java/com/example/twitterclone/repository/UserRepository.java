package com.example.twitterclone.repository;

import com.example.twitterclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>
{
//    boolean followUser(Long userIdToFollow, String followerUsername);
//    boolean unfollowUser(Long userIdToUnfollow, String followerUsername);
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
//public class UserRepository {
//}
