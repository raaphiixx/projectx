package com.projectx.configurations;

import com.projectx.components.RandomNumberGenerator;
import com.projectx.entites.Post;
import com.projectx.entites.PostLike;
import com.projectx.entites.User;
import com.projectx.entites.UserFollow;
import com.projectx.repositories.PostLikeRepository;
import com.projectx.repositories.PostRepository;
import com.projectx.repositories.UserFollowRepository;
import com.projectx.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RandomNumberGenerator randomGenerator;

    private static final int totalUsers = 1000;
    private static final int totalPosts = 10000;
    private static final int followsPerUser = 4;
    private static final int likesPerPost = 5;

    @Override
    public void run(String... args) throws Exception {

        List<User> users = new ArrayList<>();
        for (int i = 1; i <= totalUsers; i++) {
            String password = passwordEncoder.encode("password"+i);
            User user = new User(
                "user" + i,
                password,
                "Name" + i,
                "LName" + i,
                "user" + i + "@example.com"
            );
            users.add(user);
        }
        userRepository.saveAll(users);

        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= totalPosts; i++) {
            int randomUserId = randomGenerator.getRandomNumber(totalUsers);
            User user = users.get(randomUserId - 1);
            
            Post post = new Post();
            post.setContent("This is post number " + i + " by user " + randomUserId);
            post.setOwner(user);
            posts.add(post);
        }
        postRepository.saveAll(posts);

        List<UserFollow> follows = new ArrayList<>();
        for (User user : users) {
            Set<Integer> followedIds = new HashSet<>();
            
            while (followedIds.size() < followsPerUser) {
                int followedId = randomGenerator.getRandomNumber(totalUsers);
                
                if (followedId != users.indexOf(user) + 1 && followedIds.add(followedId)) {
                    UserFollow follow = new UserFollow();
                    follow.setFollowing(user);
                    follow.setFollowed(users.get(followedId - 1));
                    follows.add(follow);
                }
            }
        }
        userFollowRepository.saveAll(follows);

        List<PostLike> likes = new ArrayList<>();
        for (Post post : posts) {
            Set<Integer> likedByUsers = new HashSet<>();
            
            while (likedByUsers.size() < likesPerPost) {
                int userId = randomGenerator.getRandomNumber(totalUsers);
                
                if (likedByUsers.add(userId)) {
                    PostLike like = new PostLike();
                    like.setUser(users.get(userId - 1));
                    like.setPost(post);
                    likes.add(like);
                }
            }
        }
        postLikeRepository.saveAll(likes);
    }
        
}
