package com.final_project_rusi.payments.services;


import com.final_project_rusi.payments.dto.PaymentPlanResponse;
import com.final_project_rusi.payments.dto.RemainingPlansResponse;
import com.final_project_rusi.payments.models.PaymentPlan;
import com.final_project_rusi.payments.repositories.PaymentPlanRepository;
import com.final_project_rusi.payments.validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentPlanService {

    private final PaymentPlanRepository paymentPlanRepository;
    private final Validation validation;

    @Autowired
    public PaymentPlanService(PaymentPlanRepository paymentPlanRepository, Validation validation) {
        this.paymentPlanRepository = paymentPlanRepository;
        this.validation = validation;
    }

    public int createPaymentPlan(int indivId, BigDecimal amount) {
        validation.getIndividualOrThrow(indivId);
        return paymentPlanRepository.insertPaymentPlan(indivId, amount);
    }

    public int changePaymentPlanAmount(int planId, BigDecimal amount) {
        validation.getPaymentPlanOrThrow(planId);
        return paymentPlanRepository.updatePaymentPlanAmount(planId, amount);
    }

    public PaymentPlanResponse getPaymentPlan(int planId) {
        return validation.getPaymentPlanOrThrow(planId);
    }

    public List<PaymentPlan> getAllPaymentPlans() {
        return paymentPlanRepository.getAllPaymentPlans();
    }

    public int removePaymentPlan(int planId) {
        validation.checkIfPaymentPlanPaid(planId);
        return paymentPlanRepository.deletePaymentPlan(planId);
    }

    public List<RemainingPlansResponse> getPlansRemainingToBePaid() {
        return paymentPlanRepository.getPlansRemainingToBePaid();
    }

}
