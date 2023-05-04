package com.final_project_rusi.payments.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentResponse {
    private int planId;
    private BigDecimal amount;
    private LocalDate localDate;

    public PaymentResponse() {
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return localDate;
    }

    public void setDate(LocalDate date) {
        this.localDate = date;
    }

    @Override
    public String toString() {
        return "PaymentView{" +
                "planId=" + planId +
                ", amount=" + amount +
                ", date=" + localDate +
                '}';
    }
}
