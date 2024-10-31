package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Booking;
import com.project.KoiOrderingSystem.entity.StatusOrder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResponse {

    UUID id;

    String image;

    LocalDate expectedDate;

    Date deliveredDate;

    float price;

    String address;

    StatusOrder status;

    List<OrderDetailResponse> orderDetailResponseList;

    Booking booking;

}
