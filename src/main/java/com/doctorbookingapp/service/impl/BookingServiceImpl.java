package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.config.TimeSlotManager;
import com.doctorbookingapp.entity.Booking;
import com.doctorbookingapp.entity.User;
import com.doctorbookingapp.exception.BookingNotFoundException;
import com.doctorbookingapp.exception.ResourceNotFoundException;
import com.doctorbookingapp.payload.BookingDto;
import com.doctorbookingapp.repository.BookingRepository;
import com.doctorbookingapp.repository.UserRepository;
import com.doctorbookingapp.service.BookingService;
import com.doctorbookingapp.service.NotificationService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepo;
    private final TimeSlotManager timeSlotManager;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId)
        );
    }

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
                0, 24, HOURS);

        // Set doctor and patient IDs for the booking
        booking.setDoctorId(dto.getDoctorId());
        booking.setPatientId(dto.getPatientId());

        // Check if the booking time is set before saving to the repository
        if (booking.getBookingTime() != null) {
            bookingRepo.save(booking);
            var patient = getUserById(dto.getPatientId());
            var doctor = getUserById(dto.getDoctorId());
            notificationService.sendNotification(doctor.getId(),
                    "New appointment booked at " + booking.getBookingTime());
            notificationService.sendNotification(patient.getId(),
                    "Your appointment with Dr. " + doctor.getFirstName() + " at " + booking.getBookingTime() + " has been booked.");
        } else {
            log.warn("Time slot is not available for booking.");
        }
    }

    @Override
    public List<BookingDto> getAllBookingByDoctorId(long doctorId) {
//        try {
        List<Booking> bookings = bookingRepo.findBookingByDoctorId(doctorId);
        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("No booking yet available");
        } else {
            return bookings.stream()
                    .map(booking -> modelMapper.map(booking, BookingDto.class))
                    .collect(toList());
        }
//        }catch (BookingNotFoundException e) {
//            log.error("Error fetching bookings for doctorId {}: {}", doctorId, e.getMessage());
//            throw new BookingNotFoundException("Failed to fetch bookings. Please try again.");
//        }
    }
}
