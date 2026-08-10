package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LoanProductRequest {
    private String productCode;
    private String productName;
    private BigDecimal interestRate;
    private BigDecimal minimumAmount;
    private BigDecimal maximumAmount;
    private Integer defaultTerm;
    private BigDecimal penaltyRate;
    private String description;
    private String status;
}
