package com.smartloan.smart_loan_management_system.dto_request;

import com.smartloan.smart_loan_management_system.entity.Role;
import com.smartloan.smart_loan_management_system.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {
        User user = new User();
        Role role = new Role();

        user.setName(request.getName());
        user.setEmployeeId(request.getEmployeeId());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setStatus(request.getStatus());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setRole(role);
        return user;
    }

    public UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();
        response.setName(user.getName());
        response.setEmployeeId(user.getEmployeeId());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setLastLogin(user.getLastLogin());
        response.setStatus(user.getStatus());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        response.setRoleName(user.getRole().getRoleName());

        return response;
    }

    public User updateEntity(UserRequest request,User user){
        Role role = new Role();
        user.setName(request.getName());
        user.setEmployeeId(request.getEmployeeId());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setStatus(request.getStatus());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setRole(role);

        return user;
    }
}
