package com.final_project_rusi.payments.models;


import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private int planId;
    private BigDecimal amount;
    private LocalDate localDate;

    public Payment() {
    }

    public Payment(int paymentId, int planId, BigDecimal amount, LocalDate localDate) {
        this.paymentId = paymentId;
        this.planId = planId;
        this.amount = amount;
        this.localDate = localDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", planId=" + planId +
                ", amount=" + amount +
                ", date=" + localDate +
                '}';
    }
}
