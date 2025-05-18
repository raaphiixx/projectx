package com.projectx.dto;

import java.io.Serializable;

public record UserFollowDeleteDTO(Long followedUserId) implements Serializable {
}
