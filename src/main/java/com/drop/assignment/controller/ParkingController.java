package com.drop.assignment.controller;

import com.drop.assignment.dto.ResponseDto;
import com.drop.assignment.model.ParkingSlot;
import com.drop.assignment.repository.ParkingSlotRepository;
import com.drop.assignment.service.ParkingService;
import com.drop.assignment.util.AppConstants;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * Controller class for parking system
 * @author Bijaya Bhaskar Swain
 *
 */
@RestController
@Slf4j
public class ParkingController {

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private ParkingService parkingService;

    private final Bucket bucket;

    ParkingController(){
        // 50 tokens in 1 minute
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    /**
     * this method used for park car
     * @param carNumber
     * @return ResponseEntity<ParkingSlot>
     */
    @ApiOperation(value = "API to park car in parking lot",
            response = ResponseDto.class)
    @GetMapping("/park/{carNumber}")
    public ResponseEntity<ResponseDto>  park(@PathVariable("carNumber") String carNumber){
        if (bucket.tryConsume(1)){
            log.info("Request to park car with number : {}", carNumber);
            ParkingSlot parkingSlot = parkingService.park(carNumber);
            log.info("Car with number {} parked at slot id : {}", carNumber, parkingSlot.getSlotId());

            return new ResponseEntity<>(new ResponseDto(AppConstants.CAR_IS_PARKED_SUCCESSFULLY, parkingSlot), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto(AppConstants.TOO_MANY_REQUESTS), HttpStatus.TOO_MANY_REQUESTS);
    }

    /**
     * this method used return parking slot information based on slotId
     * @param slotId
     * @return ResponseEntity<ParkingSlot>
     */
    @ApiOperation(value = "API to get slot information in parking lot",
            response = ResponseDto.class)
    @GetMapping("/slot/{slotId}")
    public ResponseEntity<ResponseDto> slot(@PathVariable("slotId") long slotId){
        if (bucket.tryConsume(1)){
            log.info("Request to get info on slot Id : {}", slotId);
            ParkingSlot parkingSlot = parkingService.slot(slotId);
            log.info("Parking slot information  : {}", parkingSlot);

            return new ResponseEntity<>(new ResponseDto(AppConstants.SLOT_FETCHED_SUCCESSFULLY, parkingSlot), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto(AppConstants.TOO_MANY_REQUESTS), HttpStatus.TOO_MANY_REQUESTS);
    }

    /**
     * this method used return un park car
     * @param carNumber
     * @return
     */
    @ApiOperation(value = "API to un park car in parking lot",
            response = ResponseDto.class)
    @GetMapping("/unpark/{carNumber}")
    public ResponseEntity<ResponseDto> unpark(@PathVariable("carNumber") String carNumber){
        if (bucket.tryConsume(1)){
            ParkingSlot parkingSlot = parkingService.unpark(carNumber);
            return new ResponseEntity<>(new ResponseDto(AppConstants.CAR_IS_UN_PARKED_SUCCESSFULLY, parkingSlot), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto(AppConstants.TOO_MANY_REQUESTS), HttpStatus.TOO_MANY_REQUESTS);
    }

}
