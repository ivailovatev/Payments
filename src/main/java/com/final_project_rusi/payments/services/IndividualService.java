package com.final_project_rusi.payments.services;

import com.final_project_rusi.payments.dto.IndividualResponse;
import com.final_project_rusi.payments.models.Individual;
import com.final_project_rusi.payments.repositories.IndividualRepository;
import com.final_project_rusi.payments.validations.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndividualService {
    private final IndividualRepository individualRepository;
    private final Validation validation;

    @Autowired
    public IndividualService(IndividualRepository individualRepository, Validation validation) {
        this.individualRepository = individualRepository;
        this.validation = validation;
    }

    public int createIndividual(String name, String address) {
        return individualRepository.insertIndividual(name, address);
    }

    public int changeIndividualAddress(int indivId, String address) {
        validation.getIndividualOrThrow(indivId);
        return individualRepository.updateIndividualAddress(indivId, address);
    }

    public IndividualResponse getIndividual(int indivId) {
        return validation.getIndividualOrThrow(indivId);
    }

    public List<Individual> getAllIndividuals() {
        return individualRepository.getAllIndividuals();
    }

    public int removeIndividual(int indivId) {
        validation.getIndividualOrThrow(indivId);
        return individualRepository.deleteIndividual(indivId);
    }


}
