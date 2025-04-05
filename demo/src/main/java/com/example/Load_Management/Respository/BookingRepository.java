package com.example.Load_Management.Respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Load_Management.Entity.Booking;
import java.util.UUID;



public interface BookingRepository extends JpaRepository<Booking, UUID> {
    boolean existsByLoadId(UUID loadId);
}
