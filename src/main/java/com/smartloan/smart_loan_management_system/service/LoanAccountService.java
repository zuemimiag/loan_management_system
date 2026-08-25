package com.smartloan.smart_loan_management_system.service;

import com.smartloan.smart_loan_management_system.dto_request.LoanAccountRequest;
import com.smartloan.smart_loan_management_system.dto_request.LoanAccountResponse;

import java.util.List;

public interface LoanAccountService {

        LoanAccountResponse createLoanAccount(LoanAccountRequest request);

        List<LoanAccountResponse> getAllLoanAccounts();

        LoanAccountResponse getLoanAccountById(Long id);

        LoanAccountResponse updateLoanAccount(Long id, LoanAccountRequest request);

        void deactivateLoanAccount(Long id);
}
