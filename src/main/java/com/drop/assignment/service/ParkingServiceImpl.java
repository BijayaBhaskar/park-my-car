package com.drop.assignment.service;

import com.drop.assignment.exception.CarIsAlreadyParkedException;
import com.drop.assignment.exception.ParkingSlotUnAvailableException;
import com.drop.assignment.model.ParkingSlot;
import com.drop.assignment.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for parking system
 * @author Bijaya Bhaskar Swain
 *
 */
@Service
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
        ParkingSlot parkedVehicle =  parkingSlotRepository.findByCarNumber(carNumber);
        if(parkedVehicle != null){
            throw new CarIsAlreadyParkedException();
        }
        ParkingSlot parkingSlot = parkingSlotRepository.findFirstByCarNumberIsNull();
        if(parkingSlot == null){
            throw new ParkingSlotUnAvailableException();
        }
        parkingSlot.setCarNumber(carNumber);
        return parkingSlotRepository.save(parkingSlot);
    }

    // TODO -- implementation
    @Override
    public ParkingSlot slot(long id) {
        return null;
    }

    @Override
    public ParkingSlot unpark(String vehicleNumber) {
        return null;
    }
}
