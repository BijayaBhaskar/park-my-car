package com.drop.assignment.dto;

import com.drop.assignment.model.ParkingSlot;
import lombok.Data;

@Data
public class Response {
    private String message;
    private ParkingSlot parkingSlot;
    public Response(String message){
        this.message = message;
    }
    public Response(String message, ParkingSlot parkingSlot){
        this.message = message;
        this.parkingSlot = parkingSlot;
    }
}
