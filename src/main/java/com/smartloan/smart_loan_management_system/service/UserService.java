package com.smartloan.smart_loan_management_system.service;

import com.smartloan.smart_loan_management_system.dto_request.UserRequest;
import com.smartloan.smart_loan_management_system.dto_request.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
