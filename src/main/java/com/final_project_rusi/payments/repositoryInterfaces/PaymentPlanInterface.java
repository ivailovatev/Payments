package com.final_project_rusi.payments.repositoryInterfaces;

import com.final_project_rusi.payments.dto.PaymentPlanResponse;
import com.final_project_rusi.payments.dto.RemainingPlansResponse;
import com.final_project_rusi.payments.models.PaymentPlan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentPlanInterface {

    public int insertPaymentPlan(int indivId, BigDecimal amount);

    public int updatePaymentPlanAmount(int planId, BigDecimal amount);

    public Optional<PaymentPlanResponse> getPaymentPlan(int planId);

    public List<PaymentPlan> getAllPaymentPlans();

    public int deletePaymentPlan(int planId);

    public List<RemainingPlansResponse> getPlansRemainingToBePaid();

    public Optional<BigDecimal> showPlanRemainingMoney(int planId, int indivId);
}
