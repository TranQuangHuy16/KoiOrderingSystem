package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Booking;
import com.project.KoiOrderingSystem.entity.Orders;
import com.project.KoiOrderingSystem.entity.StatusOrder;
import com.project.KoiOrderingSystem.model.OrderRequest;
import com.project.KoiOrderingSystem.model.OrderUpdateCompleted;
import com.project.KoiOrderingSystem.repository.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    BookingService bookingService;

    public Orders createOrder( OrderRequest orderRequest) {

        Orders newOrder = new Orders();
        newOrder.setBooking(bookingService.getBookingById(orderRequest.getBookingId()));
        newOrder.setExpectedDate(orderRequest.getExpectedDate());
        newOrder.setStatus(orderRequest.getStatus());
        orderRepository.save(newOrder);

        return newOrder;
    }

    public List<Orders> getAllOrder() {
        return orderRepository.findAll();
    }

    public List<Orders> getOrdersByAccount() {
        Account account = authenticationService.getCurrentAccount();
        return orderRepository.findOrdersByAccount(account);
    }


    public Orders updateOrderCompleted(long orderId, OrderUpdateCompleted orderUpdateCompleted) {
        Orders order = orderRepository.findOrderById(orderId);
        order.setImage(orderUpdateCompleted.getImage());
        order.setDeliveredDate(orderUpdateCompleted.getDeliveredDate());
        order.setStatus(StatusOrder.COMPLETED);
        return orderRepository.save(order);
    }

    public Orders getOrderById(long id) {
        return orderRepository.findOrderById(id);
    }
}
