package com.drop.assignment.exception;

import com.drop.assignment.util.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * class for globalExceptionHandler
 * @author Bijaya Bhaskar Swain
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParkingSlotUnAvailableException.class)
    public ResponseEntity<String> handle(){
        return new ResponseEntity<>(ApplicationConstants.PARKING_NOT_AVAILABLE, HttpStatus.NOT_FOUND);
    }
}
