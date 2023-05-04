package com.final_project_rusi.payments.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project_rusi.payments.dto.IndividualRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.final_project_rusi.payments.messages.ExceptionMessages.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = {"/QueriesForTest.sql"})
class IndividualControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateIndividual() throws Exception {
        IndividualRequest individualRequest = new IndividualRequest();
        individualRequest.setName("Georgi Georgiev");
        individualRequest.setAddress("Plovdiv");
        mockMvc.perform(post("/individuals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(individualRequest)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void negativeTestCreateIndividualNameLessThanThreeSymbols() throws Exception {
        IndividualRequest individualRequest = new IndividualRequest();
        individualRequest.setName("aa");
        individualRequest.setAddress("Plovdiv");
        mockMvc
                .perform(post("/individuals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(individualRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NAME_SHOULD_BE_MIN_3_SYMBOLS))
                .andDo(print());
    }

    @Test
    public void negativeTestCreateIndividualNameEmpty() throws Exception {
        IndividualRequest individualRequest = new IndividualRequest();
        individualRequest.setName("");
        individualRequest.setAddress("Plovdiv");
        mockMvc
                .perform(post("/individuals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(individualRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NAME_SHOULD_BE_MIN_3_SYMBOLS))
                .andDo(print());
    }

    @Test
    public void negativeTestCreateIndividualNameGreaterThanThirtySymbols() throws Exception {
        IndividualRequest individualRequest = new IndividualRequest();
        individualRequest.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        individualRequest.setAddress("Plovdiv");
        mockMvc
                .perform(post("/individuals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(individualRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(NAME_SHOULD_BE_MAX_30_SYMBOLS))
                .andDo(print());
    }

    @Test
    public void testChangeIndividualAddress() throws Exception {
        mockMvc
                .perform(patch("/individuals/{indivId}/address",-2)
                        .queryParam("address", "Varna"))
                .andExpect(status().isOk())
                .andDo(print());
        testShowIndividualWithId2();

    }

    @Test
    private void testShowIndividualWithId2() throws Exception {
        mockMvc
                .perform(get("/individuals/{indivId}", -2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Martin Vulkov"))
                .andExpect(jsonPath("$.address").value("Varna"))
                .andDo(print());
    }

    @Test
    public void negativeTestChangeIndividualAddressInvalidId() throws Exception {
        mockMvc
                .perform(patch("/individuals/{indivId}/address",-1)
                        .queryParam("address", "Varna"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_INDIVIDUAL_ID))
                .andDo(print());
    }

    @Test
    public void testShowIndividual() throws Exception {
        mockMvc
                .perform(get("/individuals/{indivId}", -2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Martin Vulkov"))
                .andExpect(jsonPath("$.address").value("Burgas"))
                .andDo(print());
    }

    @Test
    public void negativeTestShowIndividualInvalidId() throws Exception {
        mockMvc
                .perform(get("/individuals/{indivId}", -1))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_INDIVIDUAL_ID))
                .andDo(print());
    }

    @Test
    public void testShowAllIndividuals() throws Exception {
        mockMvc
                .perform(get("/individuals"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void testDeleteIndividual() throws Exception {
        mockMvc
                .perform(delete("/individuals/{indivId}", -5))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void negativeTestDeleteIndividualInvalidId() throws Exception {
        mockMvc
                .perform(delete("/individuals/{indivId}", -1))
                .andExpect(status().isNotFound())
                .andExpect(content().string(INVALID_INDIVIDUAL_ID))
                .andDo(print());
    }

}