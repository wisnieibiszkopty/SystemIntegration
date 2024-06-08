package com.project.steamtwitchintegration.exceptions;

import com.fasterxml.jackson.core.io.JsonEOFException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.DeserializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({UserExistsException.class, UserDoesntExistException.class})
    public ResponseEntity<Object> handleUserException(UserExistsException e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MalformedJwtException.class, DeserializationException.class, JsonEOFException.class})
    public ResponseEntity<Object> handleBadJwtException(MalformedJwtException e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
