package com.smartloan.smart_loan_management_system.repository;

import com.smartloan.smart_loan_management_system.entity.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount,Long> {

    boolean existsByAccountNumber(String accountNumber);
}
