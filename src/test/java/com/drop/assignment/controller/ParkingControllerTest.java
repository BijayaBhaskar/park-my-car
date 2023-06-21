package com.drop.assignment.controller;

import com.drop.assignment.dto.ResponseDto;
import com.drop.assignment.exception.CarIsAlreadyParkedException;
import com.drop.assignment.exception.CarIsNotParkedInParkingLotException;
import com.drop.assignment.exception.InvalidSlotIdException;
import com.drop.assignment.exception.ParkingSlotUnAvailableException;
import com.drop.assignment.model.ParkingSlot;
import com.drop.assignment.service.ParkingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Test class for parkingController
 * @author Bijaya Bhaskar Swain
 */
@SpringBootTest
public class ParkingControllerTest {

    @InjectMocks
    private ParkingController parkingController;

    @Mock
    private ParkingService parkingService;

    @Test
    @DisplayName("Controller test car parked successfully")
    public void parkedSuccessfully() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Mockito.when(parkingService.park(Mockito.anyString())).thenReturn(getParkingSlot());
        ResponseEntity<ResponseDto> responseEntity = parkingController.park("ABC");

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("ABC", responseEntity.getBody().getParkingSlot().getCarNumber());
        Assertions.assertFalse(responseEntity.getBody().getParkingSlot().isAvailable());
    }

    @Test
    @DisplayName("Test controller park method should throw CarIsAlreadyParkedException when same car is already parked in parking lot")
    public void parkMethodShouldThrowCarIsAlreadyParkedException() {
        Mockito.when(parkingService.park(Mockito.anyString())).thenThrow(CarIsAlreadyParkedException.class);
        Assertions.assertThrows(CarIsAlreadyParkedException.class,
                ()-> parkingController.park("ABC"));
    }

    @Test
    @DisplayName("Controller test park method should throw ParkingSlotUnAvailableException when parking slot is not available")
    public void parkMethodShouldThrowParkingSlotUnAvailableException() {
        Mockito.when(parkingService.park(Mockito.anyString())).thenThrow(ParkingSlotUnAvailableException.class);
        Assertions.assertThrows(ParkingSlotUnAvailableException.class,
                ()-> parkingController.park("ABC"));
    }

    @Test
    @DisplayName("Test controller get slot based on slot id")
    public void slotBySlotId() {
        Mockito.when(parkingService.slot(Mockito.anyLong())).thenReturn(getParkingSlot());
        ResponseEntity<ResponseDto> responseEntity = parkingController.slot(1);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(1, responseEntity.getBody().getParkingSlot().getSlotId());
    }

    @Test
    @DisplayName("Test get slot info should throw InvalidSlotIdException when slot Id is not available ")
    public void getSlotInfoShouldThrowInvalidSlotIdException() {
        Mockito.when(parkingService.slot(Mockito.anyLong())).thenThrow(InvalidSlotIdException.class);
        Assertions.assertThrows(InvalidSlotIdException.class,
                ()-> parkingService.slot(1));
    }

    @Test
    @DisplayName("Test controller car un parked successfully")
    public void unParkedSuccessfully() {
        Mockito.when(parkingService.unpark(Mockito.anyString())).thenReturn(getUnParkingSlot());
        ResponseEntity<ResponseDto> responseEntity = parkingController.unpark("ABC");
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNull(responseEntity.getBody().getParkingSlot().getCarNumber());
        Assertions.assertTrue(responseEntity.getBody().getParkingSlot().isAvailable());
    }

    @Test
    @DisplayName("Test controller car un parked should throw CarIsNotParkedInParkingLotException when car is not found in parking lot")
    public void unParkedMethodShouldThrowCarIsNotParkedInParkingLotException() {
        Mockito.when(parkingService.unpark(Mockito.anyString())).thenThrow(CarIsNotParkedInParkingLotException.class);
        Assertions.assertThrows(CarIsNotParkedInParkingLotException.class,
                ()-> parkingController.unpark("ABC"));
    }

    private ParkingSlot getParkingSlot(){
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setSlotId(1);
        parkingSlot.setCarNumber("ABC");
        parkingSlot.setAvailable(false);

        return parkingSlot;
    }

    private ParkingSlot getUnParkingSlot(){
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setSlotId(1);
        parkingSlot.setCarNumber(null);
        parkingSlot.setAvailable(true);

        return parkingSlot;
    }
}
