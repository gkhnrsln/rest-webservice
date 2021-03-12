package com.garslan.restwebservice.controller;

import java.util.List;

import com.garslan.restwebservice.model.Coupon;
import com.garslan.restwebservice.service.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/coupons")
@AllArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        return new ResponseEntity<>(couponService.createCoupon(coupon), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable String id, @RequestBody Coupon coupon){
        Coupon updatedCoupon = couponService.getCouponById(id);
        updatedCoupon.setActive(coupon.isActive());
        updatedCoupon.setCampaignName(coupon.getCampaignName());
        updatedCoupon.setEndDate(coupon.getEndDate());
        updatedCoupon.setStartDate(coupon.getStartDate());
        updatedCoupon.setDiscount(coupon.getDiscount());
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable String id) {
        couponService.deleteCoupon(id);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getCoupons() {
        List<Coupon> coupons = couponService.getCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Coupon>> getActiveCoupons() {
        List<Coupon> coupons = couponService.getActiveCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable String id) {
        Coupon coupon = couponService.getCouponById(id);
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }
}
