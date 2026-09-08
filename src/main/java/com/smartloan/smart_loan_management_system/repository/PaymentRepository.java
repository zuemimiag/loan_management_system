package com.smartloan.smart_loan_management_system.repository;

import com.smartloan.smart_loan_management_system.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByLoanAccountId(Long loanAccountId);

    List<Payment> findByRepaymentScheduleId(Long repaymentScheduleId);

    boolean existsByReferenceNumber(String referenceNumber);
}