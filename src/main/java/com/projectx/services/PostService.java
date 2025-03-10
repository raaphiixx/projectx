package com.projectx.services;

import com.projectx.components.ConvertDTO;
import com.projectx.dto.PostDTO;
import com.projectx.entites.Post;
import com.projectx.exceptions.PostNotFoundException;
import com.projectx.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ConvertDTO convertDTO;

    public List<PostDTO> findAll() {
        return postRepository.findAll().stream().map(convertDTO::convertPostDTO).collect(Collectors.toList());
    }

    public PostDTO findById(Long id) throws PostNotFoundException {
        Post result = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return convertDTO.convertPostDTO(result);
    }

}
