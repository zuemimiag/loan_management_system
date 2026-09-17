package com.smartloan.smart_loan_management_system.repository;

import com.smartloan.smart_loan_management_system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByNrc(String nrc);
}
