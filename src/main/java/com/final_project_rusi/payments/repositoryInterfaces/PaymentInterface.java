package com.final_project_rusi.payments.repositoryInterfaces;

import com.final_project_rusi.payments.dto.PaymentHistoryResponse;
import com.final_project_rusi.payments.dto.PaymentResponse;
import com.final_project_rusi.payments.models.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentInterface {
    public int insertPayment(int planId, BigDecimal amount);

    public int updatePaymentAmount(int paymentId, BigDecimal amount);

    public Optional<PaymentResponse> getPayment(int paymentId);

    public List<Payment> getAllPayments();

    public int deletePayment(int paymentId);

    public List<PaymentHistoryResponse> getPaymentsPerPlanForIndividual(int planId, int indivId);

}
