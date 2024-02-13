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
  public ResponseEntity<?> handleMessengerException(Exception ex) {
    int status=500;
    if(ex instanceof MessengerException messengerException) {
      status=messengerException.getErrorCode().getStatus();

    }
    return ResponseEntity.status(status).body(
        GenericResponse.builder().status(Status.FAILURE.getName()).message(ex.getMessage())
            .build());

  }

}

