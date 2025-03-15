package com.projectx.controllers;

import com.projectx.dto.AuthenticationDTO;
import com.projectx.dto.LoginResponseDTO;
import com.projectx.dto.RegisterDTO;
import com.projectx.repositories.UserRepository;
import com.projectx.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data) {
        String token = authenticationService.login(data);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data) {
        if(userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        } else {
            RegisterDTO users = authenticationService.register(data);
            return ResponseEntity.ok().build();
        }
    }



}
