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

        /*
           fetch parking slot by car number
         */
        ParkingSlot parkedVehicle =  parkingSlotRepository.findByCarNumber(carNumber);
        if(parkedVehicle != null){
            log.error("Car with number {} is already parked in this parking lot", carNumber);
            throw new CarIsAlreadyParkedException();
        }

        /*
           fetch parking slot where slot is available
         */
        ParkingSlot parkingSlot = parkingSlotRepository.findFirstByIsAvailableTrue();

        /*
           if no slot found with carName null, i.e. parking slot is full, then throw ParkingSlotUnAvailableException
         */
        if(parkingSlot == null){
            log.error("Parking slot is not available");
            throw new ParkingSlotUnAvailableException();
        }

        /*
            If slot available park the car
         */
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
        /*
           Find parking slot by slot id
         */
        Optional<ParkingSlot> parkingSlot = parkingSlotRepository.findById(slotId);

        if(parkingSlot.isPresent()){
            return parkingSlot.get();
        }

        /*
            throw InvalidSlotIdException if parking slot is not found
         */
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

        /*
           Find parking slot by car number
         */
        ParkingSlot parkingSlot =  parkingSlotRepository.findByCarNumber(carNumber);

        /*
            If car is not found in parking slot throw CarIsNotParkedInParkingLotException
         */
        if(parkingSlot == null){
            throw new CarIsNotParkedInParkingLotException();
        }

        /*
            If car is available on parking lot un park the car
         */
        parkingSlot.setAvailable(true);
        parkingSlot.setCarNumber(null);
        return parkingSlotRepository.save(parkingSlot);
    }
}
