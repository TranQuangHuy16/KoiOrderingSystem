package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Booking;
import com.project.KoiOrderingSystem.entity.StatusOrder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {

    long id;

    String image;

    LocalDate expectedDate;

    Date deliveredDate;

    float price;

    String address;

    StatusOrder status;

    List<OrderDetailResponse> orderDetailResponseList;

    Booking booking;

}
