package com.final_project_rusi.payments.repositoryInterfaces;

import com.final_project_rusi.payments.dto.IndividualResponse;
import com.final_project_rusi.payments.models.Individual;

import java.util.List;
import java.util.Optional;

public interface IndividualInterface {
    public int insertIndividual(String name, String address);

    public int updateIndividualAddress(int indivId, String address);

    public Optional<IndividualResponse> getIndividual(int indivId);

    public List<Individual> getAllIndividuals();

    public int deleteIndividual(int indivId);
}
