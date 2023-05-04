package com.final_project_rusi.payments.dto;

import java.math.BigDecimal;

public class RemainingPlansResponse {
    private String name;
    private int planId;
    private BigDecimal amount;

    public RemainingPlansResponse() {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RemainingPlansView{" +
                "name='" + name + '\'' +
                ", planId=" + planId +
                ", amount=" + amount +
                '}';
    }
}
