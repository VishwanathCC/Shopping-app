package com.dcb.ProductService.service;

import com.dcb.ProductService.entity.Product;
import com.dcb.ProductService.exception.ProductServiceCustomException;
import com.dcb.ProductService.model.ProductRequest;
import com.dcb.ProductService.model.ProductResponse;
import com.dcb.ProductService.repository.ProductRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepo productRepo;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("adding product.."+ productRequest.toString());
        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
        productRepo.save(product);
        log.info("product created.." + product.toString());
        return product.getProductId();
    }

    @Override
    public List<ProductResponse> getProducts() {
        List<ProductResponse> productResponses = new ArrayList<>();
        List<Product> products = productRepo.findAll();
        products.stream()
                .forEach(product -> {
                    ProductResponse productResponse = new ProductResponse();
                    BeanUtils.copyProperties(product, productResponse);
                    productResponses.add(productResponse);
                });
        return productResponses;
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("product with id: " + productId + " not found", "PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(Long productId, long quantity) {
        log.info("reduce quantity {} for Id: {}", quantity, productId);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("product with id: " + productId + " not found", "PRODUCT_NOT_FOUND"));
        if (product.getQuantity() < quantity) {
            throw new ProductServiceCustomException("Product does not have sufficient Quantity", "INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepo.save(product);
        log.info("product quantity updated successfully");
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest, Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("product with id: " + productId + " not found", "PRODUCT_NOT_FOUND"));
        product.setQuantity(productRequest.getQuantity());
        product.setProductName(productRequest.getName());
        product.setPrice(productRequest.getPrice());

        productRepo.save(product);
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);

        return productResponse;
    }
}
