package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanAccountRequest {

    private Long customerId;
    private Long loanProductId;
    private Long branchId;
    private Long loanOfficerId;

    private BigDecimal loanAmount;
    private Integer term;
    private String status;
}
