package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.entity.Orders;
import com.project.KoiOrderingSystem.model.BookingPaymentRequest;
import com.project.KoiOrderingSystem.model.OrderPaymentRequest;
import com.project.KoiOrderingSystem.model.OrderRequest;
import com.project.KoiOrderingSystem.model.OrderUpdateCompleted;
import com.project.KoiOrderingSystem.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class OrderAPI {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Orders newOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(newOrder);
    }

    @GetMapping("/manager")
    public ResponseEntity getAllOrder() {
        List<Orders> orderList = orderService.getAllOrder();
        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/customer")
    public ResponseEntity getOrderByAccount() {
        List<Orders> orderList = orderService.getOrdersByAccount();
        return ResponseEntity.ok(orderList);
    }

    @PutMapping("{orderId}")
    public ResponseEntity updateOrder(@PathVariable long orderId, @Valid @RequestBody OrderUpdateCompleted orderUpdateCompleted) {
        Orders newOrder = orderService.updateOrderCompleted(orderId, orderUpdateCompleted);
        return ResponseEntity.ok(newOrder);
    }

    @PostMapping("payment")
    public ResponseEntity paymentOrder(@RequestBody OrderPaymentRequest orderPaymentRequest) throws Exception {
        String url = orderService.paymentOrder(orderPaymentRequest);
        return ResponseEntity.ok(url);
    }

}
