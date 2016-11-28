package com.ln.presentation;

import java.math.BigDecimal;

public class CreditApplication {

    private String uid;
    private BigDecimal amount;
    private String name;
    private String surnname;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnname() {
        return surnname;
    }

    public void setSurnname(String surnname) {
        this.surnname = surnname;
    }

    @Override
    public String toString() {
        return "CreditApplication{" +
                "uid='" + uid + '\'' +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", surnname='" + surnname + '\'' +
                '}';
    }
}
