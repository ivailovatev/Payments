package com.final_project_rusi.payments.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project_rusi.payments.dto.PaymentPlanRequest;
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
class PaymentPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreatePaymentPlan() throws Exception {
        PaymentPlanRequest paymentPlanRequest = new PaymentPlanRequest();
        paymentPlanRequest.setIndivId(-2);
        paymentPlanRequest.setAmount(BigDecimal.valueOf(10000.99));
        mockMvc.perform(post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentPlanRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void negativeTestCreatePaymentPlanInvalidIndividualId() throws Exception {
        PaymentPlanRequest paymentPlanRequest = new PaymentPlanRequest();
        paymentPlanRequest.setIndivId(-1);
        paymentPlanRequest.setAmount(BigDecimal.valueOf(10000.99));
        mockMvc.perform(post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentPlanRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_INDIVIDUAL_ID))
                .andDo(print());
    }

    @Test
    public void negativeTestCreatePaymentPlanAmountLessThanZero() throws Exception {
        PaymentPlanRequest paymentPlanRequest = new PaymentPlanRequest();
        paymentPlanRequest.setIndivId(-2);
        paymentPlanRequest.setAmount(BigDecimal.valueOf(-100));
        mockMvc.perform(post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentPlanRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO))
                .andDo(print());
    }

    @Test
    public void wrongTestCreatePaymentPlanAmountEqualsToZero() throws Exception {
        PaymentPlanRequest paymentPlanRequest = new PaymentPlanRequest();
        paymentPlanRequest.setIndivId(-2);
        paymentPlanRequest.setAmount(BigDecimal.valueOf(0));
        mockMvc.perform(post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentPlanRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO))
                .andDo(print());
    }

    @Test
    public void testChangePaymentPlanAmount() throws Exception {

        mockMvc
                .perform(patch("/plans/{planId}/amount",-8)
                        .queryParam("amount", String.valueOf(BigDecimal.valueOf(5000))))
                .andExpect(status().isOk())
                .andDo(print());
        testShowPaymentPlanWithPlanId8();
    }


    @Test
    private void testShowPaymentPlanWithPlanId8() throws Exception {
        mockMvc
                .perform(get("/plans/{planId}", -8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.indivId").value(-3))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(5000)))
                .andDo(print());
    }

    @Test
    public void negativeTestChangePaymentPlanAmountInvalidPlanId() throws Exception {
        mockMvc
                .perform(patch("/plans/{planId}/amount",-1)
                        .queryParam("amount", String.valueOf(BigDecimal.valueOf(5000))))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_PAYMENT_PLAN_ID))
                .andDo(print());
    }

    @Test
    public void negativeTestChangePaymentPlanAmountLessThanZero() throws Exception {
        mockMvc
                .perform(patch("/plans/{planId}/amount",-8)
                        .queryParam("amount", String.valueOf(BigDecimal.valueOf(-5000))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO))
                .andDo(print());
    }

    @Test
    public void negativeTestChangePaymentPlanAmountEqualsToZero() throws Exception {
        mockMvc
                .perform(patch("/plans/{planId}/amount",-8)
                        .queryParam("amount", String.valueOf(BigDecimal.valueOf(0))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(PAYMENT_AMOUNT_IS_NEGATIVE_OR_ZERO))
                .andDo(print());
    }

    @Test
    public void testShowPaymentPlan() throws Exception {
        mockMvc
                .perform(get("/plans/{planId}", -3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.indivId").value(-2))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(9999.99)))
                .andDo(print());
    }

    @Test
    public void negativeTestShowPaymentPlanInvalidPlanId() throws Exception {
        mockMvc
                .perform(get("/plans/{planId}", -1))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_PAYMENT_PLAN_ID))
                .andDo(print());
    }

    @Test
    public void testShowAllPaymentPlans() throws Exception {
        mockMvc
                .perform(get("/plans"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void testDeletePaymentPlan() throws Exception {
        mockMvc
                .perform(delete("/plans/{planId}", -8))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void negativeTestDeletePaymentPlanInvalidPlanId() throws Exception {
        mockMvc
                .perform(delete("/plans/{planId}", -1))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_PAYMENT_PLAN_ID))
                .andDo(print());
    }

    @Test
    public void testShowPlansRemainingToBePaid() throws Exception {
        mockMvc
                .perform(get("/plans/unpaid"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}