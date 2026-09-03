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
        return List.of();
    }

    @Override
    public List<RepaymentScheduleResponse> generateSchedule(
            Long loanAccountId) {

        LoanAccount loanAccount = loanAccountRepository.findById(loanAccountId)
                .orElseThrow(() ->
                        new RuntimeException("Loan account not found."));

        // Prevent duplicate schedule generation
        if (!repaymentScheduleRepository
                .findByLoanAccountId(loanAccountId)
                .isEmpty()) {

            throw new RuntimeException(
                    "Repayment schedule already exists for this loan account.");
        }

        if (loanAccount.getTerm() == null || loanAccount.getTerm() <= 0) {
            throw new RuntimeException("Invalid loan term.");
        }

        if (loanAccount.getLoanAmount() == null ||
                loanAccount.getLoanAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException("Invalid loan amount.");
        }

        if (loanAccount.getInterestRate() == null) {
            throw new RuntimeException("Interest rate is required.");
        }

        String repaymentMethod =
                loanAccount.getLoanProduct().getRepaymentMethod();

        if (repaymentMethod == null) {
            throw new RuntimeException(
                    "Repayment method is not configured.");
        }

        if (!repaymentMethod.equalsIgnoreCase("FLAT")) {
            throw new RuntimeException(
                    "Currently only FLAT repayment is supported.");
        }

        return generateFlatSchedule(loanAccount);
    }

    private List<RepaymentScheduleResponse> generateFlatSchedule(
            LoanAccount loanAccount) {

        List<RepaymentSchedule> schedules = new ArrayList<>();

        int term = loanAccount.getTerm();

        BigDecimal loanAmount = loanAccount.getLoanAmount();

        BigDecimal monthlyPrincipal =
                loanAmount.divide(
                        BigDecimal.valueOf(term),
                        2,
                        RoundingMode.HALF_UP
                );

        /*
         * Annual interest rate:
         *
         * 12% = 0.12
         *
         * Monthly flat interest:
         *
         * Loan Amount × Annual Rate ÷ 12
         */
        BigDecimal monthlyInterest =
                loanAmount
                        .multiply(loanAccount.getInterestRate())
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

        for (int i = 1; i <= term; i++) {

            RepaymentSchedule schedule =
                    new RepaymentSchedule();

            schedule.setLoanAccount(loanAccount);

            schedule.setInstallmentNo(i);

            LocalDate dueDate =
                    LocalDate.now().plusMonths(i);

            schedule.setDueDate(
                    Date.valueOf(dueDate)
            );

            BigDecimal principalDue = monthlyPrincipal;

            // Last installment adjusts rounding difference
            if (i == term) {

                BigDecimal previousPrincipal =
                        monthlyPrincipal
                                .multiply(
                                        BigDecimal.valueOf(term - 1)
                                );

                principalDue =
                        loanAmount.subtract(previousPrincipal)
                                .setScale(
                                        2,
                                        RoundingMode.HALF_UP
                                );
            }

            BigDecimal totalDue =
                    principalDue.add(monthlyInterest);

            schedule.setPrincipalDue(principalDue);
            schedule.setInterestDue(monthlyInterest);
            schedule.setPenaltyDue(BigDecimal.ZERO);
            schedule.setTotalDue(totalDue);

            schedule.setPrincipalPaid(BigDecimal.ZERO);
            schedule.setInterestPaid(BigDecimal.ZERO);
            schedule.setPenaltyPaid(BigDecimal.ZERO);

            schedule.setStatus("PENDING");

            Date today = Date.valueOf(LocalDate.now());

            schedule.setCreatedAt(today);
            schedule.setUpdatedAt(today);

            schedules.add(schedule);
        }

        List<RepaymentSchedule> savedSchedules =
                repaymentScheduleRepository.saveAll(schedules);

        return savedSchedules.stream()
                .map(repaymentScheduleMapper::toResponse)
                .toList();
    }

    @Override
    public List<RepaymentScheduleResponse> getScheduleByLoanAccount(
            Long loanAccountId) {

        if (!loanAccountRepository.existsById(loanAccountId)) {
            throw new RuntimeException(
                    "Loan account not found.");
        }

        return repaymentScheduleRepository
                .findByLoanAccountId(loanAccountId)
                .stream()
                .map(repaymentScheduleMapper::toResponse)
                .toList();
    }
}