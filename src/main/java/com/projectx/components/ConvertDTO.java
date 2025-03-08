package com.projectx.components;

import com.projectx.dto.PostDTO;
import com.projectx.dto.UserDTO;
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

        return new UserDTO(user.getName(), user.getLname(), postIds, followedIds);
    }

    public PostDTO convertPostDTO(Post post) {
        Set<Long> likesIds =
                post.getLikes().stream().map(postLike -> postLike.getUser().getId()).collect(Collectors.toSet());
        Set<Long> rtIds =
                post.getRT().stream().map(postRT -> postRT.getUserRT().getId()).collect(Collectors.toSet());
        return new PostDTO(post.getContent(), post.getOwner().getId(), likesIds, rtIds);
    }
}
