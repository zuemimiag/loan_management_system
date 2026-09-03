package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.RepaymentScheduleMapper;
import com.smartloan.smart_loan_management_system.dto_request.RepaymentScheduleResponse;
import com.smartloan.smart_loan_management_system.entity.LoanAccount;
import com.smartloan.smart_loan_management_system.entity.RepaymentSchedule;
import com.smartloan.smart_loan_management_system.repository.LoanAccountRepository;
import com.smartloan.smart_loan_management_system.repository.RepaymentScheduleRepository;
import com.smartloan.smart_loan_management_system.service.RepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleServiceImpl
        implements RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final LoanAccountRepository loanAccountRepository;
    private final RepaymentScheduleMapper repaymentScheduleMapper;

    @Override
    public List<RepaymentSchedule> findByLoanAccountId(Long loanAccountId) {

        if (!loanAccountRepository.existsById(loanAccountId)) {
            throw new RuntimeException("Loan account not found.");
        }

        return repaymentScheduleRepository
                .findByLoanAccount_Id(loanAccountId);
    }

    @Override
    public List<RepaymentScheduleResponse> generateSchedule(
            Long loanAccountId) {

        // 1. Find Loan Account
        LoanAccount loanAccount = loanAccountRepository
                .findById(loanAccountId)
                .orElseThrow(() ->
                        new RuntimeException("Loan account not found."));

        // 2. Prevent duplicate schedule generation
        if (!repaymentScheduleRepository
                .findByLoanAccount_Id(loanAccountId)
                .isEmpty()) {

            throw new RuntimeException(
                    "Repayment schedule already exists for this loan account."
            );
        }

        // 3. Validate loan amount
        if (loanAccount.getLoanAmount() == null ||
                loanAccount.getLoanAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Invalid loan amount."
            );
        }

        // 4. Validate term
        if (loanAccount.getTerm() == null ||
                loanAccount.getTerm() <= 0) {

            throw new RuntimeException(
                    "Invalid loan term."
            );
        }

        // 5. Validate interest rate
        if (loanAccount.getInterestRate() == null ||
                loanAccount.getInterestRate()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new RuntimeException(
                    "Invalid interest rate."
            );
        }

        // 6. Check repayment method
        if (loanAccount.getLoanProduct() == null) {
            throw new RuntimeException(
                    "Loan product not found."
            );
        }

        String repaymentMethod =
                loanAccount.getLoanProduct()
                        .getRepaymentMethod();

        if (repaymentMethod == null ||
                repaymentMethod.trim().isEmpty()) {

            throw new RuntimeException(
                    "Repayment method is not configured."
            );
        }

        // Currently support FLAT only
        if (!repaymentMethod.equalsIgnoreCase("FLAT")) {

            throw new RuntimeException(
                    "Currently only FLAT repayment is supported."
            );
        }

        return generateFlatSchedule(loanAccount);
    }

    private List<RepaymentScheduleResponse> generateFlatSchedule(
            LoanAccount loanAccount) {

        List<RepaymentSchedule> schedules =
                new ArrayList<>();

        int term = loanAccount.getTerm();

        BigDecimal loanAmount =
                loanAccount.getLoanAmount();

        BigDecimal interestRate =
                loanAccount.getInterestRate();

        // ------------------------------------------------
        // Monthly Principal
        // ------------------------------------------------

        BigDecimal monthlyPrincipal =
                loanAmount.divide(
                        BigDecimal.valueOf(term),
                        2,
                        RoundingMode.HALF_UP
                );

        // ------------------------------------------------
        // Monthly Flat Interest
        //
        // Example:
        //
        // Loan Amount = 500,000
        // Interest = 12%
        //
        // Annual Interest:
        // 500,000 × 12 / 100
        // = 60,000
        //
        // Monthly Interest:
        // 60,000 / 12
        // = 5,000
        // ------------------------------------------------

        BigDecimal monthlyInterest =
                loanAmount
                        .multiply(interestRate)
                        .divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP
                        )
                        .divide(
                                BigDecimal.valueOf(12),
                                2,
                                RoundingMode.HALF_UP
                        );

        // ------------------------------------------------
        // Generate installments
        // ------------------------------------------------

        for (int i = 1; i <= term; i++) {

            RepaymentSchedule schedule =
                    new RepaymentSchedule();

            schedule.setLoanAccount(loanAccount);

            schedule.setInstallmentNo(i);

            // Due date
            LocalDate dueDate =
                    LocalDate.now().plusMonths(i);

            schedule.setDueDate(
                    Date.valueOf(dueDate)
            );

            // ------------------------------------------------
            // Principal
            // ------------------------------------------------

            BigDecimal principalDue =
                    monthlyPrincipal;

            // Adjust final installment for rounding
            if (i == term) {

                BigDecimal previousPrincipal =
                        monthlyPrincipal.multiply(
                                BigDecimal.valueOf(term - 1)
                        );

                principalDue =
                        loanAmount
                                .subtract(previousPrincipal)
                                .setScale(
                                        2,
                                        RoundingMode.HALF_UP
                                );
            }

            // ------------------------------------------------
            // Total Due
            // ------------------------------------------------

            BigDecimal totalDue =
                    principalDue.add(monthlyInterest);

            // ------------------------------------------------
            // Set Schedule Values
            // ------------------------------------------------

            schedule.setPrincipalDue(
                    principalDue
            );

            schedule.setInterestDue(
                    monthlyInterest
            );

            schedule.setPenaltyDue(
                    BigDecimal.ZERO
            );

            schedule.setTotalDue(
                    totalDue
            );

            // Nothing paid yet
            schedule.setPrincipalPaid(
                    BigDecimal.ZERO
            );

            schedule.setInterestPaid(
                    BigDecimal.ZERO
            );

            schedule.setPenaltyPaid(
                    BigDecimal.ZERO
            );

            schedule.setStatus(
                    "PENDING"
            );

            Date today =
                    Date.valueOf(LocalDate.now());

            schedule.setCreatedAt(today);
            schedule.setUpdatedAt(today);

            schedules.add(schedule);
        }

        // ------------------------------------------------
        // Save all schedules
        // ------------------------------------------------

        List<RepaymentSchedule> savedSchedules =
                repaymentScheduleRepository
                        .saveAll(schedules);

        // ------------------------------------------------
        // Entity → Response
        // ------------------------------------------------

        return savedSchedules
                .stream()
                .map(repaymentScheduleMapper::toResponse)
                .toList();
    }

    @Override
    public List<RepaymentScheduleResponse> getScheduleByLoanAccount(
            Long loanAccountId) {

        // Check loan account
        if (!loanAccountRepository.existsById(loanAccountId)) {

            throw new RuntimeException(
                    "Loan account not found."
            );
        }

        return repaymentScheduleRepository
                .findByLoanAccount_Id(loanAccountId)
                .stream()
                .map(repaymentScheduleMapper::toResponse)
                .toList();
    }
}