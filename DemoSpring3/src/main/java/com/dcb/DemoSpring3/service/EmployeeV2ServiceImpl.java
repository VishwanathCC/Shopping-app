package com.dcb.DemoSpring3.service;

import com.dcb.DemoSpring3.entity.EmployeeEntity;
import com.dcb.DemoSpring3.model.Employee;
import com.dcb.DemoSpring3.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeV2ServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        if(employee.getEmployeeId()==null || employee.getEmployeeId().isEmpty()){
            employee.setEmployeeId(UUID.randomUUID().toString());
        }
        EmployeeEntity entity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, entity);
        employeeRepository.save(entity);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<EmployeeEntity> allEntity = employeeRepository.findAll();
        return allEntity.stream()
                .map(employeeEntity -> {
                            Employee employee = new Employee();
                            BeanUtils.copyProperties(employeeEntity, employee);
                            return employee;
                        })
                .collect(Collectors.toList());
    }

    @Override
    public Employee getEmployee(String employeeId) {
        final EmployeeEntity byId = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee with ID: " + employeeId + "not found"));
        Employee employee = new Employee();
        BeanUtils.copyProperties(byId, employee);
        return employee;
    }

    @Override
    public Employee deleteEmployee(String employeeId) {
        final EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee with ID: " + employeeId + "not found"));
        employeeRepository.delete(employeeEntity);
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeEntity, employee);
        return employee;
    }
}
