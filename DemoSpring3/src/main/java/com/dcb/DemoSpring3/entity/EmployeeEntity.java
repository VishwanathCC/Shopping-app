package com.dcb.DemoSpring3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EmployeeEntity {

    @Id
    private String employeeId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String department;

}
