package com.final_project_rusi.payments.dto;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

import static com.final_project_rusi.payments.messages.ExceptionMessages.PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO;

public class PaymentPlanRequest {

    private int indivId;


    @DecimalMin(value = "0",inclusive = false,message = PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO)
    private BigDecimal amount;

    public PaymentPlanRequest() {
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
        return "PaymentPlansView{" +
                "indivId=" + indivId +
                ", amount=" + amount +
                '}';
    }
}
