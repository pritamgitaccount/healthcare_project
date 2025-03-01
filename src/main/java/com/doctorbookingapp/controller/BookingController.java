package com.doctorbookingapp.controller;


import com.doctorbookingapp.payload.BookingDto;
import com.doctorbookingapp.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    //http://localhost:8080/api/bookings
    @PostMapping
    public ResponseEntity<String> bookAnAppoinment(@RequestBody BookingDto bookingDto) {
        bookingService.bookAnAppoinment(bookingDto);
        return new ResponseEntity<>("Booking is confirmed", HttpStatus.CREATED);
    }

    //http://localhost:8080/api/bookings/doctor/{doctorId}
    @GetMapping(value = "/doctor/{doctorId}")
    public ResponseEntity<List<BookingDto>> getAllBookingByDoctorId(@PathVariable long doctorId) {
        var allBookingByDoctorId = bookingService.getAllBookingByDoctorId(doctorId);
        return new ResponseEntity<>(allBookingByDoctorId, HttpStatus.OK);
    }
}