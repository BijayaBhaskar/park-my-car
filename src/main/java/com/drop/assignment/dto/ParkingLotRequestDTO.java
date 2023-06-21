package com.drop.assignment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ParkingLotRequestDTO {

    @NotBlank(message = "Car number is required.")
    @Size(min = 3, max = 12, message = "Car number should have minimum 3 and maximum 12 characters")
    private String carNumber;
}
