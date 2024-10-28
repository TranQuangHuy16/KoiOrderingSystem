package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.Role;
import com.project.KoiOrderingSystem.entity.Transactions;
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

    @Autowired
    TransactionRepository transactionRepository;


    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalAccounts", accountRepository.countCustomerByRole(Role.CUSTOMER));
        stats.put("totalKoi", koiRepository.findKoiFishesByIsDeletedFalse().stream().count());
        stats.put("totalFarm", farmRepository.findFarmsByIsDeletedFalse().stream().count());
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

    public Map<String, Object> getRevenueBookingByMonth() {
        Map<String, Object> revenuebookingMonth = new HashMap<>();

        List<Object[]> revenue = transactionRepository.getRevenueBookingByMonth();
        List<Map<String, Object>> revenueBooking = new ArrayList<>();

        for(Object[] r : revenue) {
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("year", r[0]);
            monthData.put("month", r[1]);
            monthData.put("totalOfMonth", r[2]);
            revenueBooking.add(monthData);
        }
        revenuebookingMonth.put("revenueBooking", revenueBooking);
        return revenuebookingMonth;
    }

    public Map<String, Object> getRevenueOrderByMonth() {
        Map<String, Object> revenueOrderMonth = new HashMap<>();

        List<Object[]> revenue = transactionRepository.getRevenueOrderByMonth();
        List<Map<String, Object>> revenueOrder = new ArrayList<>();

        for(Object[] r : revenue) {
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("year", r[0]);
            monthData.put("month", r[1]);
            monthData.put("totalOfMonth", r[2]);
            revenueOrder.add(monthData);
        }
        revenueOrderMonth.put("revenueOrder", revenueOrder);
        return revenueOrderMonth;
    }

    public Map<String, Object> getRevenueAllByMonth() {
        Map<String, Object> revenueAllMonth = new HashMap<>();

        List<Object[]> revenue = transactionRepository.getRevenueAllByMonth();
        List<Map<String, Object>> revenueAll = new ArrayList<>();

        for(Object[] r : revenue) {
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("year", r[0]);
            monthData.put("month", r[1]);
            monthData.put("totalOfMonth", r[2]);
            revenueAll.add(monthData);
        }
        revenueAllMonth.put("revenueAll", revenueAll);
        return revenueAllMonth;
    }
}
