package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.service.ManagerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class DashboardAPI {

    @Autowired
    ManagerService managerService;

    @GetMapping("/stats")
    public ResponseEntity getDashboardStats() {
        Map<String, Object> stats = managerService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/revenueBooking/month")
    public ResponseEntity getRevenueBookingByMonth() {
        Map<String, Object> revenue = managerService.getRevenueBookingByMonth();
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/revenueOrder/month")
    public ResponseEntity getRevenueOrderByMonth() {
        Map<String, Object> revenue = managerService.getRevenueOrderByMonth();
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/revenueAll/month")
    public ResponseEntity getRevenueAllByMonth() {
        Map<String, Object> revenue = managerService.getRevenueAllByMonth();
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/totalBooking/month")
    public ResponseEntity getBookingByMonth() {
        Map<String, Object> total = managerService.getBookingByMonth();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/totalOrder/month")
    public ResponseEntity getOrderByMonth() {
        Map<String, Object> total = managerService.getOrderByMonth();
        return ResponseEntity.ok(total);
    }

}
