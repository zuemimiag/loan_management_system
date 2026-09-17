package com.smartloan.smart_loan_management_system.dto_request;

import com.smartloan.smart_loan_management_system.entity.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {
    public Branch toEntity(BranchRequest request){
        Branch branch = new Branch();
        branch.setBranchCode(request.getBranchCode());
        branch.setBranchName(request.getBranchName());
        branch.setAddress(request.getAddress());
        branch.setEmail(request.getEmail());
        branch.setPhone(request.getPhone());
        branch.setStatus(request.getStatus());

        return branch;
    }

    public BranchResponse toResponse(Branch branch){
        BranchResponse response = new BranchResponse();
        response.setId(branch.getId());
        response.setBranchCode(branch.getBranchCode());
        response.setBranchName(branch.getBranchName());
        response.setAddress(branch.getAddress());
        response.setEmail(branch.getEmail());
        response.setPhone(branch.getPhone());
        response.setStatus(branch.getStatus());

        if(branch.getRegions() != null){
            response.setRegionId(branch.getRegions().getId());
            response.setRegionName(branch.getRegions().getRegionName());
        }

        response.setCreatedAt(branch.getCreateAt());
        response.setUpdatedAt(branch.getUpdatedAt());
        return response;
    }

    public Branch updatedBranch(BranchRequest request,Branch branch){
        branch.setBranchCode(request.getBranchCode());
        branch.setBranchName(request.getBranchName());
        branch.setAddress(request.getAddress());
        branch.setPhone(request.getPhone());
        branch.setEmail(request.getEmail());
        branch.setStatus(request.getStatus());
        branch.setUpdatedAt(request.getUpdatedAt());
        return branch;
    }
}
