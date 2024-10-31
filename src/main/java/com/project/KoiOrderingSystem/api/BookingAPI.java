package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.entity.Booking;
import com.project.KoiOrderingSystem.entity.StatusBooking;
import com.project.KoiOrderingSystem.model.*;
import com.project.KoiOrderingSystem.service.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class BookingAPI {

    @Autowired
    BookingService bookingService;

    @PostMapping
    public ResponseEntity createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/manager")
    public ResponseEntity getAllBooking() {
        List<Booking> bookingList = bookingService.getAllBooking();
        return ResponseEntity.ok(bookingList);
    }

    @GetMapping("/customer")
    public ResponseEntity getBookingByAccount() {
        List<Booking> bookingList = bookingService.getBookingByAccount();
        return ResponseEntity.ok(bookingList);
    }

    @PutMapping("/price/{bookingId}")
    public ResponseEntity updateBooking(@Valid @RequestBody BookingUpdatePriceRequest bookingUpdatePriceRequest, @PathVariable UUID bookingId) {
        Booking booking = bookingService.updateBooking(bookingUpdatePriceRequest, bookingId);
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/status/{bookingId}")
    public ResponseEntity updateStatus(@Valid @RequestBody BookingStatusUpdateRequest bookingStatusUpdateRequest, @PathVariable UUID bookingId) {
        Booking booking = bookingService.updateStatus(bookingStatusUpdateRequest, bookingId);
        return ResponseEntity.ok(booking);
    }

    @PostMapping("payment")
    public ResponseEntity paymentBooking(@RequestBody BookingPaymentRequest bookingPaymentRequest) throws Exception {
        String url = bookingService.paymentBooking(bookingPaymentRequest);
        return ResponseEntity.ok(url);
    }

    @PutMapping("/check-in/{bookingId}")
    public ResponseEntity updateBooking(@Valid @RequestBody CheckInRequest checkInRequest, @PathVariable UUID bookingId) {
        Booking booking = bookingService.updateCheckIn(checkInRequest, bookingId);
        return ResponseEntity.ok(booking);
    }
}
