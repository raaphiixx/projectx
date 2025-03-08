package com.projectx.services;

import com.projectx.components.ConvertDTO;
import com.projectx.dto.UserDTO;
import com.projectx.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConvertDTO convertDTO;


    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(convertDTO::convertUserDTO).collect(Collectors.toList());
    }

}
