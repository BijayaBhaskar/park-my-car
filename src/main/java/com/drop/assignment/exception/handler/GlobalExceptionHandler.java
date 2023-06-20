package com.drop.assignment.exception.handler;

import com.drop.assignment.exception.CarIsAlreadyParkedException;
import com.drop.assignment.exception.InvalidSlotIdException;
import com.drop.assignment.exception.ParkingSlotUnAvailableException;
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
    public ResponseEntity<String> handleParkingSlotUnAvailableException(){
        return new ResponseEntity<>(ApplicationConstants.PARKING_NOT_AVAILABLE, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarIsAlreadyParkedException.class)
    public ResponseEntity<String> handleCarIsAlreadyParkedException(){
        return new ResponseEntity<>(ApplicationConstants.CAR_IS_ALREADY_PARKED, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSlotIdException.class)
    public ResponseEntity<String> handleInvalidSlotIdException(){
        return new ResponseEntity<>(ApplicationConstants.SLOT_ID_IS_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
