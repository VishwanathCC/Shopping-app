package com.dcb.DemoSpring3.service;

import com.dcb.DemoSpring3.entity.EmployeeEntity;
import com.dcb.DemoSpring3.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    Employee save(Employee employee);

    List<Employee> getAllEmployee();

    Employee getEmployee(String employeeId);

    Employee deleteEmployee(String employeeId);
}
