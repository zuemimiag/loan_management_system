package com.smartloan.smart_loan_management_system.controller;

import com.smartloan.smart_loan_management_system.dto_request.RoleRequest;
import com.smartloan.smart_loan_management_system.dto_request.RoleResponse;
import com.smartloan.smart_loan_management_system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(RoleRequest request){
        RoleResponse response = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id){
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PutMapping
    public ResponseEntity<RoleResponse> updatedByRole(@PathVariable Long id,
                                                      @RequestBody RoleRequest request){
        return ResponseEntity.ok(roleService.updateRole(id,request));
    }

    @PutMapping("/deactive/{id}")
    public ResponseEntity<String> deactivateRole(@PathVariable Long id){
        roleService.deactivateRole(id);
        return ResponseEntity.ok("Role deactivated successfully.");
    }
}
