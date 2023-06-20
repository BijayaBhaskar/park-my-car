package com.drop.assignment.service;

import com.drop.assignment.exception.CarIsAlreadyParkedException;
import com.drop.assignment.exception.InvalidSlotIdException;
import com.drop.assignment.exception.ParkingSlotUnAvailableException;
import com.drop.assignment.model.ParkingSlot;
import com.drop.assignment.repository.ParkingSlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for parking system
 * @author Bijaya Bhaskar Swain
 *
 */
@Service
@Slf4j
public class ParkingServiceImpl implements ParkingService{

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    /**
     * park method used to check available slot and assign that slot to requested car
     *
     * @param carNumber
     * @return
     *
     */
    @Override
    public ParkingSlot park(String carNumber) {
        log.info("Parking car with number : {}", carNumber);
        ParkingSlot parkedVehicle =  parkingSlotRepository.findByCarNumber(carNumber);
        if(parkedVehicle != null){
            log.error("Car with number {} is already parked in this parking lot", carNumber);
            throw new CarIsAlreadyParkedException();
        }
        ParkingSlot parkingSlot = parkingSlotRepository.findFirstByCarNumberIsNull();
        if(parkingSlot == null){
            log.error("Parking lot is not available");
            throw new ParkingSlotUnAvailableException();
        }
        parkingSlot.setAvailable(false);
        parkingSlot.setCarNumber(carNumber);
        return parkingSlotRepository.save(parkingSlot);
    }

    /**
     *
     * @param slotId
     * @return
     */
    @Override
    public ParkingSlot getSlotInfo(long slotId) {
        Optional<ParkingSlot> parkingSlot = parkingSlotRepository.findById(slotId);
        if(parkingSlot.isPresent()){
            return parkingSlot.get();
        }
        log.error("Slot id {} is not found", slotId);
        throw new InvalidSlotIdException();
    }

    @Override
    public ParkingSlot unpark(String vehicleNumber) {
        return null;
    }
}
