package com.drop.assignment.dto;

import com.drop.assignment.model.ParkingSlot;
import lombok.Data;

@Data
public class ResponseDto {
    private String message;
    private ParkingSlot parkingSlot;
    public ResponseDto(String message){
        this.message = message;
    }
    public ResponseDto(String message, ParkingSlot parkingSlot){
        this.message = message;
        this.parkingSlot = parkingSlot;
    }
}
