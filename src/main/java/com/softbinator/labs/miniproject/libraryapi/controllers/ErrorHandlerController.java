package com.softbinator.labs.miniproject.libraryapi.controllers;

import com.softbinator.labs.miniproject.libraryapi.exceptions.AuthorNotFoundException;
import com.softbinator.labs.miniproject.libraryapi.exceptions.BadRequestException;
import com.softbinator.labs.miniproject.libraryapi.exceptions.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class, AuthorNotFoundException.class, BookNotFoundException.class})
    public ResponseEntity<String> handleBadRequest(BadRequestException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getExternalMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class, Error.class})
    public ResponseEntity<String> handleInternalServerError(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>("Internal server error!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
