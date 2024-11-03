package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Booking;
import com.project.KoiOrderingSystem.entity.Feedback;
import com.project.KoiOrderingSystem.entity.StatusBooking;
import com.project.KoiOrderingSystem.model.*;
import com.project.KoiOrderingSystem.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    TripService tripService;

    @Autowired
    EmailService emailService;

    @Autowired
    ModelMapper modelMapper;

    public Booking createBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setBookingDate(new Date());
        booking.setStatus(bookingRequest.getStatus());
        booking.setNote(bookingRequest.getNote());
        Account account = authenticationService.getCurrentAccount();
        booking.setAccount(account);
        booking.setTrip(tripService.getTripById(bookingRequest.getTripId()));
        return bookingRepository.save(booking);
    }

    public List<BookingResponseManager> getAllBooking() {
        List<BookingResponseManager> bookingList = new ArrayList<>();
        for(Booking booking : bookingRepository.findAll()) {
            Feedback feedback = booking.getFeedback();
            FeedbackResponse feedbackResponse;
            if(feedback != null) {
                feedbackResponse = modelMapper.map(booking.getFeedback(), FeedbackResponse.class);
            } else {
                feedbackResponse = null;
            }
            BookingResponseManager bookingResponseManager = modelMapper.map(booking, BookingResponseManager.class);
            bookingResponseManager.setFeedbackResponse(feedbackResponse);
            bookingList.add(bookingResponseManager);
        }
        return bookingList;
    }

    public List<Booking> getBookingByAccount() {
        Account account = authenticationService.getCurrentAccount();
        List<Booking> bookingList = bookingRepository.findBookingsByAccount(account);
        return bookingList;
    }

    public Booking updateBooking(BookingUpdatePriceRequest bookingUpdatePriceRequest, UUID bookingId) {
        Booking updatedbooking = bookingRepository.findBookingById(bookingId);
        updatedbooking.setTotalPrice(bookingUpdatePriceRequest.getTotalPrice());

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setReceiver(updatedbooking.getAccount());
        emailDetail.setSubject("Welcome to Koi Ordering System");
        emailService.sendEmailNotificatePayment(emailDetail);

        updatedbooking.setStatus(StatusBooking.AWAITING_PAYMENT);
        return bookingRepository.save(updatedbooking);
    }

    public Booking updateStatus(BookingStatusUpdateRequest bookingStatusUpdateRequest, UUID bookingId) {
        Booking updatedbooking = bookingRepository.findBookingById(bookingId);
        updatedbooking.setStatus(bookingStatusUpdateRequest.getStatus());
        return bookingRepository.save(updatedbooking);
    }

    public Booking updateCheckIn(CheckInRequest checkInRequest, UUID bookingId) {
        Booking updatedbooking = bookingRepository.findBookingById(bookingId);
        updatedbooking.setImage(checkInRequest.getImage());
        return bookingRepository.save(updatedbooking);
    }

    public Booking getBookingById(UUID id) {
        Booking booking = bookingRepository.findBookingById(id);
        return booking;
    }

    public String paymentBooking(BookingPaymentRequest bookingPaymentRequest) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);

        //code của mình
        Booking booking = getBookingById(bookingPaymentRequest.getId());
        float money = booking.getTotalPrice() * 100;
        String amount = String.valueOf((int) money);

        UUID randomUUID = UUID.randomUUID();


        String tmnCode = "J8GSGBC5";
        String secretKey = "S7IQI58YMDLNRT5CVPGTLQLV7EJ325KC";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "http://localhost:5173/book-status?bookingId=" + bookingPaymentRequest.getId();
        String currCode = "VND";

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", String.valueOf(randomUUID));
        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma GD: " + randomUUID);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount",amount);

        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "128.199.178.23");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove last '&'

        String signData = signDataBuilder.toString();
        String signed = generateHMAC(secretKey, signData);

        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Remove last '&'

        return urlBuilder.toString();
    }

    private String generateHMAC(String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
