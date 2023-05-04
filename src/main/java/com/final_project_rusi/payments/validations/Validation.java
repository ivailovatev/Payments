package com.final_project_rusi.payments.validations;

import com.final_project_rusi.payments.dto.IndividualResponse;
import com.final_project_rusi.payments.dto.PaymentPlanResponse;
import com.final_project_rusi.payments.dto.PaymentResponse;
import com.final_project_rusi.payments.exceptions.BadRequestException;
import com.final_project_rusi.payments.exceptions.NotFoundException;
import com.final_project_rusi.payments.repositories.IndividualRepository;
import com.final_project_rusi.payments.repositories.PaymentPlanRepository;
import com.final_project_rusi.payments.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.final_project_rusi.payments.messages.ExceptionMessages.*;

@Component
public class Validation {
    private final IndividualRepository individualRepository;
    private final PaymentPlanRepository paymentPlanRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public Validation(IndividualRepository individualRepository,
                      PaymentPlanRepository paymentPlanRepository,
                      PaymentRepository paymentRepository) {

        this.individualRepository = individualRepository;
        this.paymentPlanRepository = paymentPlanRepository;
        this.paymentRepository = paymentRepository;
    }

    public IndividualResponse getIndividualOrThrow(int indivId) {
        return individualRepository.getIndividual(indivId).orElseThrow(
                ()-> new NotFoundException(INVALID_INDIVIDUAL_ID)
        );
    }

    public PaymentPlanResponse getPaymentPlanOrThrow(int planId) {
        return paymentPlanRepository.getPaymentPlan(planId).orElseThrow(
                ()->new NotFoundException(INVALID_PAYMENT_PLAN_ID)
        );
    }

    public PaymentResponse getPaymentOrThrow(int paymentId) {
       return paymentRepository.getPayment(paymentId).orElseThrow(
               ()->new NotFoundException(INVALID_PAYMENT_ID)
       );
    }

    public BigDecimal getPlanRemainingMoneyOrThrow(int planId, int indivId){
        return paymentPlanRepository.showPlanRemainingMoney(planId,indivId).orElseThrow(
                ()-> new BadRequestException(INVALID_PAYMENT_PLAN_AMOUNT)
        );
    }



    public void checkIfPaymentPlanPaid(int planId) {
        int indivId = getPaymentPlanOrThrow(planId).getIndivId();
        BigDecimal paymentPlanRemainingAmount= getPlanRemainingMoneyOrThrow(planId,indivId);
        if (paymentPlanRemainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException(PAYMENT_PLAN_ALREADY_PAID);
        }
    }

    public void checkMoreMoneyThanYouShouldPayByPlanId(int planId, BigDecimal amount) {
        int indivId = getPaymentPlanOrThrow(planId).getIndivId();
        BigDecimal paymentPlanRemainingAmount= getPlanRemainingMoneyOrThrow(planId,indivId);
        BigDecimal result = paymentPlanRemainingAmount.subtract(amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException(INVALID_MONEY_MORE_THAN_THE_PAYMENT_PLAN_AMOUNT);
        }
    }

    public void checkMoreMoneyThanYouShouldPayByPaymentId(int paymentId, BigDecimal amount) {
        int planId = getPaymentOrThrow(paymentId).getPlanId();
        PaymentPlanResponse paymentPlanResponse=getPaymentPlanOrThrow(planId);
        int indivId = paymentPlanResponse.getIndivId();
        BigDecimal paymentPlanRemainingAmount= getPlanRemainingMoneyOrThrow(planId,indivId)
                .add(paymentPlanResponse.getAmount());
        BigDecimal result = paymentPlanRemainingAmount.subtract(amount);

        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException(INVALID_MONEY_MORE_THAN_THE_PAYMENT_PLAN_AMOUNT);
        }
    }

}
