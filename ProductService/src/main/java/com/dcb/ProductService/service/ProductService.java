package com.dcb.ProductService.service;

import com.dcb.ProductService.model.ProductRequest;
import com.dcb.ProductService.model.ProductResponse;

import java.util.List;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    List<ProductResponse> getProducts();

    ProductResponse getProductById(Long productId);

    void reduceQuantity(Long productId, long quantity);

    ProductResponse updateProduct(ProductRequest productRequest, Long productId);
}
