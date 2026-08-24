package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

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

    private Date disbursementDate;
    private Date maturityDate;

    private Date createdAt;
    private Date updatedAt;
}
