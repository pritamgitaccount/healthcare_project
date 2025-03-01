package com.doctorbookingapp.repository;


import com.doctorbookingapp.entity.Booking;
import com.doctorbookingapp.payload.BookingDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingByDoctorId(long doctorId);
}
