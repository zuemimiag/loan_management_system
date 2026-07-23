package com.smartloan.smart_loan_management_system.dto_request;

import com.smartloan.smart_loan_management_system.entity.Regions;

public class RegionMapper {

    public Regions toEntity(RegionRequest request){
        Regions regions = new Regions();
        regions.setRegionCode(request.getRegionCode());
        regions.setRegionName(request.getRegionName());
        regions.setManagerName(request.getManagerName());
        regions.setStatus(request.getStatus());
        return regions;
    }

    public RegionResponse toResponse(Regions regions){
        RegionResponse response = new RegionResponse();
        response.setId(regions.getId());
        response.setRegionCode(regions.getRegionCode());
        response.setRegionName(regions.getRegionName());
        response.setManagerName(regions.getManagerName());
        response.setStatus(regions.getStatus());
        response.setCreatedAt(regions.getCreatedAt());
        response.setUpdatedAt(regions.getUpdatedAt());

        return response;

    }

    public Regions updatedRegions(RegionRequest request,Regions regions){
        regions.setRegionCode(request.getRegionCode());
        regions.setRegionName(request.getRegionName());
        regions.setManagerName(request.getManagerName());
        regions.setStatus(request.getStatus());
        regions.setUpdatedAt(regions.getUpdatedAt());
        return regions;
    }
}
