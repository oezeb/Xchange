package com.oezeb.xchange.models;

import java.sql.Date;
import java.time.LocalDate;

public class Quote {
    public String base;
    public String second;
    public Double rate;
    public Date date;

    public Quote() {
        this("", "", 1.0, Date.valueOf(LocalDate.now()));
    }

    public Quote(String base, String second, Double rate, Date date) {
        this.base = base;
        this.second = second;
        this.rate = rate;
        this.date = date;
    }

    @Override
    public String toString() {
        return base + "/" + second + "   " + rate + "   " + date;
    }
}
