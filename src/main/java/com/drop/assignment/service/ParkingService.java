package com.drop.assignment.service;

import com.drop.assignment.model.ParkingSlot;
/**
 * Interface for parking system
 * @author Bijaya Bhaskar Swain
 *
 */
public interface ParkingService {

    public ParkingSlot park(String carNumber);
    public ParkingSlot slot(long slotId);
    public ParkingSlot unpark(String carNumber);

}
