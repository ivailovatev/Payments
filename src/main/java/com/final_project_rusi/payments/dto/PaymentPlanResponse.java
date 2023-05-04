package com.final_project_rusi.payments.dto;

import java.math.BigDecimal;

public class PaymentPlanResponse {
    private int indivId;
    private BigDecimal amount;

    public PaymentPlanResponse() {
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
        return "PaymentPlanResponse{" +
                "indivId=" + indivId +
                ", amount=" + amount +
                '}';
    }
}
