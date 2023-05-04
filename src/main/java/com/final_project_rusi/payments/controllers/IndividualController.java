package com.final_project_rusi.payments.controllers;


import com.final_project_rusi.payments.dto.IndividualRequest;
import com.final_project_rusi.payments.dto.IndividualResponse;
import com.final_project_rusi.payments.models.Individual;
import com.final_project_rusi.payments.services.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/individuals")
public class IndividualController {

    private final IndividualService individualService;

    @Autowired
    public IndividualController(IndividualService individualService) {
        this.individualService = individualService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public int createIndividual(@Valid @RequestBody IndividualRequest individualView) {
        return individualService.createIndividual(individualView.getName(), individualView.getAddress());
    }

    @PatchMapping("/{indivId}/address")
    @ResponseStatus(HttpStatus.OK)
    public int changeIndividualAddress(@PathVariable int indivId, @RequestParam String address) {
        return individualService.changeIndividualAddress(indivId, address);
    }

    @GetMapping("/{indivId}")
    @ResponseStatus(HttpStatus.OK)
    public IndividualResponse showIndividual(@PathVariable int indivId) {
        return individualService.getIndividual(indivId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Individual> showAllIndividuals() {
        return individualService.getAllIndividuals();
    }

    @DeleteMapping("/{indivId}")
    @ResponseStatus(HttpStatus.OK)
    public int deleteIndividual(@PathVariable int indivId) {
        return individualService.removeIndividual(indivId);
    }

}
