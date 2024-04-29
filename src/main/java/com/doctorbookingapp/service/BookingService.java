package com.doctorbookingapp.service;

import com.doctorbookingapp.payload.BookingDto;

public interface BookingService {
    public void bookAnAppoinment(BookingDto dto);
}
