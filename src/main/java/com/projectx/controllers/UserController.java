package com.projectx.controllers;

import com.projectx.components.URL;
import com.projectx.dto.UserDTO;
import com.projectx.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        UserDTO result = userService.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/emailsearch")
    public ResponseEntity<Set<UserDTO>> findByEmail(@RequestParam(value = "text") String text) {
        text = URL.decodeParam(text);
        Set<UserDTO> result = userService.findByEmail(text);
        return ResponseEntity.ok().body(result);
    }
}
