package com.smartloan.smart_loan_management_system.repository;

import com.smartloan.smart_loan_management_system.entity.RepaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RepaymentScheduleRepository
        extends JpaRepository<RepaymentSchedule, Long> {

    List<RepaymentSchedule> findByLoanAccount_Id(Long loanAccountId);

    @Query("SELECT COALESCE(SUM(r.principalDue - COALESCE(r.principalPaid, 0)), 0) " +
            "FROM RepaymentSchedule r " +
            "WHERE r.loanAccount.id = :loanAccountId")
    BigDecimal getRemainingPrincipalByLoanAccountId(
            @Param("loanAccountId") Long loanAccountId
    );
}