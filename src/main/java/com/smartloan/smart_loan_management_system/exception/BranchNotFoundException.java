package com.smartloan.smart_loan_management_system.exception;

public class BranchNotFoundException extends RuntimeException{

    public BranchNotFoundException(String message){
        super(message);
    }
}
