package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.*;

import com.project.KoiOrderingSystem.model.TransactionRespose;
import com.project.KoiOrderingSystem.repository.AccountRepository;
import com.project.KoiOrderingSystem.repository.OrderRepository;
import com.project.KoiOrderingSystem.repository.PaymentRepository;
import com.project.KoiOrderingSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    OrderRepository orderRepository;


    public void createdTransactionBooking(long bookingId) {

        Booking booking = bookingService.getBookingById(bookingId);
        if(booking == null) {
            throw new RuntimeException("Booking not found");
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setCreated_at(new Date());
        payment.setPrice(booking.getTotalPrice());

        Transactions transaction = new Transactions();

        Account admin = accountRepository.findAccountByRole(Role.MANAGER);
        Account customer =  authenticationService.getCurrentAccount();

        transaction.setTo(admin);
        transaction.setFrom(customer);
        transaction.setPayment(payment);
        transaction.setCreated_at(LocalDateTime.now());
        transaction.setStatus(StatusTransactions.SUCCESS);
        transaction.setDescription("Payment for booking " + booking.getId());

        paymentRepository.save(payment);
        transactionRepository.save(transaction);

    }

    public void createdTransactionOrder(long orderId) {

        Orders order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setCreated_at(new Date());
        payment.setPrice(order.getPrice());

        Transactions transaction = new Transactions();

        Account admin = accountRepository.findAccountByRole(Role.MANAGER);
        Account customer =  authenticationService.getCurrentAccount();

        transaction.setTo(admin);
        transaction.setFrom(customer);
        transaction.setPayment(payment);
        transaction.setCreated_at(LocalDateTime.now());
        transaction.setStatus(StatusTransactions.SUCCESS);
        transaction.setDescription("Payment for order " + order.getId());

        paymentRepository.save(payment);
        transactionRepository.save(transaction);

        order.setStatus(StatusOrder.ON_DELIVERY);
        orderRepository.save(order);

    }


    public List<TransactionRespose> getAllTransaction() {
        List<TransactionRespose> transactionList = new ArrayList<>();
        for(Transactions t : transactionRepository.findAll()) {
            TransactionRespose transaction = new TransactionRespose();
            transaction.setCreated_at(t.getCreated_at());
            transaction.setStatus(t.getStatus());
            transaction.setDescription(t.getDescription());
            transaction.setPayment(t.getPayment());
            transaction.setTo(t.getTo().getFirstName() + " " + t.getTo().getLastName());
            transaction.setFrom(t.getFrom().getFirstName() + " " + t.getFrom().getLastName());
            transactionList.add(transaction);
        }
        return transactionList;
    }

    public List<TransactionRespose> getAllTransactionByCustomer() {
        Account account = authenticationService.getCurrentAccount();
        List<TransactionRespose> transactionList = new ArrayList<>();
        for(Transactions t : transactionRepository.findTransactionsesByFromId(account.getId())) {
            TransactionRespose transaction = new TransactionRespose();
            transaction.setCreated_at(t.getCreated_at());
            transaction.setStatus(t.getStatus());
            transaction.setDescription(t.getDescription());
            transaction.setPayment(t.getPayment());
            transaction.setTo("Manager");
            transaction.setFrom(t.getFrom().getFirstName() + " " + t.getFrom().getLastName());
            transactionList.add(transaction);
        }
        return transactionList;
    }
}
