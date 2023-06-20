package com.drop.assignment.controller;

import com.drop.assignment.model.ParkingSlot;
import com.drop.assignment.repository.ParkingSlotRepository;
import com.drop.assignment.service.ParkingService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    //TODO -- implementation
    /**
     * this method used for park vehicle
     * @param carNumber
     * @return ResponseEntity<ParkingSlot>
     */
    @ApiOperation(value = "park car in parking lot",
            response = ParkingSlot.class)
    @GetMapping("/park/{carNumber}")
    public ResponseEntity<ParkingSlot>  park(@PathVariable("carNumber") String carNumber){
        log.info("Request to park car with number : {}", carNumber);
        ParkingSlot parkingSlot = parkingService.park(carNumber);
        log.info("Car with number {} parked at slot id : {}", carNumber, parkingSlot.getSlotId());
        return new ResponseEntity<>(parkingSlot, HttpStatus.OK);
    }

    /**
     * this method used return parking slot information based on slotId
     * @param slotId
     * @return ResponseEntity<ParkingSlot>
     */
    @GetMapping("/slot/{slotId}")
    public ResponseEntity<ParkingSlot> slot(@PathVariable("slotId") long slotId){
        log.info("Request to get info on slot Id : {}", slotId);
        ParkingSlot parkingSlot = parkingService.getSlotInfo(slotId);
        log.info("Parking slot information  : {}", parkingSlot);
        return new ResponseEntity<>(parkingSlot, HttpStatus.OK);
    }
    // TODO -- implementation
    @GetMapping("/unpark/{carNumber}")
    public ResponseEntity<ParkingSlot> unpark(@PathVariable("carNumber") String carNumber){
        ParkingSlot parkingSlot = parkingService.unPark(carNumber);
        return new ResponseEntity<>(parkingSlot, HttpStatus.OK);
    }

    /**
     * get all parking slot information
     * @return List<ParkingSlot>
     */
    @GetMapping("/all-slots")
    public List<ParkingSlot> parkingSlots(){
        return parkingSlotRepository.findAll();
    }

}
