package com.smartloan.smart_loan_management_system.exception;

public class LoanProductNotFoundException extends RuntimeException{

    public LoanProductNotFoundException(String message){
        super(message);
    }
}
