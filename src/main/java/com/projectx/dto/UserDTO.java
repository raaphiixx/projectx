package com.projectx.dto;

import java.io.Serializable;
import java.util.Set;

public record UserDTO(String name,
                      String lname,
                      Set<Long> postId,
                      Set<Long> followingId,
                      Set<Long> followedId) implements Serializable{
}
