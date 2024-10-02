package com.doctorbookingapp.exception;

import com.doctorbookingapp.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler1 extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleAPIExceptions(ResourceNotFoundException e, WebRequest webRequest) {

        // Create a custom error response with timestamp, error message, and request details.
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(), // The timestamp when the error occurred.
                e.getMessage(), // The message from the thrown ResourceNotFoundException.
                webRequest.getDescription(false) // The request description, excluding the query string (false).
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
