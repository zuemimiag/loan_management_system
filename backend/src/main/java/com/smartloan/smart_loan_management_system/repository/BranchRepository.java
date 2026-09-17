package com.smartloan.smart_loan_management_system.repository;

import com.smartloan.smart_loan_management_system.dto_request.BranchResponse;
import com.smartloan.smart_loan_management_system.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Long> {

    Optional<Branch> findByBranchName(String branchName);

    Optional<Branch> findByBranchCode(String branchCode);

    boolean existsByBranchCode(String branchCode);

    boolean existsByBranchName(String branchName);
}
