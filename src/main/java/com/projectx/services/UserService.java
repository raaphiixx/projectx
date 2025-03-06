package com.projectx.services;

import com.projectx.dto.UserDTO;
import com.projectx.entites.Post;
import com.projectx.entites.User;
import com.projectx.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO convertUserDTO(User user) {
        Set<Long> postIds = user.getPosts().stream().map(Post::getId).collect(Collectors.toSet());
        Set<Long> followedIds =
                user.getFollowing().stream().map(userFollow -> userFollow.getFollowed().getId()).collect(Collectors.toSet());

        return new UserDTO(user.getName(), user.getLname(), postIds, followedIds);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(this::convertUserDTO).collect(Collectors.toList());
    }

}
