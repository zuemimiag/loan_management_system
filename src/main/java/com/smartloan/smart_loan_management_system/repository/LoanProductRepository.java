package com.smartloan.smart_loan_management_system.repository;

import com.smartloan.smart_loan_management_system.entity.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanProductRepository extends JpaRepository<LoanProduct,Long> {
    Optional<LoanProduct>findByProductCode(String productCode);

    Optional<LoanProduct> findByProductName(String productName);

    boolean existsByProductCode(String productCode);

    boolean existsByProductName(String productName);
}
