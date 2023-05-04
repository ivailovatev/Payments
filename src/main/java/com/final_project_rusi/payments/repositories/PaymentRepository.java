package com.final_project_rusi.payments.repositories;

import com.final_project_rusi.payments.dto.PaymentHistoryResponse;
import com.final_project_rusi.payments.dto.PaymentResponse;
import com.final_project_rusi.payments.models.Payment;
import com.final_project_rusi.payments.repositoryInterfaces.PaymentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class PaymentRepository implements PaymentInterface {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PaymentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int insertPayment(int planId, BigDecimal amount){

        String sql="                            " +
                "INSERT INTO PAYMENTS           " +
                "(PLANID,AMOUNT,PMTDATE)        " +
                "VALUES (:planId,:amount,SYSDATE) ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("planId",planId)
                .addValue("amount",amount);

        return namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);

    }

    @Override
    public int updatePaymentAmount(int paymentId,BigDecimal amount){

        String sql="                        " +
                "UPDATE PAYMENTS            " +
                "SET AMOUNT=:amount         " +
                "WHERE PAYMENTID=:paymentId ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("paymentId",paymentId)
                .addValue("amount",amount);

        return namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
    }

    @Override
    public Optional<PaymentResponse> getPayment(int paymentId){

        String sql="                           " +
                "SELECT PLANID,AMOUNT,PMTDATE  " +
                "FROM PAYMENTS                 " +
                "WHERE PAYMENTID=:paymentId    ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("paymentId",paymentId);

        try{
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            sql,
                            mapSqlParameterSource,
                            (rs,rowNum)->{
                                PaymentResponse paymentView=new PaymentResponse();
                                paymentView.setPlanId(rs.getInt("PLANID"));
                                paymentView.setAmount(rs.getBigDecimal("AMOUNT"));
                                paymentView.setDate(rs.getDate("PMTDATE").toLocalDate());
                                return paymentView;
                            }
                    ));
        }
        catch (EmptyResultDataAccessException exception){
            return Optional.empty();
        }
    }

    @Override
    public List<Payment> getAllPayments(){

        String sql="                                    " +
                "SELECT PAYMENTID,PLANID,AMOUNT,PMTDATE " +
                "FROM PAYMENTS                          ";

        return namedParameterJdbcTemplate.query(
                sql,
                (rs,rowNum)->{
                    Payment payment=new Payment();
                    payment.setPaymentId(rs.getInt("PAYMENTID"));
                    payment.setPlanId(rs.getInt("PLANID"));
                    payment.setAmount(rs.getBigDecimal("AMOUNT"));
                    payment.setLocalDate(rs.getDate("PMTDATE").toLocalDate());
                    return payment;
                });
    }

    @Override
    public int deletePayment(int paymentId){

        String sql="                        " +
                "DELETE FROM PAYMENTS       " +
                "WHERE PAYMENTID=:paymentId ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("paymentId",paymentId);

        return namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);
    }

    @Override
    public List<PaymentHistoryResponse> getPaymentsPerPlanForIndividual(int planId, int indivId){

        String sql="" +
                "SELECT INDIVIDUALS.NAME,                        " +
                "PAYMENT_PLANS.PLANID,                           " +
                "   CASE WHEN PAYMENTS.AMOUNT IS NULL            " +
                "   THEN 0                                       " +
                "   ELSE PAYMENTS.AMOUNT                         " +
                "   END AS PAYMENT_AMOUNT,                       " +
                "PAYMENT_PLANS.AMOUNT AS PAYMENT_PLAN_AMOUNT     " +
                "FROM INDIVIDUALS                                " +
                "JOIN PAYMENT_PLANS                              " +
                "   ON INDIVIDUALS.INDIVID=PAYMENT_PLANS.INDIVID " +
                "LEFT JOIN PAYMENTS                              " +
                "   ON PAYMENT_PLANS.PLANID=PAYMENTS.PLANID      " +
                "WHERE PAYMENT_PLANS.PLANID=:planId              " +
                "   AND PAYMENT_PLANS.INDIVID=:indivId           ";

        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("planId",planId)
                .addValue("indivId",indivId);

        return namedParameterJdbcTemplate.query(
                sql,
                mapSqlParameterSource,
                (rs,rowNum)->{
                    PaymentHistoryResponse paymentHistoryView=new PaymentHistoryResponse();
                    paymentHistoryView.setName(rs.getString("NAME"));
                    paymentHistoryView.setPlanId(rs.getInt("PLANID"));
                    paymentHistoryView.setPaymentAmount(rs.getBigDecimal("PAYMENT_AMOUNT"));
                    paymentHistoryView.setPlanAmount(rs.getBigDecimal("PAYMENT_PLAN_AMOUNT"));
                    return paymentHistoryView;
                });
    }

}
