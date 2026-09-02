package com.smartloan.smart_loan_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "repayment_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_account_id", nullable = false)
    private LoanAccount loanAccount;

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

    private Date createdAt;

    private Date updatedAt;

}