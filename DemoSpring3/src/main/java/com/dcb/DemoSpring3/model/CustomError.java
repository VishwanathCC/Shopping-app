package com.dcb.DemoSpring3.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class CustomError {
    private HttpStatus httpStatus;
    private String message;
}
