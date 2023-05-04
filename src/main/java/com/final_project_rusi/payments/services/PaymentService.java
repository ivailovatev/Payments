package com.final_project_rusi.payments.services;

import com.final_project_rusi.payments.dto.PaymentHistoryResponse;
import com.final_project_rusi.payments.dto.PaymentResponse;
import com.final_project_rusi.payments.models.Payment;
import com.final_project_rusi.payments.repositories.PaymentRepository;
import com.final_project_rusi.payments.validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final Validation validation;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, Validation validation) {
        this.paymentRepository = paymentRepository;
        this.validation = validation;
    }

    public int createPayment(int planId, BigDecimal amount) {
        validation.checkIfPaymentPlanPaid(planId);
        validation.checkMoreMoneyThanYouShouldPayByPlanId(planId, amount);
        return paymentRepository.insertPayment(planId, amount);
    }

    public int changePaymentAmount(int paymentId, BigDecimal amount) {
        int planId=validation.getPaymentOrThrow(paymentId).getPlanId();
        validation.checkIfPaymentPlanPaid(planId);
        validation.checkMoreMoneyThanYouShouldPayByPaymentId(paymentId, amount);
        return paymentRepository.updatePaymentAmount(paymentId, amount);
    }

    public PaymentResponse getPayment(int paymentId) {
        return validation.getPaymentOrThrow(paymentId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    public int removePayment(int paymentId) {
        validation.getPaymentOrThrow(paymentId);
        return paymentRepository.deletePayment(paymentId);
    }

    public List<PaymentHistoryResponse> getPaymentsPerPlanForIndividual(int planId, int indivId) {
        validation.getIndividualOrThrow(indivId);
        validation.getPaymentPlanOrThrow(planId);
        return paymentRepository.getPaymentsPerPlanForIndividual(planId, indivId);
    }
}
