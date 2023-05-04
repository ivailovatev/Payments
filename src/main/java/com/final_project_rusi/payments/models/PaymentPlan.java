package com.final_project_rusi.payments.models;

import java.math.BigDecimal;

public class PaymentPlan {
    private int planId;
    private int indivId;
    private BigDecimal amount;

    public PaymentPlan() {
    }

    public PaymentPlan(int planId, int indivId, BigDecimal amount) {
        this.planId = planId;
        this.indivId = indivId;
        this.amount = amount;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getIndivId() {
        return indivId;
    }

    public void setIndivId(int indivId) {
        this.indivId = indivId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "PaymentPlan{" +
                "planId=" + planId +
                ", indivId=" + indivId +
                ", amount=" + amount +
                '}';
    }
}
