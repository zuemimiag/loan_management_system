package com.smartloan.smart_loan_management_system.repository;

import com.smartloan.smart_loan_management_system.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {

    List<LoanPayment> findByLoanAccountId(Long loanAccountId);
}