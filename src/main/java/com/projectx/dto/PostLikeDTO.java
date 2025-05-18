package com.projectx.dto;

import java.io.Serializable;

public record PostLikeDTO(Long userId, Long postId) implements Serializable {
}
