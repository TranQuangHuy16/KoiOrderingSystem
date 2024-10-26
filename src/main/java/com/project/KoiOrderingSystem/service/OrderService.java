package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.*;
import com.project.KoiOrderingSystem.model.OrderDetailRequest;
import com.project.KoiOrderingSystem.model.OrderRequest;
import com.project.KoiOrderingSystem.model.OrderUpdateCompleted;
import com.project.KoiOrderingSystem.repository.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    BookingService bookingService;

    @Autowired
    KoiService koiService;

    public Orders createOrder(OrderRequest orderRequest) {

        Orders newOrder = new Orders();
        List<OrderDetail> orderDetails = new ArrayList<>();

        newOrder.setBooking(bookingService.getBookingById(orderRequest.getBookingId()));
        newOrder.setExpectedDate(orderRequest.getExpectedDate());
        newOrder.setStatus(orderRequest.getStatus());
        newOrder.setPrice(orderRequest.getPrice());
        newOrder.setAddress(orderRequest.getAddress());
        Set<KoiFish> kois = new HashSet<>();

        for(OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) {
            KoiFish koi = koiService.getKoi(orderDetailRequest.getKoiId());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            orderDetail.setOrder(newOrder);
            orderDetail.setKoi(koi);
            orderDetails.add(orderDetail);
        }
        newOrder.setOrderDetails(orderDetails);
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
