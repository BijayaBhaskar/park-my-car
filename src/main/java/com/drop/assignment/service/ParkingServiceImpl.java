package com.drop.assignment.service;

import com.drop.assignment.exception.CarIsAlreadyParkedException;
import com.drop.assignment.exception.CarIsNotParkedInParkingLotException;
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
     * This method used to arrange available slot and park car based on car number
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
            log.error("Parking slot is not available");
            throw new ParkingSlotUnAvailableException();
        }
        parkingSlot.setAvailable(false);
        parkingSlot.setCarNumber(carNumber);
        return parkingSlotRepository.save(parkingSlot);
    }

    /**
     * this method used get slot information based on slot id
     * @param slotId
     * @return
     */
    @Override
    public ParkingSlot slot(long slotId) {
        Optional<ParkingSlot> parkingSlot = parkingSlotRepository.findById(slotId);
        if(parkingSlot.isPresent()){
            return parkingSlot.get();
        }
        log.error("Slot id {} is not found", slotId);
        throw new InvalidSlotIdException();
    }

    /**
     * This method used to unPark car based on car number
     * @param carNumber
     * @return ParkingSlot
     */
    @Override
    public ParkingSlot unpark(String carNumber) {
        ParkingSlot parkingSlot =  parkingSlotRepository.findByCarNumber(carNumber);
        if(parkingSlot == null){
            throw new CarIsNotParkedInParkingLotException();
        }
        parkingSlot.setAvailable(true);
        parkingSlot.setCarNumber(null);
        return parkingSlotRepository.save(parkingSlot);
    }
}
