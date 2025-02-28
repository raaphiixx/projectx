package com.projectx.configurations;

import com.projectx.entites.*;
import com.projectx.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class TestConfiguration implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

    @Autowired
    private PostRTRepository postRTRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User("user1", "123", "User 1", "last 1");
        User u2 = new User("user2", "123", "User 2", "last 2");
        User u3 = new User("user3", "123", "User 3", "last 3");
        User u4 = new User("user4", "123", "User 4", "last 4");
        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4));

        Post p1 = new Post("In elementum purus et lorem auctor.", u1);
        Post p2 = new Post("In molestie ante euismod feugiat aliquet. ", u1);
        Post p3 = new Post("Quisque in arcu lectus. Integer aliquet.", u2);
        Post p4 = new Post("Quisque in arcu lectus. Integer aliquet.", u3);
        Post p5 = new Post("Quisque in arcu lectus. Integer aliquet.", u4);
        Post p6 = new Post("Quisque in arcu lectus. Integer aliquet.", u2);
        Post p7 = new Post("Quisque in arcu lectus. Integer aliquet.", u4);

        postRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));

        u1.getPosts().add(p1);
        u1.getPosts().add(p2);
        u2.getPosts().add(p3);
        u2.getPosts().add(p6);
        u3.getPosts().add(p4);
        u4.getPosts().add(p5);
        u4.getPosts().add(p7);

        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4));

        PostLike like1 = new PostLike(u1, p3);
        PostLike like2 = new PostLike(u1, p4);
        PostLike like3 = new PostLike(u3, p7);

        postLikeRepository.saveAll(Arrays.asList(like1, like2, like3));

        UserFollow uf1 = new UserFollow(u2, u1);
        UserFollow uf2 = new UserFollow(u2, u4);
        UserFollow uf3 = new UserFollow(u2, u3);
        UserFollow uf4 = new UserFollow(u4, u1);

        userFollowRepository.saveAll(Arrays.asList(uf1, uf2, uf3, uf4));

        PostRT pt1 = new PostRT(p1, u2);
        PostRT pt2 = new PostRT(p7, u2);
        PostRT pt3 = new PostRT(p3, u4);

        postRTRepository.saveAll(Arrays.asList(pt1, pt2, pt3));

    }
}
