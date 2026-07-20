package com.smartloan.smart_loan_management_system.dto_request;

import com.smartloan.smart_loan_management_system.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role toRequest(RoleRequest request){
        Role role = new Role();

        role.setRoleName(request.getRoleName());
        role.setDescription(request.getDescription());
        role.setStatus(request.getStatus());

        return role;
    }

    public RoleResponse toResponse(Role role){
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setRoleName(role.getRoleName());
        response.setDescription(role.getDescription());
        response.setStatus(role.getStatus());
        response.setCreatedAt(role.getCreatedAt());
        response.setUpdatedAt(role.getUpdatedAt());
        return response;
    }

    public Role updatedRole(RoleRequest request, Role role){
        role.setRoleName(request.getRoleName());
        role.setDescription(request.getDescription());
        role.setStatus(request.getStatus());
        role.setUpdatedAt(request.getUpdatedAt());

        return role;
    }
}
