package com.drop.assignment.service;

import com.drop.assignment.exception.CarIsAlreadyParkedException;
import com.drop.assignment.exception.CarIsNotParkedInParkingLotException;
import com.drop.assignment.exception.InvalidSlotIdException;
import com.drop.assignment.exception.ParkingSlotUnAvailableException;
import com.drop.assignment.model.ParkingSlot;
import com.drop.assignment.repository.ParkingSlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * Test class for parking service
 * @author Bijaya Bhaskar Swain
 */
@SpringBootTest
public class ParkingServiceTest {

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @Mock
    private ParkingSlotRepository parkingSlotRepository;

    @Test
    @DisplayName("Test car parked successfully")
    public void parkedSuccessfully() {
        Mockito.when(parkingSlotRepository.findByCarNumber(Mockito.anyString())).thenReturn(null);
        Mockito.when(parkingSlotRepository.findFirstByCarNumberIsNull()).thenReturn(getAvailableParkingSlot());
        Mockito.when(parkingSlotRepository.save(Mockito.any())).thenReturn(getParkingSlot());
        ParkingSlot parkingSlot = parkingService.park("ABC");
        Assertions.assertNotNull(parkingSlot);
        Assertions.assertEquals("ABC", parkingSlot.getCarNumber());
        Assertions.assertFalse(parkingSlot.isAvailable());
    }

    @Test
    @DisplayName("Test park method should throw CarIsAlreadyParkedException when same car is already parked in parking lot")
    public void parkMethodShouldThrowCarIsAlreadyParkedException() {
        Mockito.when(parkingSlotRepository.findByCarNumber(Mockito.anyString())).thenReturn(getParkingSlot());
        Assertions.assertThrows(CarIsAlreadyParkedException.class,
                ()-> parkingService.park("ABC"));
    }

    @Test
    @DisplayName("Test park method should throw ParkingSlotUnAvailableException when parking slot is not available")
    public void parkMethodShouldThrowParkingSlotUnAvailableException() {
        Mockito.when(parkingSlotRepository.findByCarNumber(Mockito.anyString())).thenReturn(null);
        Mockito.when(parkingSlotRepository.findFirstByCarNumberIsNull()).thenReturn(null);
        Assertions.assertThrows(ParkingSlotUnAvailableException.class,
                ()-> parkingService.park("ABC"));
    }

    @Test
    @DisplayName("Test get slot info based on slot id")
    public void getSlotInfoBySlotId() {
        Mockito.when(parkingSlotRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getParkingSlot()));
        ParkingSlot parkingSlot = parkingService.slot(1);
        Assertions.assertNotNull(parkingSlot);
        Assertions.assertEquals(1, parkingSlot.getSlotId());
    }

    @Test
    @DisplayName("Test get slot info should throw InvalidSlotIdException when slot Id is not available ")
    public void getSlotInfoShouldThrowInvalidSlotIdException() {
        Mockito.when(parkingSlotRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(InvalidSlotIdException.class,
                ()-> parkingService.slot(1));
    }

    @Test
    @DisplayName("Test car un parked successfully")
    public void unParkedSuccessfully() {
        Mockito.when(parkingSlotRepository.findByCarNumber(Mockito.anyString())).thenReturn(getParkingSlot());
        Mockito.when(parkingSlotRepository.save(Mockito.any())).thenReturn(getUnParkingSlot());
        ParkingSlot parkingSlot = parkingService.unpark("ABC");
        System.out.printf(""+parkingSlot);
        Assertions.assertNotNull(parkingSlot);
        Assertions.assertNull(parkingSlot.getCarNumber());
        Assertions.assertTrue(parkingSlot.isAvailable());
    }

    @Test
    @DisplayName("Test car un parked should throw CarIsNotParkedInParkingLotException when car is not found in parking lot")
    public void unParkedMethodShouldThrowCarIsNotParkedInParkingLotException() {
        Mockito.when(parkingSlotRepository.findByCarNumber(Mockito.anyString())).thenReturn(null);
        Assertions.assertThrows(CarIsNotParkedInParkingLotException.class,
                ()-> parkingService.unpark("ABC"));
    }

    private ParkingSlot getAvailableParkingSlot(){
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setSlotId(1);
        parkingSlot.setCarNumber(null);
        parkingSlot.setAvailable(true);

        return parkingSlot;
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
