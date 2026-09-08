package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.LoanPaymentMapper;
import com.smartloan.smart_loan_management_system.dto_request.LoanPaymentRequest;
import com.smartloan.smart_loan_management_system.dto_request.LoanPaymentResponse;
import com.smartloan.smart_loan_management_system.entity.LoanAccount;
import com.smartloan.smart_loan_management_system.entity.LoanPayment;
import com.smartloan.smart_loan_management_system.entity.RepaymentSchedule;
import com.smartloan.smart_loan_management_system.repository.LoanAccountRepository;
import com.smartloan.smart_loan_management_system.repository.LoanPaymentRepository;
import com.smartloan.smart_loan_management_system.repository.RepaymentScheduleRepository;
import com.smartloan.smart_loan_management_system.service.LoanPaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanPaymentServiceImpl implements LoanPaymentService {

    private final LoanPaymentRepository loanPaymentRepository;
    private final LoanAccountRepository loanAccountRepository;
    private final LoanPaymentMapper loanPaymentMapper;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Override
    @Transactional
    public LoanPaymentResponse createPayment(
            LoanPaymentRequest request) {

        // 1. Find Loan Account
        LoanAccount loanAccount =
                loanAccountRepository.findById(request.getLoanAccountId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Loan Account not found."
                                ));

        // 2. Check Loan Account status
        if (loanAccount.getStatus() == null ||
                !loanAccount.getStatus().equalsIgnoreCase("ACTIVE")) {

            throw new RuntimeException(
                    "Payment cannot be made for an inactive loan account."
            );
        }

        // 3. Validate payment amount
        if (request.getPaymentAmount() == null ||
                request.getPaymentAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Payment amount must be greater than zero."
            );
        }

        // 4. Validate payment date
        if (request.getPaymentDate() == null) {

            throw new RuntimeException(
                    "Payment date is required."
            );
        }

        // 5. Get repayment schedules
        List<RepaymentSchedule> schedules =
                repaymentScheduleRepository.findByLoanAccount_Id(
                        request.getLoanAccountId()
                );

        if (schedules.isEmpty()) {

            throw new RuntimeException(
                    "Repayment schedule not found."
            );
        }

        // Sort installments by installment number
        schedules.sort(
                Comparator.comparing(
                        RepaymentSchedule::getInstallmentNo
                )
        );

        // 6. Remaining payment amount
        BigDecimal remainingPayment =
                request.getPaymentAmount();

        // Total allocation for this payment
        BigDecimal totalPrincipalAmount = BigDecimal.ZERO;
        BigDecimal totalInterestAmount = BigDecimal.ZERO;
        BigDecimal totalPenaltyAmount = BigDecimal.ZERO;

        // 7. Process installments one by one
        for (RepaymentSchedule schedule : schedules) {

            // Skip fully paid installments
            if ("PAID".equalsIgnoreCase(schedule.getStatus())) {
                continue;
            }

            if (remainingPayment.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            // Existing paid amounts
            BigDecimal principalPaid =
                    schedule.getPrincipalPaid() != null
                            ? schedule.getPrincipalPaid()
                            : BigDecimal.ZERO;

            BigDecimal interestPaid =
                    schedule.getInterestPaid() != null
                            ? schedule.getInterestPaid()
                            : BigDecimal.ZERO;

            BigDecimal penaltyPaid =
                    schedule.getPenaltyPaid() != null
                            ? schedule.getPenaltyPaid()
                            : BigDecimal.ZERO;

            // Due amounts
            BigDecimal principalDue =
                    schedule.getPrincipalDue() != null
                            ? schedule.getPrincipalDue()
                            : BigDecimal.ZERO;

            BigDecimal interestDue =
                    schedule.getInterestDue() != null
                            ? schedule.getInterestDue()
                            : BigDecimal.ZERO;

            BigDecimal penaltyDue =
                    schedule.getPenaltyDue() != null
                            ? schedule.getPenaltyDue()
                            : BigDecimal.ZERO;

            // Remaining amounts for this installment
            BigDecimal remainingPenalty =
                    penaltyDue.subtract(penaltyPaid);

            BigDecimal remainingInterest =
                    interestDue.subtract(interestPaid);

            BigDecimal remainingPrincipal =
                    principalDue.subtract(principalPaid);

            // --------------------------------
            // Pay Penalty first
            // --------------------------------
            BigDecimal penaltyAmount =
                    remainingPayment.min(remainingPenalty);

            remainingPayment =
                    remainingPayment.subtract(penaltyAmount);

            // --------------------------------
            // Then pay Interest
            // --------------------------------
            BigDecimal interestAmount =
                    remainingPayment.min(remainingInterest);

            remainingPayment =
                    remainingPayment.subtract(interestAmount);

            // --------------------------------
            // Then pay Principal
            // --------------------------------
            BigDecimal principalAmount =
                    remainingPayment.min(remainingPrincipal);

            remainingPayment =
                    remainingPayment.subtract(principalAmount);

            // Update schedule paid amounts
            schedule.setPenaltyPaid(
                    penaltyPaid.add(penaltyAmount)
            );

            schedule.setInterestPaid(
                    interestPaid.add(interestAmount)
            );

            schedule.setPrincipalPaid(
                    principalPaid.add(principalAmount)
            );

            // Check installment completion
            boolean penaltyCompleted =
                    schedule.getPenaltyPaid()
                            .compareTo(penaltyDue) >= 0;

            boolean interestCompleted =
                    schedule.getInterestPaid()
                            .compareTo(interestDue) >= 0;

            boolean principalCompleted =
                    schedule.getPrincipalPaid()
                            .compareTo(principalDue) >= 0;

            if (penaltyCompleted &&
                    interestCompleted &&
                    principalCompleted) {

                schedule.setStatus("PAID");

            } else {

                schedule.setStatus("PARTIAL");
            }

            schedule.setUpdatedAt(new Date());

            // Save schedule
            repaymentScheduleRepository.save(schedule);

            // Accumulate payment allocation
            totalPenaltyAmount =
                    totalPenaltyAmount.add(penaltyAmount);

            totalInterestAmount =
                    totalInterestAmount.add(interestAmount);

            totalPrincipalAmount =
                    totalPrincipalAmount.add(principalAmount);
        }

        // 8. Check if payment is larger than the
        // entire remaining loan amount
        if (remainingPayment.compareTo(BigDecimal.ZERO) > 0) {

            throw new RuntimeException(
                    "Payment amount is greater than the remaining loan amount."
            );
        }

        // 9. Create Loan Payment record
        LoanPayment payment =
                loanPaymentMapper.toEntity(request);

        payment.setLoanAccount(loanAccount);

        payment.setPrincipalAmount(
                totalPrincipalAmount
        );

        payment.setInterestAmount(
                totalInterestAmount
        );

        payment.setPenaltyAmount(
                totalPenaltyAmount
        );

        payment.setCreatedAt(new Date());
        payment.setUpdatedAt(new Date());

        // 10. Save payment
        LoanPayment savedPayment =
                loanPaymentRepository.save(payment);

        // 11. Return response
        return loanPaymentMapper.toResponse(savedPayment);
    }
    @Override
    public List<LoanPaymentResponse> getAllPayments() {

        return loanPaymentRepository.findAll()
                .stream()
                .map(loanPaymentMapper::toResponse)
                .toList();
    }

    @Override
    public LoanPaymentResponse getPaymentById(
            Long id) {

        LoanPayment payment =
                loanPaymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found."
                                ));

        return loanPaymentMapper.toResponse(
                payment
        );
    }

    @Override
    public List<LoanPaymentResponse>
    getPaymentsByLoanAccount(
            Long loanAccountId) {

        if (!loanAccountRepository
                .existsById(loanAccountId)) {

            throw new RuntimeException(
                    "Loan Account not found."
            );
        }

        return loanPaymentRepository
                .findByLoanAccountId(loanAccountId)
                .stream()
                .map(loanPaymentMapper::toResponse)
                .toList();
    }
    @Override
    public LoanPaymentResponse updatePayment(
            Long id,
            LoanPaymentRequest request) {

        LoanPayment payment =
                loanPaymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found."
                                ));

        loanPaymentMapper.updatePayment(
                request,
                payment
        );

        LoanPayment updatedPayment =
                loanPaymentRepository.save(
                        payment
                );

        return loanPaymentMapper.toResponse(
                updatedPayment
        );
    }

    @Override
    public void deactivatePayment(
            Long id) {

        LoanPayment payment =
                loanPaymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found."
                                ));

        payment.setStatus("INACTIVE");
        payment.setUpdatedAt(new Date());

        loanPaymentRepository.save(payment);
    }
}