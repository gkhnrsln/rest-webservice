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

    /**
     * Einen neuen Coupon anlegen.
     */
    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        Coupon createdCoupon = couponService.createCoupon(coupon);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    /** 
     * Einen bestehenden Coupon aendern.
     */ 
    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable String id, @RequestBody Coupon coupon){
        Coupon updatedCoupon = couponService.getCouponById(id);
        updatedCoupon.setActive(coupon.isActive());
        updatedCoupon.setAktionsName(coupon.getAktionsName());
        updatedCoupon.setEndDatum(coupon.getEndDatum());
        updatedCoupon.setStartDatum(coupon.getStartDatum());
        updatedCoupon.setRabatt(coupon.getRabatt());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Einen Coupon loeschen.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable String id) {
        couponService.deleteCoupon(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Zeige alle vorhandenen Coupons.
     */
    @GetMapping
    public ResponseEntity<List<Coupon>> getCoupons() {
        List<Coupon> coupons = couponService.getCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }
    

    /**
     * Zeige alle vorhandenen Coupons, die aktiv und gueltig sind.
     */
    @GetMapping("/active")
    public ResponseEntity<List<Coupon>> getActiveCoupons() {
        List<Coupon> coupons = couponService.getActiveCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    /**
     * Einen Coupon anhand seiner EAN/GTIN abrufen.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable String id) {
        Coupon coupon = couponService.getCouponById(id);
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }
}
