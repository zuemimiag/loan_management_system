package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.UserMapper;
import com.smartloan.smart_loan_management_system.dto_request.UserRequest;
import com.smartloan.smart_loan_management_system.dto_request.UserResponse;
import com.smartloan.smart_loan_management_system.entity.Role;
import com.smartloan.smart_loan_management_system.entity.User;
import com.smartloan.smart_loan_management_system.exception.EmailAlreadyExistsException;
import com.smartloan.smart_loan_management_system.exception.RoleNotFoundException;
import com.smartloan.smart_loan_management_system.exception.UserNotFoundException;
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
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        // 1. check email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        //2. Find Role
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found."));

        //3. Convert DTO -> Entity
        User user = userMapper.toEntity(request);
        user.setRole(role);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        User savedUser = userRepository.save(user);

    return userMapper.toResponse(savedUser) ;
    }
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found."));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found."));
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(()->new RuntimeException("Role not found."));

        userMapper.updateEntity(request,user);
        user.setRole(role);
        user.setUpdatedAt(new Date());

        User updateUser = userRepository.save(user);
        return userMapper.toResponse(updateUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found."));
        user.setStatus("INACTIVE");
        user.setUpdatedAt(new Date());

        userRepository.save(user);
    }
}
