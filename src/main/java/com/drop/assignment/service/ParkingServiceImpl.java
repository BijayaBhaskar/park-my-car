package com.drop.assignment.service;

import com.drop.assignment.model.ParkingSlot;
import org.springframework.stereotype.Service;

/**
 * Service class for parking system
 * @author Bijaya Bhaskar Swain
 *
 */
@Service
public class ParkingServiceImpl implements ParkingService{

    //TODO : Add park logic
    @Override
    public ParkingSlot park(String vehicleNumber) {
        return null;
    }

    @Override
    public ParkingSlot slot(long id) {
        return null;
    }

    @Override
    public ParkingSlot unpark(String vehicleNumber) {
        return null;
    }
}
