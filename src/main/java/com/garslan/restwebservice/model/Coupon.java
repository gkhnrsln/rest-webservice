package com.garslan.restwebservice.model;

import java.util.Calendar;
import lombok.Data;

@Data
public class Coupon {
    /** 13-stellige EAN/GTIN */
    private String id;
    /** Aktionsname des Coupons */
    private String aktionsName;
    /** Start-Datum des Aktionszeitraumes eines Coupons */
    private Calendar startDatum;
    /** End-Datum des Aktionszeitraumes eines Coupons */
    private Calendar endDatum;
    /** Rabattwert des Coupons */
    private double rabatt;
    /** Status des Coupons */
    private boolean isActive;
}
