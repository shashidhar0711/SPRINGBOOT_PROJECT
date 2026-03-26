package com.scaler.mslearning.Advice;

import com.scaler.mslearning.Dto.ExceptionResponseDto;
import com.scaler.mslearning.Exception.CategoryNotFound;
import com.scaler.mslearning.Exception.ProductNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<ExceptionResponseDto> handleProductNotFoundException() {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setMessage("Product Not Found");
        exceptionResponseDto.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<ExceptionResponseDto> handleCategoryNotFoundException() {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setMessage("Category Not Found");
        exceptionResponseDto.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }
}
