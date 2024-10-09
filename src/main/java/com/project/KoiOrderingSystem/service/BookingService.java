package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Booking;
import com.project.KoiOrderingSystem.entity.StatusBooking;
import com.project.KoiOrderingSystem.model.BookingRequest;
import com.project.KoiOrderingSystem.model.BookingUpdatePriceRequest;
import com.project.KoiOrderingSystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        booking.setBookingDate(new Date());
        booking.setImage(bookingRequest.getImage());
        booking.setStatus(bookingRequest.getStatus());
        booking.setNote(bookingRequest.getNote());
        Account account = authenticationService.getCurrentAccount();
        booking.setAccount(account);
        booking.setTrip(tripService.getTripById(bookingRequest.getTripId()));
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBooking() {
        List<Booking> bookingList = bookingRepository.findAll();
        return bookingList;
    }

    public List<Booking> getBookingById() {
        Account account = authenticationService.getCurrentAccount();
        List<Booking> bookingList = bookingRepository.findBookingsByAccount(account);
        return bookingList;
    }

    public Booking updateBooking(BookingUpdatePriceRequest bookingUpdatePriceRequest, long bookingId) {
        Booking updatedbooking = bookingRepository.findBookingById(bookingId);
        updatedbooking.setTotalPrice(bookingUpdatePriceRequest.getTotalPrice());
        updatedbooking.setStatus(StatusBooking.AWAITING_PAYMENT);
        return bookingRepository.save(updatedbooking);
    }

}
