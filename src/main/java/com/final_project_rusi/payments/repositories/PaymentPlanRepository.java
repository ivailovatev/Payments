package com.final_project_rusi.payments.repositories;

import com.final_project_rusi.payments.dto.PaymentPlanResponse;
import com.final_project_rusi.payments.dto.RemainingPlansResponse;
import com.final_project_rusi.payments.models.PaymentPlan;
import com.final_project_rusi.payments.repositoryInterfaces.PaymentPlanInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class PaymentPlanRepository implements PaymentPlanInterface {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PaymentPlanRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int insertPaymentPlan(int indivId, BigDecimal amount){

        String sql="                       " +
                "INSERT INTO PAYMENT_PLANS " +
                "(INDIVID,AMOUNT)          " +
                "VALUES (:indivId,:amount) ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("indivId",indivId)
                .addValue("amount",amount);

        return namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
    }

    @Override
    public int updatePaymentPlanAmount(int planId,BigDecimal amount){

        String sql="                  " +
                "UPDATE PAYMENT_PLANS " +
                "SET AMOUNT=:amount   " +
                "WHERE PLANID=:planId ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("planId",planId)
                .addValue("amount",amount);

        return namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
    }

    @Override
    public Optional<PaymentPlanResponse> getPaymentPlan(int planId){

        String sql="                   " +
                "SELECT INDIVID,AMOUNT " +
                "FROM PAYMENT_PLANS    " +
                "WHERE PLANID=:planId  ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("planId",planId);

        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            sql,
                            mapSqlParameterSource,
                            (rs,rowNum)->{
                                PaymentPlanResponse paymentPlanResponse=new PaymentPlanResponse();
                                paymentPlanResponse.setIndivId(rs.getInt("INDIVID"));
                                paymentPlanResponse.setAmount(rs.getBigDecimal("AMOUNT"));
                                return paymentPlanResponse;
                            }
                    ));
        }
        catch (EmptyResultDataAccessException exception){
            return Optional.empty();
        }
    }

    @Override
    public List<PaymentPlan> getAllPaymentPlans(){

        String sql="                          " +
                "SELECT PLANID,INDIVID,AMOUNT " +
                "FROM PAYMENT_PLANS           ";

        return namedParameterJdbcTemplate.query(
                sql,
                (rs,rowNum)->{
                    PaymentPlan paymentPlan=new PaymentPlan();
                    paymentPlan.setPlanId(rs.getInt("PLANID"));
                    paymentPlan.setIndivId(rs.getInt("INDIVID"));
                    paymentPlan.setAmount(rs.getBigDecimal("AMOUNT"));
                    return paymentPlan;
                });
    }

    @Override
    public int deletePaymentPlan(int planId){

        String sql="                       " +
                "DELETE FROM PAYMENT_PLANS " +
                "WHERE PLANID=:planId      ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("planId",planId);

        return namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
    }

    @Override
    public List<RemainingPlansResponse> getPlansRemainingToBePaid(){

        String sql="                                                            " +
                "SELECT                                                         " +
                "INDIVIDUALS.NAME,                                              " +
                "PAYMENT_PLANS.PLANID,                                          " +
                "   CASE WHEN PAYMENT_PLANS.AMOUNT-SUM(PAYMENTS.AMOUNT) IS NULL " +
                "   THEN PAYMENT_PLANS.AMOUNT                                   " +
                "   ELSE PAYMENT_PLANS.AMOUNT-SUM(PAYMENTS.AMOUNT)              " +
                "   END AS REMAINING_AMOUNTS                                    " +
                "FROM INDIVIDUALS                                               " +
                "JOIN PAYMENT_PLANS                                             " +
                "   ON INDIVIDUALS.INDIVID=PAYMENT_PLANS.INDIVID                " +
                "LEFT JOIN PAYMENTS                                             " +
                "   ON PAYMENT_PLANS.PLANID=PAYMENTS.PLANID                     " +
                "GROUP BY PAYMENT_PLANS.PLANID,                                 " +
                "PAYMENT_PLANS.AMOUNT,                                          " +
                "INDIVIDUALS.NAME                                               ";

        return namedParameterJdbcTemplate.query(
                sql,
                (rs,rowNum)->{
                    RemainingPlansResponse remainingPlansView=new RemainingPlansResponse();
                    remainingPlansView.setName(rs.getString("NAME"));
                    remainingPlansView.setPlanId(rs.getInt("PLANID"));
                    remainingPlansView.setAmount(rs.getBigDecimal("REMAINING_AMOUNTS"));
                    return remainingPlansView;
                });
    }
    @Override
    public Optional<BigDecimal> showPlanRemainingMoney(int planId, int indivId){

        String sql="                                                  " +
                "SELECT                                               " +
                "   CASE WHEN                                         " +
                "   PAYMENT_PLANS.AMOUNT-SUM(PAYMENTS.AMOUNT) IS NULL " +
                "   THEN PAYMENT_PLANS.AMOUNT                         " +
                "   ELSE PAYMENT_PLANS.AMOUNT-SUM(PAYMENTS.AMOUNT)    " +
                "   END                                               " +
                "FROM INDIVIDUALS                                     " +
                "JOIN PAYMENT_PLANS                                   " +
                "   ON INDIVIDUALS.INDIVID=PAYMENT_PLANS.INDIVID      " +
                "LEFT JOIN PAYMENTS                                   " +
                "           ON PAYMENT_PLANS.PLANID=PAYMENTS.PLANID   " +
                "GROUP BY PAYMENT_PLANS.PLANID,                       " +
                "PAYMENT_PLANS.AMOUNT,                                " +
                "PAYMENT_PLANS.INDIVID                                " +
                "HAVING PAYMENT_PLANS.PLANID=:planId                  " +
                "   AND PAYMENT_PLANS.INDIVID=:indivId                ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("planId",planId)
                .addValue("indivId",indivId);

        try {
            return Optional.ofNullable(namedParameterJdbcTemplate
                    .queryForObject(sql,mapSqlParameterSource,BigDecimal.class));
        }
        catch (EmptyResultDataAccessException exception){
            return Optional.empty();
        }
    }

}
