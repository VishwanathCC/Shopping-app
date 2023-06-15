package com.dcb.DemoSpring3.controller;

import com.dcb.DemoSpring3.model.Employee;
import com.dcb.DemoSpring3.service.EmployeeService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/employees")
public class EmployeeV2Controller {

    @Qualifier("employeeV2ServiceImpl")
    @Autowired
    private EmployeeService employeeService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "not found", content = @Content(schema = @Schema(implementation = Error.class), examples = @ExampleObject(value = "{\n" + "    \"httpStatus\": \"NOT_FOUND\",\n" + "    \"message\": \"no data found for this Id: employeeId\"\n" + "}")))
    })
    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{employeeId}")
    public Employee getAEmployee(@PathVariable String employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @DeleteMapping(value = "/{employeeId}")
    public Employee deleteEmployee(@PathVariable String employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
}
