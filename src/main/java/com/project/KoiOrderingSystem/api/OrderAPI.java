package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.entity.Orders;
import com.project.KoiOrderingSystem.model.*;
import com.project.KoiOrderingSystem.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        List<OrderResponse> orderList = orderService.getAllOrder();
        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/customer")
    public ResponseEntity getOrderByAccount() {
        List<OrderResponse> orderList = orderService.getOrdersByAccount();
        return ResponseEntity.ok(orderList);
    }

    @PutMapping("{orderId}")
    public ResponseEntity updateOrder(@PathVariable UUID orderId, @Valid @RequestBody OrderUpdateCompleted orderUpdateCompleted) {
        Orders newOrder = orderService.updateOrderCompleted(orderId, orderUpdateCompleted);
        return ResponseEntity.ok(newOrder);
    }

    @PostMapping("payment")
    public ResponseEntity paymentOrder(@RequestBody OrderPaymentRequest orderPaymentRequest) throws Exception {
        String url = orderService.paymentOrder(orderPaymentRequest);
        return ResponseEntity.ok(url);
    }

}
