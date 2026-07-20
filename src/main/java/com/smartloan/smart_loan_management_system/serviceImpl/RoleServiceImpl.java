package com.smartloan.smart_loan_management_system.serviceImpl;

import com.smartloan.smart_loan_management_system.dto_request.RoleMapper;
import com.smartloan.smart_loan_management_system.dto_request.RoleRequest;
import com.smartloan.smart_loan_management_system.dto_request.RoleResponse;
import com.smartloan.smart_loan_management_system.entity.Role;
import com.smartloan.smart_loan_management_system.exception.RoleNameAlreadyExistsException;
import com.smartloan.smart_loan_management_system.exception.RoleNotFoundException;
import com.smartloan.smart_loan_management_system.repository.RoleRepository;
import com.smartloan.smart_loan_management_system.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if(roleRepository.existsByRoleName(request.getRoleName())){
            throw new RoleNameAlreadyExistsException("RoleName Already Exists.");
        }
        Role role = roleMapper.toRequest(request) ;
        role.setCreatedAt(new Date());
        Role savedRole = roleRepository.save(role);

        return roleMapper.toResponse(savedRole);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new RoleNotFoundException("Role Not Found"));
        return null;
    }

    @Override
    public RoleResponse updateRole(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new RoleNotFoundException("Role not found"));

        roleMapper.updatedRole(request,role);
        Role updatedRole = roleRepository.save(role);

        return roleMapper.toResponse(updatedRole);
    }

    @Override
    public void deactivateRole(Long id) {
      Role role = roleRepository.findById(id)
              .orElseThrow(()-> new RoleNotFoundException("Role Not Found"));
      role.setStatus("INACTIVE");
      role.setUpdatedAt(new Date());

      roleRepository.save(role);
    }
}
