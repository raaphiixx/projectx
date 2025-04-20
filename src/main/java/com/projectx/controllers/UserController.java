package com.projectx.controllers;

import com.projectx.components.URL;
import com.projectx.dto.*;
import com.projectx.exceptions.UserNotDeletedException;
import com.projectx.services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/{userId}/following")
    public ResponseEntity<Set<Long>> getUserFollowing(@PathVariable("userId") Long userId) {
        Set<Long> list = userService.getUserFollowing(userId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{userId}/followed")
    public ResponseEntity<Set<Long>> getUserFollowed(@PathVariable("userId") Long userId) {
        Set<Long> list = userService.getUserFollowed(userId);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity update(@RequestBody UserDTO userDTO) throws BadRequestException {
        UserDTO userUpdate = userService.update(userDTO);
        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{login}").buildAndExpand(userUpdate).toUri();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{userSourceId}/follow")
    public ResponseEntity<Void> followUser(@PathVariable ("userSourceId") Long userSourceId,
                                     @RequestBody UserFollowDTO userTargetId) {
        userService.followUser(userSourceId, userTargetId.userIdTarget());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/like")
    public ResponseEntity<Void> postLike(@PathVariable ("userId") Long userId,
                                         @RequestBody PostLikeDTO postId) {
        PostLikeDTO likeDTO = new PostLikeDTO(userId, postId.postId());
        userService.likePost(likeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody @Validated AuthenticationDTO data) throws UserNotDeletedException {
        Boolean deleted = userService.deleteUser(data);
        if(deleted) {
            SuccessResponseDTO message = new SuccessResponseDTO("User deleted successfully");
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Delete Failed");
        }
    }

    @DeleteMapping("/{userSourceId}/follow/{userTargetId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long userSourceId,
                                               @PathVariable Long userTargetId) {
        try {
            UserFollowDeleteDTO userDeleteDTO = new UserFollowDeleteDTO(userTargetId);
            userService.unfollowUser(userTargetId, userSourceId);
            return ResponseEntity.ok("User " + userSourceId + " unfollowed user " + userTargetId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{userId}/like/{postId}")
    public ResponseEntity<String> removeLikePost(@PathVariable Long userId,
                                               @PathVariable Long postId) {
        try {
            PostRemoveLikeDTO postRemoveLikeDTO = new PostRemoveLikeDTO(postId);
            userService.unlikePost(userId, postId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
