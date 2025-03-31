package com.projectx.dto;

public record SuccessResponseDTO(String msg) {

    public SuccessResponseDTO(String msg) {
        this.msg = msg;
    }
}
