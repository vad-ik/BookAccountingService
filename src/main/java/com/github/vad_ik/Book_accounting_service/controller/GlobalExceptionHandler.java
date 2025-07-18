package com.github.vad_ik.Book_accounting_service.controller;

import com.github.vad_ik.Book_accounting_service.model.exeption.AppError;
import com.github.vad_ik.Book_accounting_service.model.exeption.DataIncorrectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //данный класс вылавливает все DataIncorrectException и отправляет их как ответ на запрос
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(DataIncorrectException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
