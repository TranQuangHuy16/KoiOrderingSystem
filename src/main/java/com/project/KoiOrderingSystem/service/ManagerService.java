package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.Role;
import com.project.KoiOrderingSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    KoiRepository koiRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    FarmRepository farmRepository;


    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalAccounts", accountRepository.countCustomerByRole(Role.CUSTOMER));
        stats.put("totalKoi", koiRepository.count());
        stats.put("totalFarm", farmRepository.count());
        stats.put("totalBooking", bookingRepository.count());
        stats.put("totalOrder", orderRepository.count());

        List<Object[]> topKoi = koiRepository.findTopKoiSold();
        List<Map<String, Object>> topKoiSold = new ArrayList<>();

        for (Object[] koi : topKoi) {
            Map<String, Object> koiSold = new HashMap<>();
            koiSold.put("name", koi[0]);
            koiSold.put("totalSold", koi[1]);
            topKoiSold.add(koiSold);
        }

        stats.put("topKoiSold", topKoiSold);
        return stats;

    }
}
