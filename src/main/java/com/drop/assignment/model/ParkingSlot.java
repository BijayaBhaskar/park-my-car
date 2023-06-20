package com.drop.assignment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class for parking slot
 * @author Bijaya Bhaskar Swain
 */
@Data
@Entity
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long slotId;
    private String carNumber;
    private boolean isAvailable = true;
}
