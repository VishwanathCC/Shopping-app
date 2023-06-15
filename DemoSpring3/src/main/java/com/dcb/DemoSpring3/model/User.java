package com.dcb.DemoSpring3.model;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
public class User {

    private long id;
    private String name;
    private String emailId;

}
