package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.*;

import com.project.KoiOrderingSystem.model.TransactionOrderRequest;
import com.project.KoiOrderingSystem.repository.AccountRepository;
import com.project.KoiOrderingSystem.repository.PaymentRepository;
import com.project.KoiOrderingSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class TransactionService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    OrderService orderService;

    @Autowired
    BookingService bookingService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PaymentRepository paymentRepository;


    public void createdTransactionBooking(long bookingId) {

        Booking booking = bookingService.getBookingById(bookingId);
        if(booking == null) {
            throw new RuntimeException("Booking not found");
        }

//        Set<Transactions> setTransactions  = new HashSet<>();

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setCreated_at(new Date());

        Transactions transaction = new Transactions();

        Account admin = accountRepository.findAccountByRole(Role.MANAGER);
        Account customer =  authenticationService.getCurrentAccount();

        transaction.setTo(admin);
        transaction.setFrom(customer);
        transaction.setPayment(payment);
        transaction.setStatus(StatusTransactions.SUCCESS);
        transaction.setDescription("Payment for booking");

//        setTransactions.add(transaction);
//        payment.setTransactions(transaction);

        paymentRepository.save(payment);
        transactionRepository.save(transaction);

    }

    public void createdTransactionOrder(TransactionOrderRequest transactionOrderRequest) {

        Orders order = orderService.getOrderById(transactionOrderRequest.getOrderId());
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

//        Set<Transactions> setTransactions  = new HashSet<>();

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setCreated_at(new Date());

        Transactions transaction = new Transactions();

        Account admin = accountRepository.findAccountByRole(Role.MANAGER);
        Account customer =  authenticationService.getCurrentAccount();

        transaction.setTo(admin);
        transaction.setFrom(customer);
        transaction.setPayment(payment);
        transaction.setStatus(StatusTransactions.SUCCESS);
        transaction.setDescription("Payment for order");

//        setTransactions.add(transaction);
//        payment.setTransactions(setTransactions);

        paymentRepository.save(payment);
        transactionRepository.save(transaction);
    }
}
