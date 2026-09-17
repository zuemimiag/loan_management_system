package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.LoanAccountMapper;
import com.smartloan.smart_loan_management_system.dto_request.LoanAccountRequest;
import com.smartloan.smart_loan_management_system.dto_request.LoanAccountResponse;
import com.smartloan.smart_loan_management_system.entity.Branch;
import com.smartloan.smart_loan_management_system.entity.Customer;
import com.smartloan.smart_loan_management_system.entity.LoanAccount;
import com.smartloan.smart_loan_management_system.entity.LoanProduct;
import com.smartloan.smart_loan_management_system.entity.User;
import com.smartloan.smart_loan_management_system.repository.BranchRepository;
import com.smartloan.smart_loan_management_system.repository.CustomerRepository;
import com.smartloan.smart_loan_management_system.repository.LoanAccountRepository;
import com.smartloan.smart_loan_management_system.repository.LoanProductRepository;
import com.smartloan.smart_loan_management_system.repository.UserRepository;
import com.smartloan.smart_loan_management_system.service.LoanAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanAccountServiceImpl implements LoanAccountService {

    private final LoanAccountRepository loanAccountRepository;
    private final CustomerRepository customerRepository;
    private final LoanProductRepository loanProductRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final LoanAccountMapper loanAccountMapper;

    @Override
    public LoanAccountResponse createLoanAccount(LoanAccountRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found."));

        LoanProduct loanProduct = loanProductRepository.findById(request.getLoanProductId())
                .orElseThrow(() -> new RuntimeException("Loan product not found."));

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found."));

        User loanOfficer = userRepository.findById(request.getLoanOfficerId())
                .orElseThrow(() -> new RuntimeException("Loan officer not found."));

        if (loanOfficer.getRole() == null ||
                !"LO".equalsIgnoreCase(loanOfficer.getRole().getRoleName())) {
            throw new RuntimeException("Selected user is not a Loan Officer.");
        }

        validateLoanAmount(request.getLoanAmount(), loanProduct);

        validateTerm(request.getTerm());

        LoanAccount loanAccount = loanAccountMapper.toEntity(request);

        loanAccount.setCustomer(customer);
        loanAccount.setLoanProduct(loanProduct);
        loanAccount.setBranch(branch);
        loanAccount.setLoanOfficer(loanOfficer);
        loanAccount.setInterestRate(loanProduct.getInterestRate());
        loanAccount.setPenaltyRate(loanProduct.getPenaltyRate());
        loanAccount.setAccountNumber(generateAccountNumber());
        loanAccount.setStatus("ACTIVE");
        loanAccount.setCreatedAt(LocalDate.now());

        LoanAccount savedLoanAccount = loanAccountRepository.save(loanAccount);

        return loanAccountMapper.toResponse(savedLoanAccount);
    }

    @Override
    public List<LoanAccountResponse> getAllLoanAccounts() {

        return loanAccountRepository.findAll()
                .stream()
                .map(loanAccountMapper::toResponse)
                .toList();
    }

    @Override
    public LoanAccountResponse getLoanAccountById(Long id) {

        LoanAccount loanAccount = loanAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan account not found."));
        return loanAccountMapper.toResponse(loanAccount);
    }

    @Override
    public LoanAccountResponse updateLoanAccount(Long id, LoanAccountRequest request) {

        LoanAccount loanAccount = loanAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan account not found."));

        Customer customer = customerRepository.findById(
                request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found."));

        LoanProduct loanProduct = loanProductRepository.findById(
                request.getLoanProductId()).orElseThrow(() ->
                new RuntimeException("Loan product not found."));

        Branch branch = branchRepository.findById(
                request.getBranchId()).orElseThrow(() ->
                new RuntimeException("Branch not found."));

        User loanOfficer = userRepository.findById(
                request.getLoanOfficerId()).orElseThrow(() ->
                new RuntimeException("Loan officer not found."));

        if (loanOfficer.getRole() == null || !"LO".equalsIgnoreCase(
                        loanOfficer.getRole().getRoleName())) {
            throw new RuntimeException("Selected user is not a Loan Officer.");
        }

        validateLoanAmount(request.getLoanAmount(), loanProduct);
        validateTerm(request.getTerm());
        loanAccountMapper.updateLoanAccount(request, loanAccount);
        loanAccount.setCustomer(customer);
        loanAccount.setLoanProduct(loanProduct);
        loanAccount.setBranch(branch);
        loanAccount.setLoanOfficer(loanOfficer);
        loanAccount.setInterestRate(loanProduct.getInterestRate());
        loanAccount.setPenaltyRate(loanProduct.getPenaltyRate());
        loanAccount.setUpdatedAt(LocalDate.now());
        LoanAccount updatedLoanAccount = loanAccountRepository.save(loanAccount);

        return loanAccountMapper.toResponse(updatedLoanAccount);
    }

    @Override
    public void deactivateLoanAccount(Long id) {

        LoanAccount loanAccount = loanAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan account not found."));
        loanAccount.setStatus("INACTIVE");
        loanAccount.setUpdatedAt(LocalDate.now());
        loanAccountRepository.save(loanAccount);
    }

    private void validateLoanAmount(BigDecimal loanAmount, LoanProduct loanProduct) {
        if (loanAmount == null || loanAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Loan amount must be greater than zero.");
        }

        if (loanAmount.compareTo(loanProduct.getMinimumAmount()) < 0) {
            throw new RuntimeException("Loan amount cannot be less than minimum amount.");
        }

        if (loanAmount.compareTo(loanProduct.getMaximumAmount()) > 0) {
            throw new RuntimeException("Loan amount cannot exceed maximum amount.");
        }
    }

    private void validateTerm(Integer term) {
        if (term == null || term <= 0) {
            throw new RuntimeException("Loan term must be greater than zero.");
        }
    }

    private String generateAccountNumber() {
        long nextId = loanAccountRepository.count() + 1;
        return String.format("LN%06d", nextId);
    }
}