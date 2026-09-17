package com.smartloan.smart_loan_management_system.dto_request;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class RepaymentScheduleResponse {

    private Long id;

    private Long loanAccountId;
    private String accountNumber;

    private Integer installmentNo;
    private Date dueDate;

    private BigDecimal principalDue;
    private BigDecimal interestDue;
    private BigDecimal penaltyDue;
    private BigDecimal totalDue;

    private BigDecimal principalPaid;
    private BigDecimal interestPaid;
    private BigDecimal penaltyPaid;

    private String status;

    private java.util.Date createdAt;
    private java.util.Date updatedAt;
}