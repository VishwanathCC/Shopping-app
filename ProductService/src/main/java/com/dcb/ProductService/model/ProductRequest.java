package com.dcb.ProductService.model;

import lombok.*;

@Data
public class ProductRequest {
    private String name;
    private long price;
    private long quantity;
}
