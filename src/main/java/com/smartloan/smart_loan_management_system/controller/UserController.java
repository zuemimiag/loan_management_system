package com.smartloan.smart_loan_management_system.controller;

import com.smartloan.smart_loan_management_system.dto_request.UserRequest;
import com.smartloan.smart_loan_management_system.dto_request.UserResponse;
import com.smartloan.smart_loan_management_system.entity.User;
import com.smartloan.smart_loan_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.updateUser(id,request));
    }
    @PutMapping("/deactive/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deactivated successfully.");
    }

}
