package com.smartloan.smart_loan_management_system.service;

import com.smartloan.smart_loan_management_system.dto_request.BranchRequest;
import com.smartloan.smart_loan_management_system.dto_request.BranchResponse;
import com.smartloan.smart_loan_management_system.entity.Branch;

import java.util.List;

public interface BranchService {

    BranchResponse createBranch(BranchRequest request);

    List<BranchResponse> getAllBranch();

    BranchResponse getBranchById(Long id);

    BranchResponse updatedBranch(Long id,BranchRequest request);

    void deactivatedBranch(Long id);
}
