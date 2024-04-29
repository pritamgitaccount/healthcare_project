package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.config.TimeSlotManager;
import com.doctorbookingapp.entity.Booking;
import com.doctorbookingapp.payload.BookingDto;
import com.doctorbookingapp.repository.BookingRepository;
import com.doctorbookingapp.service.BookingService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private TimeSlotManager timeSlotManager;

    // Method to book an appointment
    @Override
    @Transactional
    public void bookAnAppoinment(BookingDto dto) {
        // Get the available time slots from the TimeSlotManager
        List<String> availableTimeSlots = timeSlotManager.getAvailableTimeSlots();

        // Create a new Booking object to store the appointment details
        Booking booking = new Booking();

        // Iterate through available time slots to find a match with the requested time
        for (String slots : availableTimeSlots) {
            if (slots.equals(dto.getBookingTime())) {
                // If a match is found, set the booking time and update the available time slots
                booking.setBookingTime(dto.getBookingTime());
                availableTimeSlots.remove(slots);
                timeSlotManager.setAvailableTimeSlots(availableTimeSlots);
                break; // Exit the loop after booking a slot
            }
        }

        // Set up a ScheduledExecutorService to execute code every 24 hours
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(
                () -> {
                    // Code to execute every 24 hours
                    System.out.println("Executing code every 24 hours...");
                    availableTimeSlots.add("10:15 am");
                    availableTimeSlots.add("11:15 am");
                    availableTimeSlots.add("12:15 pm");
                },
                0, 24, TimeUnit.HOURS);

        // Set doctor and patient IDs for the booking
        booking.setDoctorId(dto.getDoctorId());
        booking.setPatientId(dto.getPatientId());

        // Check if the booking time is set before saving to the repository
        if (booking.getBookingTime() != null) {
            bookingRepo.save(booking);
        } else {
            System.out.println("Time slot is not available");
        }
    }
}
