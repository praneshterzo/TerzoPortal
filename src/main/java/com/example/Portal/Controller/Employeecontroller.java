package com.example.Portal.Controller;

import com.example.Portal.Dto.AuthenticationResponseDto;
import com.example.Portal.Dto.EmployeeDto;
import com.example.Portal.Dto.HolidayDto;
import com.example.Portal.Dto.LoginDto;
import com.example.Portal.Handler.ResponseHandler;
import com.example.Portal.Models.ApplyLeave;
import com.example.Portal.Models.Employee;
import com.example.Portal.Models.Login;
import com.example.Portal.Services.EmpServices;
import com.example.Portal.Services.Loginservices;
import com.example.Portal.Services.RefreshTokenService;
import com.example.Portal.Services.ServiceImplementation.LoginService;
import com.example.Portal.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
public class Employeecontroller {
    EmpServices empServices;

    Loginservices loginservices;

    RefreshTokenService refreshTokenService;

    JwtUtils jwtUtils;

    LoginService loginService;
    @Autowired
    public Employeecontroller(EmpServices empServices,Loginservices loginservices,RefreshTokenService refreshTokenService,JwtUtils jwtUtils,LoginService loginService) {
        this.empServices = empServices;
        this.loginservices=loginservices;
        this.refreshTokenService=refreshTokenService;
        this.jwtUtils=jwtUtils;
        this.loginService=loginService;
    }
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDTO) {

        Employee employee = empServices.getByEmployeeEmail(loginDTO.getEmpEmail());
        if(employee==null){
            return ResponseHandler.generateResponse("User not found , please sign up again!!!", HttpStatus.BAD_REQUEST);
        }

        if(!employee.isActive())
            return ResponseHandler.generateResponse("User is not active", HttpStatus.FORBIDDEN);

        AuthenticationResponseDto responseDto = loginService.authenticate(loginDTO);
        System.out.println(responseDto);
        if (responseDto != null)
            return ResponseHandler.generateResponse( responseDto,"Login Successful", HttpStatus.OK);
        else
            return ResponseHandler.generateResponse("Try again",HttpStatus.EXPECTATION_FAILED);

    }


    @GetMapping("/view")
    public List<EmployeeDto> listEmployees() {
        List<EmployeeDto> Details = empServices.findallemployee();
        return Details;
    }

    @GetMapping("/home")
    public List<HolidayDto> listholiday(){
        List<HolidayDto> holidayDetails=empServices.findallholidays();
        return holidayDetails;
    }

    @PostMapping("/add")
    public String Employeeadd(@RequestBody EmployeeDto employeeDto, Login login){
        Employee emp=new Employee();
        if(employeeDto.getEmpEmail().endsWith("@terzo.com"))
            emp.setEmpEmail(employeeDto.getEmpEmail());
        emp.setEmpPassword(passwordEncoder.encode(employeeDto.getEmpPassword()));
        emp.setEmpDatefBirth(employeeDto.getEmpDateofBirth());
        emp.setRole(loginservices.findByName(employeeDto.getEmpRole()));
        emp.setEmpManager(employeeDto.getEmpManager());
        emp.setEmpSalary(employeeDto.getEmpSalary());
        emp.setEmpMobileNumber(employeeDto.getEmpMobileNumber());
        emp.setEmpJoiningDate(employeeDto.getEmpJoiningDate());
        emp.setEmpEmail(employeeDto.getEmpEmail());
        emp.setEmpDepartment(employeeDto.getEmpDepartment());
        emp.setEmpEmploymentType(employeeDto.getEmpEmploymentType());
        emp.setEmpAddress(employeeDto.getEmpAddress());
        emp.setEmpDesignation(employeeDto.getEmpDesignation());
        emp.setActive(true);
        empServices.save(emp);
        login.setEmpId(emp.getEmpId());
        login.setUserEmail(employeeDto.getEmpEmail());
        login.setUserPassword(passwordEncoder.encode(employeeDto.getEmpPassword()));
        login.setRole1(loginservices.findByName(employeeDto.getEmpRole()));
        loginservices.save(login);
        return "New Employee Added";
    }
    @GetMapping("/employee/{id}/view")
    public EmployeeDto view(@PathVariable("id")String id ){

        Employee employee = empServices.getEmployeeById((Integer.parseInt(id)));
        EmployeeDto employeeDto= new EmployeeDto();
        BeanUtils.copyProperties(employee,employeeDto);
        employeeDto.setEmpDateofBirth(employee.getEmpDatefBirth());
        return employeeDto;
    }

 @Transactional
 @DeleteMapping("/employee/{id}/delete")
    public String deleteemplo(@PathVariable("id") int id){
        empServices.deleteemplo(id);
        return "Employee Details Deleted Successfully";
    }

    @PutMapping("/employee/{id}/update")
    public String save(@PathVariable("id")int id ,@RequestBody Employee employee){
        Employee empl = empServices.getEmployeeById(id);
        System.out.println(empl);
        if(employee.getEmpEmail().endsWith("@terzo.com"))
            empl.setEmpEmail(employee.getEmpEmail());
        else
            return "Enter Valid Email Id";
        empl.setEmpDatefBirth(employee.getEmpDatefBirth());
        empl.setEmpPassword(passwordEncoder.encode(employee.getEmpPassword()));
        empl.setEmpAddress(employee.getEmpAddress());
        empl.setEmpDesignation(employee.getEmpDesignation());
        empl.setApplyLeaves(employee.getApplyLeaves());
        empl.setEmpEmploymentType(employee.getEmpEmploymentType());
        empl.setEmpDepartment(employee.getEmpDepartment());
        empl.setEmpRole(employee.getEmpRole());
        empl.setEmpJoiningDate(employee.getEmpJoiningDate());
        empl.setEmpManager(employee.getEmpManager());
        empl.setEmpSalary(employee.getEmpSalary());
        empl.setEmpMobileNumber(employee.getEmpMobileNumber());
        empl.setLeaves(employee.getLeaves());
        empl.setLogins(employee.getLogins());
        empServices.save(empl);
        return "Details Updated Successfully";
    }

    @PutMapping("/employee/{id}/leave")
    public String Leave(@PathVariable("id") int id, @RequestBody ApplyLeave applyLeave){
        Employee Leave1=empServices.getEmployeeById(id);
        String type=applyLeave.getLeaveType();
        int available=empServices.getAvailabledays(type);
        if((int)(ChronoUnit.DAYS.between(applyLeave.getEndingDate(),applyLeave.getStartingDate()))>available){
            return "Leave Request is sent to Manager";
        }
        else
            return "No available Days";
    }
}
