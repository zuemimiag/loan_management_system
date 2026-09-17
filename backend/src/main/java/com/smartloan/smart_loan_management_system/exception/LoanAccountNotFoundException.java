package com.smartloan.smart_loan_management_system.exception;

public class LoanAccountNotFoundException extends RuntimeException{
    public LoanAccountNotFoundException(String message){
        super(message);
    }
}
