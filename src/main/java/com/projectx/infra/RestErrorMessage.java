package com.projectx.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage {

    private Instant timeStamp;
    private Integer status;
    private String message;
}
