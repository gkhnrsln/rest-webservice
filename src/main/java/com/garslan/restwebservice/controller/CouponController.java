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
        Coupon createdCoupon = couponService.createCoupon(coupon);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable String id, @RequestBody Coupon coupon){
        Coupon updatedCoupon = couponService.getCouponById(id);
        if (updatedCoupon == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updatedCoupon.setActive(coupon.isActive());
        updatedCoupon.setCampaignName(coupon.getCampaignName());
        updatedCoupon.setEndDate(coupon.getEndDate());
        updatedCoupon.setStartDate(coupon.getStartDate());
        updatedCoupon.setDiscount(coupon.getDiscount());
        couponService.updateCoupon(updatedCoupon);

        return ResponseEntity.ok(updatedCoupon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Coupon> deleteCoupon(@PathVariable String id) {
        Coupon deletedCoupon = couponService.getCouponById(id);
        if (deletedCoupon == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        couponService.deleteCoupon(id);
        
        return ResponseEntity.ok(deletedCoupon);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getCoupons() {
        List<Coupon> coupons = couponService.getCoupons();

        return ResponseEntity.ok(coupons);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Coupon>> getActiveCoupons() {
        List<Coupon> coupons = couponService.getActiveCoupons();

        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable String id) {
        Coupon coupon = couponService.getCouponById(id);
        if (coupon == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(coupon);
    }
}
