package com.final_project_rusi.payments.dto;

import java.math.BigDecimal;

public class PaymentHistoryResponse {
    private String name;
    private int planId;
    BigDecimal planAmount;
    BigDecimal paymentAmount;

    public PaymentHistoryResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public BigDecimal getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(BigDecimal planAmount) {
        this.planAmount = planAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }


    @Override
    public String toString() {
        return "PaymentHistoryView{" +
                "name='" + name + '\'' +
                ", planId=" + planId +
                ", planAmount=" + planAmount +
                ", paymentAmount=" + paymentAmount +
                '}';
    }
}
