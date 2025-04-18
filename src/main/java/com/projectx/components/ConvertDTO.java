package com.projectx.components;

import com.projectx.dto.*;
import com.projectx.entites.Post;
import com.projectx.entites.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ConvertDTO {

    public UserDTO convertUserDTO(User user) {
        Set<Long> postIds = user.getPosts().stream().map(Post::getId).collect(Collectors.toSet());
        Set<Long> followedIds =
                user.getFollowing().stream().map(userFollow -> userFollow.getFollowed().getId()).collect(Collectors.toSet());
        Set<Long> followingIds =
                user.getFollowing().stream().map(userFollow -> userFollow.getFollowing().getId()).collect(Collectors.toSet());

        return new UserDTO(user.getName(), user.getLname(), postIds, followingIds, followedIds);
    }

    public RegisterDTO convertRegisterDTO(User user) {
        return new RegisterDTO(user.getName(), user.getLname(), user.getLogin(),
                user.getPassword(), user.getEmail());
    }

    public PostDTO convertPostDTO(Post post) {
        Set<Long> likesIds =
                post.getLikes().stream().map(postLike -> postLike.getUser().getId()).collect(Collectors.toSet());
        Set<Long> rtIds =
                post.getRT().stream().map(postRT -> postRT.getUserRT().getId()).collect(Collectors.toSet());
        return new PostDTO(post.getContent(), post.getOwner().getId(), likesIds, rtIds);
    }

    public FollowingResponseDTO convertUserFollowingDTO(Set<Long> followingIds) {
        return new FollowingResponseDTO(followingIds);
    }

    public FollowedResponseDTO convertUserFollowedDTO(Set<Long> followedIds) {
        return new FollowedResponseDTO(followedIds);
    }
}
