package com.projectx.services;

import com.projectx.components.ConvertDTO;
import com.projectx.dto.AuthenticationDTO;
import com.projectx.dto.RegisterDTO;
import com.projectx.entites.User;
import com.projectx.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConvertDTO convertDTO;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    public String login(AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(),
                authenticationDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();

        RegisterDTO userConverter = convertDTO.convertRegisterDTO(user);

        return tokenService.generateToken(userConverter);
    }

    public Boolean checkInfo(AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(),
                authenticationDTO.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        return auth.isAuthenticated();
    }

    public RegisterDTO register(RegisterDTO registerDTO) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User user = new User();
        user.setName(registerDTO.name());
        user.setLname(registerDTO.lname());
        user.setLogin(registerDTO.login());
        user.setPassword(encryptedPassword);
        user.setEmail(registerDTO.email());
        userRepository.save(user);
        return convertDTO.convertRegisterDTO(user);
    }
}
