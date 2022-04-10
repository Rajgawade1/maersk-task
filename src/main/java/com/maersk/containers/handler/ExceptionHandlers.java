package com.maersk.containers.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlers {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String serverExceptionHandler(Exception ex) {
    log.error(ex.getMessage(), ex);
    if(ex instanceof WebExchangeBindException){
      List<ObjectError> errors = ((WebExchangeBindException) ex).getBindingResult().getAllErrors().stream().collect(Collectors.toList());
      return errors.toString();
    }else{
      return "HTTP 500 INTERNAL SERVER ERROR";
    }

  }
}