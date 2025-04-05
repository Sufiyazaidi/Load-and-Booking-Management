package com.example.Load_Management.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.Load_Management.Entity.*;
import com.example.Load_Management.Enum.BookingStatus;
import com.example.Load_Management.Enum.LoadStatus;
import com.example.Load_Management.Exception.ResourceNotFoundException;
import com.example.Load_Management.Respository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private LoadRepository loadRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        // Fetch the load associated with this booking
        Load load = loadRepository.findById(booking.getLoadId())
            .orElseThrow(() -> new ResourceNotFoundException("Load not found with ID: " + booking.getLoadId()));

        // Check if the load is already cancelled
        if (load.getStatus() == LoadStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot book a cancelled load");
        }

        // Save the booking
        booking.setStatus(BookingStatus.PENDING); // Default status for new bookings
        Booking savedBooking = bookingRepository.save(booking);

        // Update Load status to BOOKED
        load.setStatus(LoadStatus.BOOKED);
        loadRepository.save(load);

        return savedBooking;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    public Optional<Booking> getBookingById(UUID bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Transactional
public Booking updateBooking(UUID bookingId, Booking updatedBooking) {
    Booking existingBooking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
    

    // Update fields only if they are provided
    if(updatedBooking.getLoadId()!=null)
    {
        Load newLoad = loadRepository.findById(updatedBooking.getLoadId())
        .orElseThrow(() -> new ResourceNotFoundException("Load not found with ID: " + updatedBooking.getLoadId()));
        if (newLoad.getStatus() == LoadStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot assign a CANCELLED load to a booking");
        }
        existingBooking.setLoadId(updatedBooking.getLoadId());
    }
    if (updatedBooking.getProposedRate() != 0) {
        existingBooking.setProposedRate(updatedBooking.getProposedRate());
    }
    if (updatedBooking.getComment() != null) {
        existingBooking.setComment(updatedBooking.getComment());
    }
    if (updatedBooking.getStatus() != null) {
        existingBooking.setStatus(updatedBooking.getStatus());
    }
    if(updatedBooking.getTransporterId()!=null)
    {
        existingBooking.setTransporterId(updatedBooking.getTransporterId());
    }

    return bookingRepository.save(existingBooking);
}

@Transactional
public void deleteBooking(UUID bookingId) {
    // Fetch the booking
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

    UUID loadId = booking.getLoadId(); // Get the associated load ID

    // Delete the booking
    bookingRepository.deleteById(bookingId);

    // Check if there are any remaining bookings for this load
    boolean hasOtherBookings = bookingRepository.existsByLoadId(loadId);

    // If no other bookings exist, mark the load as CANCELLED
    if (!hasOtherBookings) {
        Load load = loadRepository.findById(loadId)
            .orElseThrow(() -> new ResourceNotFoundException("Load not found with ID: " + loadId));

        load.setStatus(LoadStatus.CANCELLED);
        loadRepository.save(load);
    }
}
}
