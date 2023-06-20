package com.drop.assignment.controller;

import com.drop.assignment.model.ParkingSlot;
import com.drop.assignment.repository.ParkingSlotRepository;
import com.drop.assignment.service.ParkingService;
import io.swagger.annotations.ApiOperation;
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
        ParkingSlot parkingSlot = parkingService.park(carNumber);
        return new ResponseEntity<>(parkingSlot, HttpStatus.OK);
    }

    // TODO -- implementation
    /**
     * this method used return parking slot information based on slotId
     * @param slotId
     * @return ResponseEntity<ParkingSlot>
     */
    @GetMapping("/slot/{slotId}")
    public ResponseEntity<ParkingSlot> slot(@PathVariable("slotId") long slotId){
        ParkingSlot parkingSlot = parkingService.slot(slotId);
        return new ResponseEntity<>(parkingSlot, HttpStatus.OK);
    }
    // TODO -- implementation
    @GetMapping("/unpark/{carNumber}")
    public ResponseEntity<ParkingSlot> unpark(@PathVariable("carNumber") String carNumber){
        ParkingSlot parkingSlot = parkingService.unpark(carNumber);
        return new ResponseEntity<>(parkingSlot, HttpStatus.OK);
    }

    @GetMapping("/parking-slots")
    public List<ParkingSlot> parkingSlots(){
        return parkingSlotRepository.findAll();
    }

}
