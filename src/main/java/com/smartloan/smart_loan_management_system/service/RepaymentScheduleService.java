package com.smartloan.smart_loan_management_system.service;

import com.smartloan.smart_loan_management_system.dto_request.RepaymentScheduleResponse;
import com.smartloan.smart_loan_management_system.entity.RepaymentSchedule;

import java.util.List;

public interface RepaymentScheduleService {

    List<RepaymentSchedule> findByLoanAccountId(Long loanAccountId);

    List<RepaymentScheduleResponse> generateSchedule(Long loanAccountId);

    List<RepaymentScheduleResponse> getScheduleByLoanAccount(Long loanAccountId);
}