package com.final_project_rusi.payments.repositories;


import com.final_project_rusi.payments.dto.IndividualResponse;
import com.final_project_rusi.payments.models.Individual;
import com.final_project_rusi.payments.repositoryInterfaces.IndividualInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IndividualRepository implements IndividualInterface {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public IndividualRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int insertIndividual(String name, String address) {

        String sql = "                   " +
                "INSERT INTO INDIVIDUALS " +
                "(NAME,ADDRESS)          " +
                "VALUES (:name,:address) ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("address", address);

        return namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

    }

    @Override
    public int updateIndividualAddress(int indivId, String address) {

        String sql = "                  " +
                "UPDATE INDIVIDUALS     " +
                "SET ADDRESS=:address   " +
                "WHERE INDIVID=:indivId ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("indivId", indivId)
                .addValue("address", address);

        return namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

    }

    @Override
    public Optional<IndividualResponse> getIndividual(int indivId) {

        String sql = "                  " +
                "SELECT NAME, ADDRESS   " +
                "FROM INDIVIDUALS       " +
                "WHERE INDIVID=:indivId ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("indivId", indivId);

        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            sql,
                            mapSqlParameterSource,
                            (rs, rowNum) -> {
                                IndividualResponse individualResponse = new IndividualResponse();
                                individualResponse.setName(rs.getString("NAME"));
                                individualResponse.setAddress(rs.getString("ADDRESS"));
                                return individualResponse;
                            }
                    ));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Individual> getAllIndividuals() {

        String sql = "                         " +
                "SELECT INDIVID,NAME, ADDRESS  " +
                "FROM INDIVIDUALS              ";

        return namedParameterJdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Individual individual = new Individual();
                    individual.setIndivId(rs.getInt("INDIVID"));
                    individual.setName(rs.getString("NAME"));
                    individual.setAddress(rs.getString("ADDRESS"));
                    return individual;
                });
    }

    @Override
    public int deleteIndividual(int indivId) {

        String sql = "                   " +
                "DELETE FROM INDIVIDUALS " +
                "WHERE INDIVID=:indivId  ";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("indivId", indivId);

        return namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }
}
