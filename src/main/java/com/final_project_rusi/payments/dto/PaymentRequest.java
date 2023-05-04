package com.final_project_rusi.payments.dto;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

import static com.final_project_rusi.payments.messages.ExceptionMessages.PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO;

public class PaymentRequest {
    private int planId;

    @DecimalMin(value="0",inclusive = false,message = PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO)
    private BigDecimal amount;

    public PaymentRequest() {
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
        return "PaymentRequest{" +
                "planId=" + planId +
                ", amount=" + amount +
                '}';
    }
}
