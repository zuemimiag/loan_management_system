package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.PaymentMapper;
import com.smartloan.smart_loan_management_system.dto_request.PaymentRequest;
import com.smartloan.smart_loan_management_system.dto_request.PaymentResponse;
import com.smartloan.smart_loan_management_system.entity.LoanAccount;
import com.smartloan.smart_loan_management_system.entity.Payment;
import com.smartloan.smart_loan_management_system.entity.RepaymentSchedule;
import com.smartloan.smart_loan_management_system.repository.LoanAccountRepository;
import com.smartloan.smart_loan_management_system.repository.PaymentRepository;
import com.smartloan.smart_loan_management_system.repository.RepaymentScheduleRepository;
import com.smartloan.smart_loan_management_system.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final LoanAccountRepository loanAccountRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) {

        // 1. Validate payment amount
        if (request.getAmount() == null ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Payment amount must be greater than zero.");
        }

        // 2. Find Loan Account
        LoanAccount loanAccount =
                loanAccountRepository.findById(
                        request.getLoanAccountId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Loan account not found."));

        // 3. Find Repayment Schedule
        RepaymentSchedule schedule =
                repaymentScheduleRepository.findById(
                        request.getRepaymentScheduleId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Repayment schedule not found."));

        // 4. Make sure schedule belongs to loan account
        if (!schedule.getLoanAccount()
                .getId()
                .equals(loanAccount.getId())) {

            throw new RuntimeException(
                    "Repayment schedule does not belong to this loan account.");
        }

        // 5. Check duplicate reference number
        if (request.getReferenceNumber() != null &&
                !request.getReferenceNumber().trim().isEmpty() &&
                paymentRepository.existsByReferenceNumber(
                        request.getReferenceNumber())) {

            throw new RuntimeException(
                    "Payment reference number already exists.");
        }

        // 6. Get outstanding amounts
        BigDecimal principalOutstanding =
                getOutstanding(
                        schedule.getPrincipalDue(),
                        schedule.getPrincipalPaid()
                );

        BigDecimal interestOutstanding =
                getOutstanding(
                        schedule.getInterestDue(),
                        schedule.getInterestPaid()
                );

        BigDecimal penaltyOutstanding =
                getOutstanding(
                        schedule.getPenaltyDue(),
                        schedule.getPenaltyPaid()
                );

        BigDecimal remainingPayment = request.getAmount();

        // 7. Pay penalty first
        BigDecimal penaltyPayment =
                remainingPayment.min(penaltyOutstanding);

        remainingPayment =
                remainingPayment.subtract(penaltyPayment);

        // 8. Pay interest second
        BigDecimal interestPayment =
                remainingPayment.min(interestOutstanding);

        remainingPayment =
                remainingPayment.subtract(interestPayment);

        // 9. Pay principal last
        BigDecimal principalPayment =
                remainingPayment.min(principalOutstanding);

        remainingPayment =
                remainingPayment.subtract(principalPayment);

        // 10. Prevent overpayment
        if (remainingPayment.compareTo(BigDecimal.ZERO) > 0) {

            throw new RuntimeException(
                    "Payment amount exceeds outstanding amount.");
        }

        // 11. Update schedule paid amounts

        BigDecimal currentPrincipalPaid =
                zeroIfNull(schedule.getPrincipalPaid());

        BigDecimal currentInterestPaid =
                zeroIfNull(schedule.getInterestPaid());

        BigDecimal currentPenaltyPaid =
                zeroIfNull(schedule.getPenaltyPaid());

        schedule.setPrincipalPaid(
                currentPrincipalPaid.add(principalPayment)
        );

        schedule.setInterestPaid(
                currentInterestPaid.add(interestPayment)
        );

        schedule.setPenaltyPaid(
                currentPenaltyPaid.add(penaltyPayment)
        );

        // 12. Update schedule status

        BigDecimal newPrincipalOutstanding =
                principalOutstanding.subtract(principalPayment);

        BigDecimal newInterestOutstanding =
                interestOutstanding.subtract(interestPayment);

        BigDecimal newPenaltyOutstanding =
                penaltyOutstanding.subtract(penaltyPayment);

        if (newPrincipalOutstanding.compareTo(BigDecimal.ZERO) == 0 &&
                newInterestOutstanding.compareTo(BigDecimal.ZERO) == 0 &&
                newPenaltyOutstanding.compareTo(BigDecimal.ZERO) == 0) {

            schedule.setStatus("PAID");

        } else {

            schedule.setStatus("PARTIAL");
        }

        schedule.setUpdatedAt(
                Date.valueOf(LocalDate.now())
        );

        repaymentScheduleRepository.save(schedule);

        // 13. Create Payment
        Payment payment = new Payment();

        payment.setLoanAccount(loanAccount);
        payment.setRepaymentSchedule(schedule);

        payment.setAmount(request.getAmount());

        payment.setPenaltyAmount(penaltyPayment);
        payment.setInterestAmount(interestPayment);
        payment.setPrincipalAmount(principalPayment);

        payment.setPaymentMethod(
                request.getPaymentMethod()
        );

        payment.setReferenceNumber(
                request.getReferenceNumber()
        );

        payment.setPaymentDate(
                Date.valueOf(LocalDate.now())
        );

        payment.setStatus("SUCCESS");

        payment.setCreatedAt(
                Date.valueOf(LocalDate.now())
        );

        payment.setUpdatedAt(
                Date.valueOf(LocalDate.now())
        );

        Payment savedPayment =
                paymentRepository.save(payment);

        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found."));

        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponse> getPaymentsByLoanAccount(
            Long loanAccountId) {

        if (!loanAccountRepository.existsById(loanAccountId)) {

            throw new RuntimeException(
                    "Loan account not found.");
        }

        return paymentRepository
                .findByLoanAccountId(loanAccountId)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    private BigDecimal getOutstanding(
            BigDecimal due,
            BigDecimal paid) {

        BigDecimal dueAmount = zeroIfNull(due);
        BigDecimal paidAmount = zeroIfNull(paid);

        BigDecimal outstanding =
                dueAmount.subtract(paidAmount);

        if (outstanding.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        return outstanding;
    }

    private BigDecimal zeroIfNull(BigDecimal value) {

        return value == null
                ? BigDecimal.ZERO
                : value;
    }
}