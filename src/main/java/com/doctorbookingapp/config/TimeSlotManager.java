package com.doctorbookingapp.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
public class TimeSlotManager {

    // Declaring a final field to hold the list of available time slots.
    private List<String> availableTimeSlots;


//    public TimeSlotManager(List<String> initialTimeSlots) {
//        this.availableTimeSlots = initialTimeSlots;
//    }

//    public List<String> getAvailableTimeSlots() {
//        return availableTimeSlots;
//    }
//
//    public void setAvailableTimeSlots(List<String> timeSlots) {
//        this.availableTimeSlots = timeSlots;
//    }
}

