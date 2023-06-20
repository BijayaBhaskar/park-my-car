package com.drop.assignment.component;

import com.drop.assignment.model.ParkingSlot;
import com.drop.assignment.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Component class to create parking slot after startup
 * @author Bijaya Bhaskar Swain
 */
@Component
public class ParkingLotComponent {

    @Value("${parking.size}")
    private int numberOfSlots;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    /**
     * method used to create parking slot
     * @param event
     */
    @EventListener
    public void createParkingSlot(ApplicationReadyEvent event) {
        List<ParkingSlot> slots = new ArrayList<>();
        for (int i = 1; i <= numberOfSlots; i++) {
            parkingSlotRepository.save(new ParkingSlot());
        }
        System.out.printf("Created a parking lot with %s slots %n", numberOfSlots);
    }
}
