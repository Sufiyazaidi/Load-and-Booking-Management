package com.example.Load_Management.Controller;
import com.example.Load_Management.Entity.Booking;
//import com.example.Load_Management.Enum.BookingStatus;
import com.example.Load_Management.Service.BookingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
    @GetMapping("/{bookingId}")
    public Optional<Booking> getBookingById(@PathVariable UUID bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

   
    @PutMapping("/{bookingId}")
public ResponseEntity<Booking> updateBooking(
        @PathVariable UUID bookingId,
        @RequestBody Booking updatedBooking) {
    Booking booking = bookingService.updateBooking(bookingId, updatedBooking);
    return ResponseEntity.ok(booking);
}

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable UUID bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok("Booking deleted successfully");
    }
}



