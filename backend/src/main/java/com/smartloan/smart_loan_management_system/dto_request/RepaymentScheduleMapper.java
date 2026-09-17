package com.smartloan.smart_loan_management_system.dto_request;

import com.smartloan.smart_loan_management_system.entity.RepaymentSchedule;
import org.springframework.stereotype.Component;

@Component
public class RepaymentScheduleMapper {

    public RepaymentScheduleResponse toResponse(
            RepaymentSchedule schedule) {

        RepaymentScheduleResponse response =
                new RepaymentScheduleResponse();

        response.setId(schedule.getId());

        if (schedule.getLoanAccount() != null) {
            response.setLoanAccountId(
                    schedule.getLoanAccount().getId()
            );

            response.setAccountNumber(
                    schedule.getLoanAccount().getAccountNumber()
            );
        }

        response.setInstallmentNo(
                schedule.getInstallmentNo()
        );

        response.setDueDate(
                schedule.getDueDate()
        );

        response.setPrincipalDue(
                schedule.getPrincipalDue()
        );

        response.setInterestDue(
                schedule.getInterestDue()
        );

        response.setPenaltyDue(
                schedule.getPenaltyDue()
        );

        response.setTotalDue(
                schedule.getTotalDue()
        );

        response.setPrincipalPaid(
                schedule.getPrincipalPaid()
        );

        response.setInterestPaid(
                schedule.getInterestPaid()
        );

        response.setPenaltyPaid(
                schedule.getPenaltyPaid()
        );

        response.setStatus(
                schedule.getStatus()
        );

        response.setCreatedAt(
                schedule.getCreatedAt()
        );

        response.setUpdatedAt(
                schedule.getUpdatedAt()
        );

        return response;
    }
}