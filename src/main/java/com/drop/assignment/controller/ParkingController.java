package com.drop.assignment.controller;

import com.drop.assignment.dto.ParkingLotRequestDTO;
import com.drop.assignment.dto.ParkingLotResponseDTO;
import com.drop.assignment.model.ParkingSlot;
import com.drop.assignment.repository.ParkingSlotRepository;
import com.drop.assignment.service.ParkingService;
import com.drop.assignment.util.AppConstants;
import io.github.bucket4j.Bucket;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @Autowired
    private Bucket bucket;

    /**
     * this method used for park car
     * @param parkingLotRequestDTO
     * @return ResponseEntity<ParkingSlot>
     */
    @ApiOperation(value = "API to park car in parking lot",
            authorizations = { @Authorization(value="Bearer") },
            response = ParkingLotResponseDTO.class)
    @PostMapping("/park")
    public ResponseEntity<ParkingLotResponseDTO> park(@Valid @RequestBody ParkingLotRequestDTO parkingLotRequestDTO){

        if (bucket.tryConsume(1)){

            log.info("Request to park car with number : {}", parkingLotRequestDTO.getCarNumber());

            ParkingSlot parkingSlot = parkingService.park(parkingLotRequestDTO.getCarNumber());

            log.info("Car with number {} parked at slot id : {}", parkingLotRequestDTO.getCarNumber(), parkingSlot.getSlotId());
            return new ResponseEntity<>(new ParkingLotResponseDTO(AppConstants.CAR_IS_PARKED_SUCCESSFULLY, parkingSlot), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ParkingLotResponseDTO(AppConstants.TOO_MANY_REQUESTS), HttpStatus.TOO_MANY_REQUESTS);
    }

    /**
     * this method used return parking slot information based on slotId
     * @param slotId
     * @return ResponseEntity<ParkingSlot>
     */
    @ApiOperation(value = "API to get slot information in parking lot",
            authorizations = { @Authorization(value="Bearer") },
            response = ParkingLotResponseDTO.class)
    @GetMapping("/slot/{slotId}")
    public ResponseEntity<ParkingLotResponseDTO> slot(@PathVariable("slotId") long slotId){

        if (bucket.tryConsume(1)){

            log.info("Request to get info on slot Id : {}", slotId);

            ParkingSlot parkingSlot = parkingService.slot(slotId);
            log.info("Parking slot information  : {}", parkingSlot);

            return new ResponseEntity<>(new ParkingLotResponseDTO(AppConstants.SLOT_FETCHED_SUCCESSFULLY, parkingSlot), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ParkingLotResponseDTO(AppConstants.TOO_MANY_REQUESTS), HttpStatus.TOO_MANY_REQUESTS);
    }

    /**
     * this method used return un park car
     * @param parkingLotRequestDTO
     * @return
     */
    @ApiOperation(value = "API to un park car from parking slot",
            authorizations = { @Authorization(value="Bearer") },
            response = ParkingLotResponseDTO.class)
    @PutMapping("/unpark")
    public ResponseEntity<ParkingLotResponseDTO> unpark(@Valid @RequestBody ParkingLotRequestDTO parkingLotRequestDTO){

        if (bucket.tryConsume(1)){

            ParkingSlot parkingSlot = parkingService.unpark(parkingLotRequestDTO.getCarNumber());
            return new ResponseEntity<>(new ParkingLotResponseDTO(AppConstants.CAR_IS_UN_PARKED_SUCCESSFULLY, parkingSlot), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ParkingLotResponseDTO(AppConstants.TOO_MANY_REQUESTS), HttpStatus.TOO_MANY_REQUESTS);
    }

}
