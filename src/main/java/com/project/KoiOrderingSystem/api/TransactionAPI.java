package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.entity.Transactions;
import com.project.KoiOrderingSystem.model.TransactionRespose;
import com.project.KoiOrderingSystem.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin("*")
@SecurityRequirement(name = "api")


public class TransactionAPI {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/booking")
    public ResponseEntity createTransactionBooking(@RequestParam UUID bookingId) {
        transactionService.createdTransactionBooking(bookingId);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/order")
    public ResponseEntity createTransactionOrder(@RequestParam UUID orderId) {
        transactionService.createdTransactionOrder(orderId);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/manager")
    public ResponseEntity getTransactionByManager() {
        List<TransactionRespose> transactions = transactionService.getAllTransaction();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/customer")
    public ResponseEntity getTransactionByCustomer() {
        List<TransactionRespose> transactions = transactionService.getAllTransactionByCustomer();
        return ResponseEntity.ok(transactions);
    }
}
