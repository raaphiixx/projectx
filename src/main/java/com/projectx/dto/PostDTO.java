package com.projectx.dto;

import java.io.Serializable;
import java.util.Set;

public record PostDTO(String content,
                      Long userId,
                      Set<Long> likes) implements Serializable {
}
