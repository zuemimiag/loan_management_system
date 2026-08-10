package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class LoanProductResponse {
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
    private Date updatedAt;
}
