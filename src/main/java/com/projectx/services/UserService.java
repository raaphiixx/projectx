package com.projectx.services;

import com.projectx.components.ConvertDTO;
import com.projectx.dto.UserDTO;
import com.projectx.entites.User;
import com.projectx.exceptions.UserNotAuthorizationException;
import com.projectx.exceptions.UserNotFoundException;
import com.projectx.repositories.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public UserDTO update(UserDTO data) throws BadRequestException {
        Long authenticateUserId = getAuthenticationUserId();

        User userUpdate =
                userRepository.findById(authenticateUserId).orElseThrow(UserNotFoundException::new);
        userUpdate.setName(data.name());
        userUpdate.setLname(data.lname());

        userRepository.save(userUpdate);
        return convertDTO.convertUserDTO(userUpdate);
    }

    private Long getAuthenticationUserId() throws UserNotAuthorizationException {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ((User) userDetails).getId();
        } else {
            throw new UserNotAuthorizationException();
        }
    }

}
