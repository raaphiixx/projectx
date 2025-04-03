package com.projectx.services;

import com.projectx.components.ConvertDTO;
import com.projectx.dto.AuthenticationDTO;
import com.projectx.dto.PostDTO;
import com.projectx.entites.Post;
import com.projectx.entites.User;
import com.projectx.exceptions.PostNotCreatedException;
import com.projectx.exceptions.PostNotFoundException;
import com.projectx.exceptions.UserNotFoundException;
import com.projectx.repositories.PostRepository;
import com.projectx.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConvertDTO convertDTO;

    @Autowired
    private AuthenticationService authenticationService;

    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(convertDTO::convertPostDTO).collect(Collectors.toList());
    }

    public PostDTO findById(Long id) throws PostNotFoundException {
        Post result = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return convertDTO.convertPostDTO(result);
    }

    public List<PostDTO> findByContentContaining(String text) throws PostNotFoundException {
        List<Post> result = postRepository.findByContentContainingIgnoreCase(text);
        if(result.isEmpty()) {
            throw new PostNotFoundException("Content not found!");
        }
        return result.stream().map(convertDTO::convertPostDTO).collect(Collectors.toList());
    }

    public PostDTO insert(PostDTO postDTO) throws PostNotCreatedException {
         User owner = userRepository.findById(postDTO.userId()).orElseThrow(UserNotFoundException::new);

         if(owner != null) {
            Post newPost = new Post();
            newPost.setContent(postDTO.content());
            newPost.setOwner(owner);
            postRepository.save(newPost);
            return convertDTO.convertPostDTO(newPost);
         } else {
             throw new PostNotCreatedException();
         }
    }

    public Boolean delete(AuthenticationDTO data, Long postId) {
        Boolean checkinfo = authenticationService.checkInfo(data);
        System.out.println("POST ID: " + postId);

        Post postSearch = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        User userSearch = (User) userRepository.findByLogin(data.login());
        System.out.println("USER ID: " + userSearch.getId());

        if (!postSearch.getOwner().getId().equals(userSearch.getId())) {
            throw new UserNotFoundException();
        }
        postRepository.delete(postSearch);

        return true;
    }
}
