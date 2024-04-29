package com.doctorbookingapp.repository;


import com.doctorbookingapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
