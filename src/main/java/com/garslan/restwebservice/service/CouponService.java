package com.garslan.restwebservice.service;

import java.util.*;
import java.util.stream.Collectors;

import com.garslan.restwebservice.exception.EntityAlreadyExistsException;
import com.garslan.restwebservice.exception.EntityNotFoundException;
import com.garslan.restwebservice.model.Coupon;

import org.springframework.stereotype.Service;

@Service
public class CouponService {
    private static List<Coupon> coupons;

    public List<Coupon> getCoupons() {
        initCoupons();
        return coupons;
    }

    public List<Coupon> getActiveCoupons() {
        Calendar today = new GregorianCalendar();
        List<Coupon> activeCoupons = new ArrayList<>();
        for (Coupon c : coupons) {
            if (c.isActive() && (!today.before(c.getStartDatum()) && !today.after(c.getEndDatum()))) {
                activeCoupons.add(c);
            }
        }
        return activeCoupons;
    }

    private void initCoupons() {
        if (coupons == null) {
            Coupon coupon = new Coupon();
            coupon.setId("981" + "1234567890");
            coupon.setAktionsName("0.50 € Rabatt auf Coca Cola");
            coupon.setStartDatum(new GregorianCalendar(2021, 0, 20));
            coupon.setEndDatum(new GregorianCalendar(2021, 3, 20));
            coupon.setRabatt(0.5);
            coupon.setActive(true);

            Coupon coupon2 = new Coupon();
            coupon2.setId("982" + "0987654321");
            coupon2.setAktionsName("0.25 € Rabatt auf eine Pepsi");
            coupon2.setStartDatum(new GregorianCalendar(2019, 0, 20));
            coupon2.setEndDatum(new GregorianCalendar(2020, 3, 20));
            coupon2.setRabatt(0.25);
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
