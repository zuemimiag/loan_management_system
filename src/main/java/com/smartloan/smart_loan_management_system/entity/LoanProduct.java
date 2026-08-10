package com.smartloan.smart_loan_management_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productCode;
    private String productName;
    private BigDecimal interestRate;
    private BigDecimal minimumAmount;
    private BigDecimal maximumAmount;
    private Integer defaultTerm;
    private BigDecimal penaltyRate;
    private String description;
    private String status;
    private Date createdAt;
    private Date updatedAt;

}
