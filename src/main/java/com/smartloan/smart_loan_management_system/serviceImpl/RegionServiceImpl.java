package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.RegionMapper;
import com.smartloan.smart_loan_management_system.dto_request.RegionRequest;
import com.smartloan.smart_loan_management_system.dto_request.RegionResponse;
import com.smartloan.smart_loan_management_system.entity.Regions;
import com.smartloan.smart_loan_management_system.exception.RegionCodeAlreadyExistException;
import com.smartloan.smart_loan_management_system.exception.RegionNotFoundException;
import com.smartloan.smart_loan_management_system.repository.RegionRepository;
import com.smartloan.smart_loan_management_system.service.RegionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public RegionServiceImpl(RegionRepository regionRepository, RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    @Override
    public RegionResponse createRegion(RegionRequest request) {
        if(regionRepository.existsByRegionCode(request.getRegionCode()))
            throw new RegionCodeAlreadyExistException("Region Code Already Exists.");
        Regions regions = regionMapper.toEntity(request);
        regions.setCreatedAt(new Date());
        regions.setUpdatedAt(new Date());
        Regions savedRegion = regionRepository.save(regions);
        return regionMapper.toResponse(savedRegion);
    }

    @Override
    public List<RegionResponse> getAllRegions() {
        List<Regions> regions = regionRepository.findAll();

        return regions.stream()
                .map(regionMapper::toResponse)
                .toList();
    }

    @Override
    public RegionResponse getRegionById(Long id) {
        Regions regions = regionRepository.findById(id)
                .orElseThrow(()-> new RegionNotFoundException("Region Not Found Exception."));
        return regionMapper.toResponse(regions);
    }

    @Override
    public RegionResponse updatedRegion(Long id, RegionRequest request) {
        Regions regions = regionRepository.findById(id)
                .orElseThrow(()-> new RegionNotFoundException("Region Not Found Exception."));
        regionMapper.updatedRegions(request,regions);
        Regions updatedRegion = regionRepository.save(regions);
        return regionMapper.toResponse(updatedRegion);
    }

    @Override
    public void deactivateRegion(Long id) {
        Regions regions = regionRepository.findById(id)
                .orElseThrow(()-> new RegionNotFoundException("Region Not Found Exception."));
        regions.setStatus("INACTIVE");
        regions.setUpdatedAt(new Date());
        regionRepository.save(regions);

    }
}
