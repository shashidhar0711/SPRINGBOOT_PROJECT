package com.scaler.mslearning.Dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponseDto {
    private String message;
    private HttpStatus status;
}
