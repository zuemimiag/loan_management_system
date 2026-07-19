package com.smartloan.smart_loan_management_system.exception;

import com.smartloan.smart_loan_management_system.repository.UserRepository;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
        super(message);
    }
}
