package com.drop.assignment.repository;

import com.drop.assignment.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for parking slot
 * @author Bijaya Bhaskar Swain
 */
@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    ParkingSlot findByCarNumber(String vehicleNumber);
    ParkingSlot findFirstByIsAvailableTrue();

}
