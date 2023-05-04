package com.final_project_rusi.payments.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project_rusi.payments.dto.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.final_project_rusi.payments.messages.ExceptionMessages.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = {"/QueriesForTest.sql"})
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreatePayment() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPlanId(-7);
        paymentRequest.setAmount(BigDecimal.valueOf(20));
        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void negativeTestCreatePaymentInvalidPlanId() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPlanId(-1);
        paymentRequest.setAmount(BigDecimal.valueOf(20));
        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_PAYMENT_PLAN_ID))
                .andDo(print());
    }

    @Test
    public void negativeTestCreatePaymentAmountLessThanZero() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPlanId(-7);
        paymentRequest.setAmount(BigDecimal.valueOf(-1000));
        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO))
                .andDo(print());
    }

    @Test
    public void negativeTestCreatePaymentAmountEqualsToZero() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPlanId(-7);
        paymentRequest.setAmount(BigDecimal.valueOf(0));
        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO))
                .andDo(print());
    }

    @Test
    public void negativeTestCreatePaymentPlanPaid() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPlanId(-9);
        paymentRequest.setAmount(BigDecimal.valueOf(20));
        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(PAYMENT_PLAN_ALREADY_PAID))
                .andDo(print());
    }


    @Test
    public void negativeTestCreatePaymentMoreMoneyThanYouShouldPay() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPlanId(-7);
        paymentRequest.setAmount(BigDecimal.valueOf(200000));
        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(INVALID_MONEY_MORE_THAN_THE_PAYMENT_PLAN_AMOUNT))
                .andDo(print());
    }


    @Test
    public void testChangePaymentAmount() throws Exception {
        mockMvc
                .perform(patch("/payments/{paymentId}/amount",-3)
                        .queryParam("amount", String.valueOf(BigDecimal.valueOf(2999.99))))
                .andExpect(status().isOk())
                .andDo(print());
        testShowPaymentWithId3();
    }


    private void testShowPaymentWithId3() throws Exception {
        mockMvc
                .perform(get("/payments/{paymentId}", -3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planId").value(-3))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(2999.99)))
                .andDo(print());
    }

    @Test
    public void negativeTestChangePaymentAmountInvalidId() throws Exception {
        mockMvc
                .perform(patch("/payments/{paymentId}/amount",-1)
                        .queryParam("amount", String.valueOf(BigDecimal.valueOf(2000))))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_PAYMENT_ID))
                .andDo(print());
    }

    @Test
    public void negativeTestChangePaymentAmountLessThanZero() throws Exception {
        mockMvc
                .perform(patch("/payments/{paymentId}/amount",-4)
                        .queryParam("amount", String.valueOf(BigDecimal.valueOf(-1000))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO))
                .andDo(print());
    }

    @Test
    public void negativeTestChangePaymentAmountEqualsToZero() throws Exception {
        mockMvc
                .perform(patch("/payments/{paymentId}/amount",-4)
                        .queryParam("amount", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO))
                .andDo(print());
    }

    @Test
    public void negativeTestChangePaymentAmountPaymentPlanPaid() throws Exception {
        mockMvc
                .perform(patch("/payments/{paymentId}/amount",-7)
                        .queryParam("amount", String.valueOf(BigDecimal.valueOf(2000))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(PAYMENT_PLAN_ALREADY_PAID))
                .andDo(print());
    }

    @Test
    public void negativeTestChangePaymentAmountMoreThanYouShouldPay() throws Exception {
        mockMvc
                .perform(patch("/payments/{paymentId}/amount",-4)
                        .queryParam("amount", String.valueOf(BigDecimal.valueOf(2000000))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(INVALID_MONEY_MORE_THAN_THE_PAYMENT_PLAN_AMOUNT))
                .andDo(print());
    }

    @Test
    public void testShowPayment() throws Exception {
        mockMvc
                .perform(get("/payments/{paymentId}", -3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planId").value(-3))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(2000)))
                .andDo(print());
    }

    @Test
    public void negativeTestShowPaymentInvalidPaymentId() throws Exception {
        mockMvc
                .perform(get("/payments/{paymentId}", -1))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_PAYMENT_ID))
                .andDo(print());
    }

    @Test
    public void testShowAllPayments() throws Exception {
        mockMvc
                .perform(get("/payments"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void testDeletePayment() throws Exception {
        mockMvc
                .perform(delete("/payments/{paymentId}", -6))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void negativeTestDeletePaymentInvalidId() throws Exception {
        mockMvc
                .perform(delete("/payments/{paymentId}", -1))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_PAYMENT_ID))
                .andDo(print());
    }

    @Test
    public void testShowIndividualPaymentsForPlan() throws Exception {
        mockMvc
                .perform(get("/payments/{planId}/{indivId}",-3,-2))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void negativeTestShowIndividualPaymentsForPlanInvalidPlanId() throws Exception {
        mockMvc
                .perform(get("/payments/{planId}/{indivId}",-1,-2))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_PAYMENT_PLAN_ID))
                .andDo(print());
    }

    @Test
    public void negativeTestShowIndividualPaymentsForPlanInvalidIndividualId() throws Exception {
        mockMvc
                .perform(get("/payments/{planId}/{indivId}",-3,-1))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_INDIVIDUAL_ID))
                .andDo(print());
    }
}