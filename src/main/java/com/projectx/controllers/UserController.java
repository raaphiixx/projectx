package com.projectx.controllers;

import com.projectx.components.URL;
import com.projectx.dto.UserDTO;
import com.projectx.exceptions.UserNotAuthorizationException;
import com.projectx.services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity update(@RequestBody UserDTO userDTO) throws BadRequestException {
        UserDTO userUpdate = userService.update(userDTO);
        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{login}").buildAndExpand(userUpdate).toUri();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
