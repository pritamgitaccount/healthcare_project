package com.doctorbookingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TimeSlotConfiguration {

    @Bean
    public TimeSlotManager timeSlotManager() {
        // Load the available time slots and perform initialization logic
        List<String> availableTimeSlots = new ArrayList<>();
        availableTimeSlots.add("10:15 am");
        availableTimeSlots.add("11:00 am");
        availableTimeSlots.add("12:15 pm");
        return new TimeSlotManager(availableTimeSlots);
    }
}