package com.final_project_rusi.payments.controllers;

import com.final_project_rusi.payments.dto.PaymentPlanRequest;
import com.final_project_rusi.payments.dto.PaymentPlanResponse;
import com.final_project_rusi.payments.dto.RemainingPlansResponse;
import com.final_project_rusi.payments.models.PaymentPlan;
import com.final_project_rusi.payments.services.PaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

import static com.final_project_rusi.payments.messages.ExceptionMessages.PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO;

@RestController
@Validated
@RequestMapping("/plans")
public class PaymentPlanController {

    private final PaymentPlanService paymentPlanService;

    @Autowired
    public PaymentPlanController(PaymentPlanService paymentPlanService) {
        this.paymentPlanService = paymentPlanService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public int createPaymentPlan(@Valid @RequestBody PaymentPlanRequest paymentPlanRequest) {
        return paymentPlanService.createPaymentPlan(paymentPlanRequest.getIndivId(), paymentPlanRequest.getAmount());
    }

    @PatchMapping("/{planId}/amount")
    @ResponseStatus(HttpStatus.OK)
    public int changePaymentPlanAmount(@PathVariable int planId,
                                       @Valid @RequestParam @DecimalMin(value ="0",inclusive = false,message = PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO)BigDecimal amount) {
        return paymentPlanService.changePaymentPlanAmount(planId, amount);
    }

    @GetMapping("/{planId}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentPlanResponse showPaymentPlan(@PathVariable int planId) {
        return paymentPlanService.getPaymentPlan(planId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentPlan> showAllPaymentPlans() {
        return paymentPlanService.getAllPaymentPlans();
    }

    @DeleteMapping("/{planId}")
    @ResponseStatus(HttpStatus.OK)
    public int deletePaymentPlan(@PathVariable int planId) {
        return paymentPlanService.removePaymentPlan(planId);
    }

    @GetMapping("/unpaid")
    @ResponseStatus(HttpStatus.OK)
    public List<RemainingPlansResponse> showPlansRemainingToBePaid() {
        return paymentPlanService.getPlansRemainingToBePaid();
    }
}
