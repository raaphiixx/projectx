package com.projectx.controllers;

import com.projectx.components.URL;
import com.projectx.dto.PostDTO;
import com.projectx.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> findAll() {
        List<PostDTO> list = postService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> findById(@PathVariable("id") Long id) {
        PostDTO result = postService.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/contentsearch")
    public ResponseEntity<List<PostDTO>> findByContentContaining(@RequestParam(value = "text") String text) {
        text = URL.decodeParam(text);
        List<PostDTO> list = postService.findByContentContaining(text);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity insert(@RequestBody PostDTO postDTO) {
        PostDTO savePost = postService.insert(postDTO);
        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{login}").buildAndExpand(savePost).toUri();
        return ResponseEntity.created(uri).build();
    }

}
