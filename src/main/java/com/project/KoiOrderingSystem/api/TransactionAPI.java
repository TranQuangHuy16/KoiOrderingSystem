package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.model.TransactionBookingRequest;
import com.project.KoiOrderingSystem.model.TransactionOrderRequest;
import com.project.KoiOrderingSystem.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin("*")
@SecurityRequirement(name = "api")


public class TransactionAPI {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/booking")
    public ResponseEntity createTransactionBooking(@RequestParam long bookingId) {
        transactionService.createdTransactionBooking(bookingId);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/order")
    public ResponseEntity createTransactionOrder(@RequestBody TransactionOrderRequest transactionOrderRequest) {
        transactionService.createdTransactionOrder(transactionOrderRequest);
        return ResponseEntity.ok("Success");
    }
}
