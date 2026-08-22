package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.BranchMapper;
import com.smartloan.smart_loan_management_system.dto_request.BranchRequest;
import com.smartloan.smart_loan_management_system.dto_request.BranchResponse;
import com.smartloan.smart_loan_management_system.entity.Branch;
import com.smartloan.smart_loan_management_system.entity.Regions;
import com.smartloan.smart_loan_management_system.exception.BranchNotFoundException;
import com.smartloan.smart_loan_management_system.exception.RegionNotFoundException;
import com.smartloan.smart_loan_management_system.repository.BranchRepository;
import com.smartloan.smart_loan_management_system.repository.RegionRepository;
import com.smartloan.smart_loan_management_system.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;
    private final RegionRepository regionRepository;

    @Override
    public BranchResponse createBranch(BranchRequest request) {

        if(branchRepository.existsByBranchCode(request.getBranchCode())){
            throw new BranchNotFoundException("BranchCode Already Exist.");
        }

        Regions regions = regionRepository.findById(request.getRegionId())
                .orElseThrow(()->
                        new RegionNotFoundException("Region not found."));

        Branch branch = branchMapper.toEntity(request);

        branch.setRegions(regions);
        branch.setCreateAt(new Date());
        Branch savedBranch = branchRepository.save(branch);
        return branchMapper.toResponse(savedBranch);
    }

    @Override
    public List<BranchResponse> getAllBranch() {
        List<Branch> branches = branchRepository.findAll();
        return branches.stream()
                .map(branchMapper::toResponse)
                .toList();
    }

    @Override
    public BranchResponse getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(()->new BranchNotFoundException("Branch Not Found!."));
        return branchMapper.toResponse(branch);
    }

    @Override
    public BranchResponse updatedBranch(Long id, BranchRequest request) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(()->new BranchNotFoundException("Branch Not Found!"));
        branchMapper.updatedBranch(request,branch);
        Branch savedBranch = branchRepository.save(branch);
        return branchMapper.toResponse(savedBranch);
    }

    @Override
    public void deactivatedBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(()->new BranchNotFoundException("Branch Not Found."));
        branch.setStatus("INACTIVE");
        branch.setUpdatedAt(new Date());

        branchRepository.save(branch);
    }
}
