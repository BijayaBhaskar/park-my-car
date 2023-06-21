package com.drop.assignment.dto;

import com.drop.assignment.model.ParkingSlot;
import lombok.Data;

@Data
public class ParkingLotResponseDTO {
    private String message;
    private ParkingSlot parkingSlot;
    public ParkingLotResponseDTO(String message){
        this.message = message;
    }
    public ParkingLotResponseDTO(String message, ParkingSlot parkingSlot){
        this.message = message;
        this.parkingSlot = parkingSlot;
    }
}
