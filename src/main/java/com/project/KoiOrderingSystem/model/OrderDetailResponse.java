package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Booking;
import com.project.KoiOrderingSystem.entity.KoiFish;
import com.project.KoiOrderingSystem.entity.OrderDetail;
import lombok.Data;

@Data
public class OrderDetailResponse {

        KoiFishResponse koiFishResponse;

        int quantity;

        float price;

}
