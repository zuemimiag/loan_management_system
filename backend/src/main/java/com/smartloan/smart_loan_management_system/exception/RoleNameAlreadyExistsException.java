package com.smartloan.smart_loan_management_system.exception;

public class RoleNameAlreadyExistsException extends RuntimeException {

    public RoleNameAlreadyExistsException(String message){
        super(message);
    }
}
