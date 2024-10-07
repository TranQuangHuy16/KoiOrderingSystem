package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Booking;
import com.project.KoiOrderingSystem.model.BookingRequest;
import com.project.KoiOrderingSystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TripService tripService;

    public Booking createBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setTotalPrice(bookingRequest.getTotalPrice());
        booking.setBookingDate(bookingRequest.getBookingDate());
        booking.setImage(bookingRequest.getImage());
        booking.setStatus(bookingRequest.getStatus());
        booking.setNote(bookingRequest.getNote());
        Account account = authenticationService.getCurrentAccount();
        booking.setAccount(account);
        booking.setTrip(tripService.getTripById(bookingRequest.getTripId()));
        return bookingRepository.save(booking);
    }

}
