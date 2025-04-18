package com.projectx.repositories;

import com.projectx.entites.User;
import com.projectx.entites.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    List<UserFollow> findByFollowedAndFollowing(User u1, User u2);
}
