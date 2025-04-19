package com.projectx.repositories;

import com.projectx.entites.Post;
import com.projectx.entites.PostLike;
import com.projectx.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    List<PostLike> findByUserAndPost(User u1, Post p1);
}
