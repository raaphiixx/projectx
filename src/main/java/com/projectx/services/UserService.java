package com.projectx.services;

import com.projectx.components.ConvertDTO;
import com.projectx.dto.AuthenticationDTO;
import com.projectx.dto.UserDTO;
import com.projectx.entites.User;
import com.projectx.entites.UserFollow;
import com.projectx.exceptions.UserNotAuthorizationException;
import com.projectx.exceptions.UserNotDeletedException;
import com.projectx.exceptions.UserNotFoundException;
import com.projectx.repositories.UserFollowRepository;
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
    private UserFollowRepository userFollowRepository;

    @Autowired
    private ConvertDTO convertDTO;

    @Autowired
    private AuthenticationService authenticationService;


    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(convertDTO::convertUserDTO).collect(Collectors.toList());
    }

    public UserDTO findById(Long id) throws UserNotFoundException {
        User result = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return convertDTO.convertUserDTO(result);
    }

    public User findByIdEntity(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public Set<UserDTO> findByEmail(String text) throws UserNotFoundException {
        Set<User> result = userRepository.findByEmailIgnoreCase(text);
        if(result.isEmpty()) {
            throw new UserNotFoundException("Email not found!");
        }
        return result.stream().map(convertDTO::convertUserDTO).collect(Collectors.toSet());
    }

    public Set<Long> getUserFollowing(Long userId) {
        UserDTO findUserId = findById(userId);
        return findUserId.followingId();
    }

    public Set<Long> getUserFollowed(Long userId) {
        UserDTO findUserId = findById(userId);
        return findUserId.followedId();
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

    public Boolean deleteUser(AuthenticationDTO data) throws UserNotDeletedException {

        Boolean checkInfo = authenticationService.checkInfo(data);

        Long authenticateUserId = getAuthenticationUserId();

        User userDelete =
                userRepository.findById(authenticateUserId).orElseThrow(UserNotFoundException::new);

        if(!checkInfo) {
            throw new UserNotDeletedException();
        } else {
            userRepository.delete(userDelete);
            return true;
        }
    }

    public void followUser(Long userSource, Long userTarget) {
        User u1 = findByIdEntity(userSource);
        User u2 = findByIdEntity(userTarget);

        UserFollow follow = new UserFollow(u1, u2);
        userFollowRepository.save(follow);
    }

    public void unfollowUser(Long userSource, Long userTarget) {
        User u1 = findByIdEntity(userSource);
        User u2 = findByIdEntity(userTarget);
        List<UserFollow> checkFollow = userFollowRepository.findByFollowedAndFollowing(u1, u2);

        if(!checkFollow.isEmpty()) {
            userFollowRepository.delete(checkFollow.get(0));
        } else {
            throw new RuntimeException("Follow not found!");
        }
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
