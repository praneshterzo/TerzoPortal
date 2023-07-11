package com.example.Portal.Services.ServiceImplementation;

import com.example.Portal.Dto.EmployeeDto;
import com.example.Portal.Dto.HolidayDto;
import com.example.Portal.Models.Employee;
import com.example.Portal.Models.Holiday;
import com.example.Portal.Models.Leave;
import com.example.Portal.Models.Login;
import com.example.Portal.Repoistry.HolidayRepoistry;
import com.example.Portal.Repoistry.Leaverepoistry;
import com.example.Portal.Repoistry.EmployeeRepoistry;
import com.example.Portal.Repoistry.LoginRepoistry;
import com.example.Portal.Services.EmpServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesImplementation implements EmpServices {

    EmployeeRepoistry employeerepo;

    Leaverepoistry leaverepoistry;

    HolidayRepoistry holidayRepoistry;

    LoginRepoistry loginRepoistry;

    @Autowired
    public ServicesImplementation(EmployeeRepoistry employeerepo, Leaverepoistry leaverepoistry, HolidayRepoistry holidayRepoistry, List<EmployeeDto> emplist,LoginRepoistry loginRepoistry) {
        this.employeerepo = employeerepo;
        this.leaverepoistry = leaverepoistry;
        this.holidayRepoistry = holidayRepoistry;
        this.loginRepoistry=loginRepoistry;
        Emplist = emplist;
    }

    public List<EmployeeDto> Emplist;
    @Override
    public void save(Employee emp) {
        employeerepo.save(emp);
    }

    @Override
    public Employee getByEmployeeEmail(String email) {
        return employeerepo.findByEmpEmail(email);
    }

    @Override
    public List<EmployeeDto> findallemployee() {
        List<Employee> emp=employeerepo.findAll();
        return emp.stream().map((empl)-> mapToEmployeeDto(empl)).collect(Collectors.toList());
    }
    @Override
    public List<HolidayDto> findallholidays(){
        List<Holiday> holi=holidayRepoistry.findAll();
        return holi.stream().map((hol)-> mapToHolidayDto(hol)).collect(Collectors.toList());
    }

    @Override
    public Employee getEmployeeById(int id) {

        return employeerepo.findByEmpId(id);
    }

//    @Override
//    public Leave getEmployeById(int id) {
//        return leaverepoistry.findByEmplo(id);
//    }

    @Override
    public void deleteemplo(int id) {

        employeerepo.deleteByEmpId(id);
    }

    @Override
    public int getAvailabledays(String type) {
        if (type.equals("Medeicalleave")) {
            return Leave.Medicalleave;
        }
        else if (type.equals("PaidLeave")) {
            return Leave.PaidLeave;
        }
        else
            return Leave.Vacationdays;
    }

    private EmployeeDto mapToEmployeeDto(Employee empl){
        EmployeeDto employeeDto=EmployeeDto.builder()
                .empAddress(empl.getEmpAddress())
                .empDepartment(empl.getEmpDepartment())
                .empDesignation(empl.getEmpDesignation())
                .empEmail(empl.getEmpEmail())
                .empJoiningDate(empl.getEmpJoiningDate())
                .empRole(empl.getEmpRole())
                .empEmploymentType(empl.getEmpEmploymentType())
                .empManager(empl.getEmpManager())
                .empMobileNumber(empl.getEmpMobileNumber())
                .build();
        return employeeDto;
    }

    private HolidayDto mapToHolidayDto(Holiday hol) {
        HolidayDto holidayDto=HolidayDto.builder()
                .holidayDate(hol.getHolidayDate())
                .holidayEvent(hol.getHolidayEvent())
                .build();
        return holidayDto;

    }

}
