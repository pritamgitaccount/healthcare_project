package com.doctorbookingapp.service;

import com.doctorbookingapp.payload.BookingDto;

import java.util.List;

public interface BookingService {
    public void bookAnAppoinment(BookingDto dto);

    List<BookingDto> getAllBookingByDoctorId(long doctorId);
}
