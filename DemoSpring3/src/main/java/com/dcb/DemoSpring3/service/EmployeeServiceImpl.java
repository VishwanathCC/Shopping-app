package com.dcb.DemoSpring3.service;

import com.dcb.DemoSpring3.entity.EmployeeEntity;
import com.dcb.DemoSpring3.exception.EmployeeNotFoundException;
import com.dcb.DemoSpring3.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    List<Employee> employees = new ArrayList<>();

    @Override
    public Employee save(Employee employee) {
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()){
            employee.setEmployeeId(UUID.randomUUID().toString());
        }
        employees.add(employee);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employees;
    }

    @Override
    public Employee getEmployee(String employeeId) {
        return employees.parallelStream()
                .filter((employee) -> employee.getEmployeeId().equals(employeeId))
                .findAny()
                .orElseThrow(() -> new EmployeeNotFoundException("no data found for this Id: " + employeeId));
    }

    @Override
    public Employee deleteEmployee(String employeeId) {
        Employee employee = employees.parallelStream()
                .filter((emp) -> emp.getEmployeeId().equals(employeeId))
                .findAny()
                .orElseThrow(() -> new EmployeeNotFoundException("no data found with this Id: " + employeeId + "delete not performed"));
        employees.remove(employee);
        return employee;
    }
}
