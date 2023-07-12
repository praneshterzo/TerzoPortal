package com.example.Portal.Services;

import com.example.Portal.Dto.EmployeeDto;
import com.example.Portal.Dto.HolidayDto;
import com.example.Portal.Models.Employee;

import java.util.List;

public interface EmpServices {
    public void save(Employee emp);



    public Employee getByEmployeeEmail(String email);

    List<EmployeeDto> findallemployee();

    List<HolidayDto> findallholidays();

    Employee getEmployeeById(int id);

//    Leave getEmployeById(int id);

    void deleteemplo(int id);

    int getAvailabledays(String type);
}
