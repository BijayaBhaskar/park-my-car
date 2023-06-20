package com.drop.assignment.service;

import com.drop.assignment.model.ParkingSlot;
/**
 * Interface for parking system
 * @author Bijaya Bhaskar Swain
 *
 */
public interface ParkingService {

    public ParkingSlot park(String vehicleNumber);
    public ParkingSlot slot(long id);
    public ParkingSlot unpark(String vehicleNumber);

}
