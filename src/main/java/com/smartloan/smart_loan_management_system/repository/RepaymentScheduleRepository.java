package com.smartloan.smart_loan_management_system.repository;

import com.smartloan.smart_loan_management_system.entity.RepaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepaymentScheduleRepository
        extends JpaRepository<RepaymentSchedule, Long> {

    List<RepaymentSchedule> findByLoanAccountIdOrderByInstallmentNoAsc(
            Long loanAccountId
    );
}