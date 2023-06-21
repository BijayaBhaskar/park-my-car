package com.drop.assignment.controller;

import com.drop.assignment.dto.Response;
import com.drop.assignment.exception.CarIsAlreadyParkedException;
import com.drop.assignment.exception.CarIsNotParkedInParkingLotException;
import com.drop.assignment.exception.InvalidSlotIdException;
import com.drop.assignment.exception.ParkingSlotUnAvailableException;
import com.drop.assignment.model.ParkingSlot;
import com.drop.assignment.service.ParkingService;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

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

    @Mock
    private Bucket bucket;

    @Test
    @DisplayName("Controller test car parked successfully")
    public void parkedSuccessfully() {
        Mockito.when(bucket.tryConsume(1)).thenReturn(true);
        Mockito.when(parkingService.park(Mockito.anyString())).thenReturn(getParkingSlot());
        ResponseEntity<Response> responseEntity = parkingController.park("ABC");

        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("ABC", responseEntity.getBody().getParkingSlot().getCarNumber());
        Assertions.assertFalse(responseEntity.getBody().getParkingSlot().isAvailable());
    }

    @Test
    @DisplayName("Test controller park method should throw CarIsAlreadyParkedException when same car is already parked in parking lot")
    public void parkMethodShouldThrowCarIsAlreadyParkedException() {
        Mockito.when(bucket.tryConsume(1)).thenReturn(true);
        Mockito.when(parkingService.park(Mockito.anyString())).thenThrow(CarIsAlreadyParkedException.class);
        Assertions.assertThrows(CarIsAlreadyParkedException.class,
                ()-> parkingController.park("ABC"));
    }

    @Test
    @DisplayName("Controller test park method should throw ParkingSlotUnAvailableException when parking slot is not available")
    public void parkMethodShouldThrowParkingSlotUnAvailableException() {
        Mockito.when(bucket.tryConsume(1)).thenReturn(true);
        Mockito.when(parkingService.park(Mockito.anyString())).thenThrow(ParkingSlotUnAvailableException.class);
        Assertions.assertThrows(ParkingSlotUnAvailableException.class,
                ()-> parkingController.park("ABC"));
    }

    @Test
    @DisplayName("Test controller get slot based on slot id")
    public void slotBySlotId() {
        Mockito.when(bucket.tryConsume(1)).thenReturn(true);
        Mockito.when(parkingService.slot(Mockito.anyLong())).thenReturn(getParkingSlot());
        ResponseEntity<Response> responseEntity = parkingController.slot(1);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(1, responseEntity.getBody().getParkingSlot().getSlotId());
    }

    @Test
    @DisplayName("Test get slot info should throw InvalidSlotIdException when slot Id is not available ")
    public void getSlotInfoShouldThrowInvalidSlotIdException() {
        Mockito.when(bucket.tryConsume(1)).thenReturn(true);
        Mockito.when(parkingService.slot(Mockito.anyLong())).thenThrow(InvalidSlotIdException.class);
        Assertions.assertThrows(InvalidSlotIdException.class,
                ()-> parkingService.slot(1));
    }

    @Test
    @DisplayName("Test controller car un parked successfully")
    public void unParkedSuccessfully() {
        Mockito.when(bucket.tryConsume(1)).thenReturn(true);
        Mockito.when(parkingService.unpark(Mockito.anyString())).thenReturn(getUnParkingSlot());
        ResponseEntity<Response> responseEntity = parkingController.unpark("ABC");
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNull(responseEntity.getBody().getParkingSlot().getCarNumber());
        Assertions.assertTrue(responseEntity.getBody().getParkingSlot().isAvailable());
    }

    @Test
    @DisplayName("Test controller car un parked should throw CarIsNotParkedInParkingLotException when car is not found in parking lot")
    public void unParkedMethodShouldThrowCarIsNotParkedInParkingLotException() {
        Mockito.when(bucket.tryConsume(1)).thenReturn(true);
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
