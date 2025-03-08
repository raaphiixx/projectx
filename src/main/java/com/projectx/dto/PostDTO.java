package com.projectx.dto;

import java.util.Set;

public record PostDTO(String content,
                      Long userId,
                      Set<Long> likes,
                      Set<Long> rt) {
}
