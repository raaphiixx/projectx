package com.projectx.configurations;

import com.projectx.entites.Post;
import com.projectx.entites.User;
import com.projectx.repositories.PostRepository;
import com.projectx.repositories.UserRepository;
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

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(null, "user1", "123", "User 1", "last 1");
        User u2 = new User(null, "user2", "123", "User 2", "last 2");
        User u3 = new User(null, "user3", "123", "User 3", "last 3");
        User u4 = new User(null, "user4", "123", "User 4", "last 4");
        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4));

        Post p1 = new Post(null, "In elementum purus et lorem auctor.", u1);
        Post p2 = new Post(null,"In molestie ante euismod feugiat aliquet. ", u1);
        Post p3 = new Post(null,"Quisque in arcu lectus. Integer aliquet.", u2);
        Post p4 = new Post(null,"Quisque in arcu lectus. Integer aliquet.", u3);
        Post p5 = new Post(null,"Quisque in arcu lectus. Integer aliquet.", u4);
        Post p6 = new Post(null,"Quisque in arcu lectus. Integer aliquet.", u2);
        Post p7 = new Post(null,"Quisque in arcu lectus. Integer aliquet.", u4);

        postRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));

        u1.getPosts().add(p1);
        u1.getPosts().add(p2);
        u2.getPosts().add(p3);
        u2.getPosts().add(p6);
        u3.getPosts().add(p4);
        u4.getPosts().add(p5);
        u4.getPosts().add(p7);

        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4));
    }
}
