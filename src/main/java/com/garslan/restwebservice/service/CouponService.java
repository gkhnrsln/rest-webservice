package com.garslan.restwebservice.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.garslan.restwebservice.exception.EntityAlreadyExistsException;
import com.garslan.restwebservice.exception.EntityNotFoundException;
import com.garslan.restwebservice.model.Coupon;

import org.springframework.stereotype.Service;

@Service
public class CouponService {
    private List<Coupon> coupons;

    public List<Coupon> getCoupons() {
        initCoupons();
        return coupons;
    }

    public List<Coupon> getActiveCoupons() {
        LocalDateTime today = LocalDateTime.now();

        List<Coupon> activeCoupons = new ArrayList<>();
        for (Coupon c : coupons) {
            if (c.isActive() && (!today.isBefore(c.getStartDate()) && !today.isAfter(c.getEndDate()))) {
                activeCoupons.add(c);
            }
        }
        return activeCoupons;
    }

    private void initCoupons() {
        if (coupons == null) {
            Coupon coupon = new Coupon();
            coupon.setId("981" + "1234567890");
            coupon.setCampaignName("0.50 € Rabatt auf Coca Cola");
            coupon.setStartDate(LocalDateTime.parse("2021-01-20T00:00:00"));
            coupon.setEndDate(LocalDateTime.parse("2021-04-20T00:00:00"));
            coupon.setDiscount(50);
            coupon.setActive(true);

            Coupon coupon2 = new Coupon();
            coupon2.setId("982" + "0987654321");
            coupon2.setCampaignName("0.25 € Rabatt auf eine Pepsi");
            coupon2.setStartDate(LocalDateTime.parse("2019-01-20T00:00:00"));
            coupon2.setEndDate(LocalDateTime.parse("2020-04-20T00:00:00"));
            coupon2.setDiscount(25);
            coupon2.setActive(true);

            coupons = new ArrayList<>();
            coupons.add(coupon);
            coupons.add(coupon2);
        }
    }

    public Coupon getCouponById(String id) {
        return coupons.stream().filter(cou -> cou.getId().equals(id)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Coupon not found with id: %s", id)));
    }

    public Coupon createCoupon(Coupon coupon) {
        checkExistingCoupon(coupon);
        // pruefe, ob EAN richtige Format besitzt
        if (coupon.getId().matches("^98(1|2)\\d{10}$")) {
            coupons.add(coupon);
            return coupon;
        } else {
            throw new EntityAlreadyExistsException(String.format("Coupon with id: %s has false format", coupon.getId()));
        }
    }

    private void checkExistingCoupon(Coupon coupon) {
        coupons.stream()
                .filter(cou -> cou.getId().equals(coupon.getId()))
                .findFirst()
                .ifPresent(cou -> {
                    throw new EntityAlreadyExistsException(String.format("Coupon with id: %s is already existing", coupon.getId()));
                });

    }

    public void deleteCoupon(String id) {
        coupons = coupons.stream()
                .filter(coupon -> !coupon.getId().equals(id))
                .collect(Collectors.toList());
    }
}
