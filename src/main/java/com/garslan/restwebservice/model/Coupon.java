package com.garslan.restwebservice.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Coupon {
    private String id;
    private String campaignName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int discount;
    private boolean isActive;
}
