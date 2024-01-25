package com.baeldung.drools.model;

import me.samarthsinha.rugram.core.models.RuleTransaction;

import java.io.Serializable;

public class Customer extends RuleTransaction implements Serializable {
    int years;
    int discount;
    CustomerType type;


    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public enum  CustomerType {
        INDIVIDUAL,BUSINESS;
    }
}
