package com.garslan.restwebservice.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.garslan.restwebservice.exception.EntityAlreadyExistsException;
import com.garslan.restwebservice.exception.EntityNotFoundException;
import com.garslan.restwebservice.model.Coupon;

import org.springframework.stereotype.Service;

@Service
public class CouponService {
    private List<Coupon> coupons;
    private static final Pattern COUPON_ID_PATTERN = Pattern.compile("^98(1|2)\\d{10}$");

    private void initCoupons() {
        if (coupons == null) {
            coupons = new ArrayList<>();
            coupons.add(createSampleCoupon("9811234567890", "0.50 € Rabatt auf Coca Cola", "2021-01-20T00:00:00", "2021-04-20T00:00:00", 50, true));
            coupons.add(createSampleCoupon("9820987654321", "0.25 € Rabatt auf eine Pepsi", "2019-01-20T00:00:00", "2020-04-20T00:00:00", 25, true));
        }
    }

    public List<Coupon> getCoupons() {
        initCoupons();
        return coupons;
    }

    public List<Coupon> getActiveCoupons() {
        LocalDateTime today = LocalDateTime.now();

        return coupons.stream()
                .filter(c -> c.isActive() && !today.isBefore(c.getStartDate()) && !today.isAfter(c.getEndDate()))
                .collect(Collectors.toList());
    }

    private Coupon createSampleCoupon(String id, String campaignName, String startDate, String endDate, int discount, boolean isActive) {
        Coupon coupon = new Coupon();
        coupon.setId(id);
        coupon.setCampaignName(campaignName);
        coupon.setStartDate(LocalDateTime.parse(startDate));
        coupon.setEndDate(LocalDateTime.parse(endDate));
        coupon.setDiscount(discount);
        coupon.setActive(isActive);

        return coupon;
    }

    public Coupon getCouponById(String id) {
        return coupons.stream()
                .filter(cou -> cou.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Coupon not found with id: %s", id)));
    }

    public Coupon createCoupon(Coupon coupon) {
        checkExistingCoupon(coupon);
        
        if (!COUPON_ID_PATTERN.matcher(coupon.getId()).matches()) {
            throw new IllegalArgumentException(String.format("Coupon with id: %s has an invalid format", coupon.getId()));
        }
        
        coupons.add(coupon);
        
        return coupon;
    }

    private void checkExistingCoupon(Coupon coupon) {
        coupons.stream()
                .filter(c -> c.getId().equals(coupon.getId()))
                .findFirst()
                .ifPresent(c -> {
                    throw new EntityAlreadyExistsException(String.format("Coupon with id: %s is already existing", c.getId()));
                });
    }

    public void deleteCoupon(String id) {
        boolean removed = coupons.removeIf(c -> c.getId().equals(id));

        if (!removed) {
            throw new EntityNotFoundException(String.format("Coupon with id: %s not found", id));
        }
    }

    public Coupon updateCoupon(Coupon updatedCoupon) {
        for (int i = 0; i < coupons.size(); i++) {
            if(coupons.get(i).getId().equals(updatedCoupon.getId())) {
                coupons.set(i, updatedCoupon);
                return updatedCoupon;
            }
        }
        throw new EntityNotFoundException(String.format("Coupon with id: %s not found", updatedCoupon.getId()));
    }
}
