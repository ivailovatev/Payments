package com.final_project_rusi.payments.controllers;

import com.final_project_rusi.payments.dto.PaymentHistoryResponse;
import com.final_project_rusi.payments.dto.PaymentRequest;
import com.final_project_rusi.payments.dto.PaymentResponse;
import com.final_project_rusi.payments.models.Payment;
import com.final_project_rusi.payments.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import static com.final_project_rusi.payments.messages.ExceptionMessages.PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO;

@RestController
@Validated
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public int createPayment(@Valid @RequestBody PaymentRequest paymentRequest) throws ParseException {
        return paymentService.createPayment(paymentRequest.getPlanId(), paymentRequest.getAmount());
    }

    @PatchMapping("/{paymentId}/amount")
    @ResponseStatus(HttpStatus.OK)
    public int changePaymentAmount(@PathVariable int paymentId,
                                   @Valid @RequestParam @DecimalMin(value ="0",inclusive = false,message = PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO)
                                   BigDecimal amount) {
        return paymentService.changePaymentAmount(paymentId, amount);
    }

    @GetMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponse showPayment(@PathVariable int paymentId) {
        return paymentService.getPayment(paymentId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Payment> showAllPayments() {
        return paymentService.getAllPayments();
    }

    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public int deletePayment(@PathVariable int paymentId) {
        return paymentService.removePayment(paymentId);
    }

    @GetMapping("/{planId}/{indivId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentHistoryResponse> showIndividualPaymentsForPlan(@PathVariable int planId, @PathVariable int indivId) {
        return paymentService.getPaymentsPerPlanForIndividual(planId, indivId);
    }
}
