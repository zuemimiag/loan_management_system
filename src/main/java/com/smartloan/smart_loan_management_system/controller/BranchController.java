package com.smartloan.smart_loan_management_system.controller;

import com.smartloan.smart_loan_management_system.dto_request.BranchRequest;
import com.smartloan.smart_loan_management_system.dto_request.BranchResponse;
import com.smartloan.smart_loan_management_system.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchResponse> createBranch(BranchRequest request){
        BranchResponse response = branchService.createBranch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAllBranches(){
        return ResponseEntity.ok(branchService.getAllBranch());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BranchResponse> getBranchById(@PathVariable Long id){
        return ResponseEntity.ok(branchService.getBranchById(id));
    }
    @PutMapping
    public ResponseEntity<BranchResponse> updatedBranch(@PathVariable Long id,
                                                        @RequestBody BranchRequest request){
        return ResponseEntity.ok(branchService.updatedBranch(id,request));
    }
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivatedBranch(@PathVariable Long id){
        branchService.deactivatedBranch(id);
        return ResponseEntity.ok("Branch deactivated successfully");
    }
}
