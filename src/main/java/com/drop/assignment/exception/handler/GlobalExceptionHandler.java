package com.drop.assignment.exception.handler;

import com.drop.assignment.exception.CarIsAlreadyParkedException;
import com.drop.assignment.exception.CarIsNotParkedInParkingLotException;
import com.drop.assignment.exception.InvalidSlotIdException;
import com.drop.assignment.exception.ParkingSlotUnAvailableException;
import com.drop.assignment.util.AppConstants;
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
        return new ResponseEntity<>(AppConstants.PARKING_LOT_IS_FULL, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarIsAlreadyParkedException.class)
    public ResponseEntity<String> handleCarIsAlreadyParkedException(){
        return new ResponseEntity<>(AppConstants.CAR_IS_ALREADY_PARKED, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSlotIdException.class)
    public ResponseEntity<String> handleInvalidSlotIdException(){
        return new ResponseEntity<>(AppConstants.SLOT_ID_IS_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarIsNotParkedInParkingLotException.class)
    public ResponseEntity<String> handleCarIsNotParkedInParkingLotException(){
        return new ResponseEntity<>(AppConstants.CAR_IS_NOT_AVAILABLE_IN_PARKING_LOT, HttpStatus.NOT_FOUND);
    }
}
