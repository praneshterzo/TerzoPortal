package com.example.Portal.Dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class EmployeeDto {


    private String empEmail;

    private String empPassword;
    private String empAddress;

    private String empDesignation;

    private String empDepartment;

    private String empRole;

    private String empManager;

    private String empEmploymentType;

    private Date empJoiningDate;

    private int empSalary;

    private int empMobileNumber;

    private Date empDateofBirth;
}
