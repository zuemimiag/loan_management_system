package com.smartloan.smart_loan_management_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @GetMapping
    public String test(){
        return "Role API Working";
    }
}
