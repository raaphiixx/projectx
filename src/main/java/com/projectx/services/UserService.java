package com.projectx.services;

import com.projectx.components.ConvertDTO;
import com.projectx.dto.UserDTO;
import com.projectx.entites.User;
import com.projectx.exceptions.UserNotFoundException;
import com.projectx.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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

    public UserDTO findById(Long id) throws UserNotFoundException {
        User result = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return convertDTO.convertUserDTO(result);
    }

    public Set<UserDTO> findByEmail(String text) throws UserNotFoundException {
        Set<User> result = userRepository.findByEmailIgnoreCase(text);
        if(result.isEmpty()) {
            throw new UserNotFoundException("Email not found!");
        }
        return result.stream().map(convertDTO::convertUserDTO).collect(Collectors.toSet());
    }

}
