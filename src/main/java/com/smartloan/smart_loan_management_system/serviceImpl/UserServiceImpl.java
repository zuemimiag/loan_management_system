package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.UserRequest;
import com.smartloan.smart_loan_management_system.dto_request.UserResponse;
import com.smartloan.smart_loan_management_system.entity.Role;
import com.smartloan.smart_loan_management_system.entity.User;
import com.smartloan.smart_loan_management_system.repository.RoleRepository;
import com.smartloan.smart_loan_management_system.repository.UserRepository;
import com.smartloan.smart_loan_management_system.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        // 1. check email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        //2. Find Role
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found."));

        //3. Convert DTO -> Entity
        User user = new User();
        user.setName(request.getName());
        user.setEmployeeId(request.getEmployeeId());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setStatus(request.getStatus());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setRole(role);

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setName(savedUser.getName());
        response.setEmployeeId(savedUser.getEmployeeId());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());
        response.setLastLogin(savedUser.getLastLogin());
        response.setStatus(savedUser.getStatus());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        response.setRoleName(savedUser.getRole().getRoleName());


    return response ;
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
