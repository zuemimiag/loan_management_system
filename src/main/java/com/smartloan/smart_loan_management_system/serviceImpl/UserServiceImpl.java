package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.UserRequest;
import com.smartloan.smart_loan_management_system.dto_request.UserResponse;
import com.smartloan.smart_loan_management_system.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public UserResponse createUser(UserRequest request) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
