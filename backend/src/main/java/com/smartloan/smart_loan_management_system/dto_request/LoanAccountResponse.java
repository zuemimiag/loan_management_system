package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Data
public class LoanAccountResponse {
    private Long id;
    private String accountNumber;

    private Long customerId;
    private String customerName;

    private Long loanProductId;
    private String loanProductName;

    private Long branchId;
    private String branchName;

    private Long loanOfficerId;
    private String loanOfficerName;

    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private Integer term;
    private BigDecimal penaltyRate;

    private String status;

    private LocalDate disbursementDate;
    private LocalDate maturityDate;

    private LocalDate createdAt;
    private LocalDate updatedAt;
}
