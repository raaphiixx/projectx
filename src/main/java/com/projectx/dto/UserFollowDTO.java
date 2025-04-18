package com.projectx.dto;

import org.antlr.v4.runtime.misc.NotNull;

public record UserFollowDTO(
        @NotNull Long userIdTarget
) {}
