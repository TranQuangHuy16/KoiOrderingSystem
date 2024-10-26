package com.project.KoiOrderingSystem.service;

import com.project.KoiOrderingSystem.entity.*;
import com.project.KoiOrderingSystem.model.*;
import com.project.KoiOrderingSystem.repository.OrderRepository;
import jakarta.validation.Valid;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public String paymentOrder(OrderPaymentRequest orderPaymentRequest) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);

        //code của mình
        Orders order = getOrderById(orderPaymentRequest.getId());
        float money = order.getPrice() * 100;
        String amount = String.valueOf((int) money);



        String tmnCode = "J8GSGBC5";
        String secretKey = "S7IQI58YMDLNRT5CVPGTLQLV7EJ325KC";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "http://localhost:5173/profile/orders?orderId=" + orderPaymentRequest.getId();
        String currCode = "VND";

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", String.valueOf(orderPaymentRequest.getId()));
        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma GD: " + orderPaymentRequest.getId());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount",amount);

        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "128.199.178.23");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove last '&'

        String signData = signDataBuilder.toString();
        String signed = generateHMAC(secretKey, signData);

        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Remove last '&'

        return urlBuilder.toString();
    }

    private String generateHMAC(String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
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
