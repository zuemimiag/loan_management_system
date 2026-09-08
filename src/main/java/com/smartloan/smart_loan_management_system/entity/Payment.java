package com.smartloan.smart_loan_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_account_id", nullable = false)
    private LoanAccount loanAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repayment_schedule_id", nullable = false)
    private RepaymentSchedule repaymentSchedule;

    private BigDecimal amount;

    private BigDecimal penaltyAmount;

    private BigDecimal interestAmount;

    private BigDecimal principalAmount;

    private String paymentMethod;

    @Column(unique = true)
    private String referenceNumber;

    private Date paymentDate;

    private String status;

    private Date createdAt;

    private Date updatedAt;
}