package com.smartloan.smart_loan_management_system.dto_request;

import com.smartloan.smart_loan_management_system.entity.LoanAccount;
import org.springframework.stereotype.Component;

@Component
public class LoanAccountMapper {

    public LoanAccount toEntity(LoanAccountRequest request) {

        LoanAccount loanAccount = new LoanAccount();

        loanAccount.setLoanAmount(request.getLoanAmount());
        loanAccount.setTerm(request.getTerm());
        loanAccount.setStatus(request.getStatus());

        return loanAccount;
    }

    public LoanAccountResponse toResponse(LoanAccount loanAccount) {

        LoanAccountResponse response = new LoanAccountResponse();

        response.setId(loanAccount.getId());
        response.setAccountNumber(loanAccount.getAccountNumber());

        if (loanAccount.getCustomer() != null) {
            response.setCustomerId(loanAccount.getCustomer().getId());
            response.setCustomerName(loanAccount.getCustomer().getCustomerName());
        }

        if (loanAccount.getLoanProduct() != null) {
            response.setLoanProductId(loanAccount.getLoanProduct().getId());
            response.setLoanProductName(loanAccount.getLoanProduct().getProductName());
        }

        if (loanAccount.getBranch() != null) {
            response.setBranchId(loanAccount.getBranch().getId());
            response.setBranchName(loanAccount.getBranch().getBranchName());
        }

        if (loanAccount.getLoanOfficer() != null) {
            response.setLoanOfficerId(loanAccount.getLoanOfficer().getId());
            response.setLoanOfficerName(loanAccount.getLoanOfficer().getName());
        }

        response.setLoanAmount(loanAccount.getLoanAmount());
        response.setInterestRate(loanAccount.getInterestRate());
        response.setTerm(loanAccount.getTerm());
        response.setPenaltyRate(loanAccount.getPenaltyRate());
        response.setStatus(loanAccount.getStatus());
        response.setDisbursementDate(loanAccount.getDisbursementDate());
        response.setMaturityDate(loanAccount.getMaturityDate());
        response.setCreatedAt(loanAccount.getCreatedAt());
        response.setUpdatedAt(loanAccount.getUpdatedAt());

        return response;
    }

    public LoanAccount updateLoanAccount(LoanAccountRequest request, LoanAccount loanAccount) {

        loanAccount.setLoanAmount(request.getLoanAmount());
        loanAccount.setTerm(request.getTerm());
        loanAccount.setStatus(request.getStatus());

        return loanAccount;
    }
}
