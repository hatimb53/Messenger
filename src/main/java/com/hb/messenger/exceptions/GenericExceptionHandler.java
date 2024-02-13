package com.hb.messenger.exceptions;

import com.hb.messenger.models.enums.Status;
import com.hb.messenger.models.response.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({MessengerException.class})
  public ResponseEntity<?> handleMessengerException(MessengerException ex) {
    return ResponseEntity.status(ex.getErrorCode().getStatus()).body(
        GenericResponse.builder().status(Status.FAILURE.getName()).message(ex.getMessage())
            .build());
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<?> handleMessengerException(Exception ex) {
    return ResponseEntity.status(500).body(
        GenericResponse.builder().status(Status.FAILURE.getName()).message(ex.getMessage())
            .build());
  }
}

