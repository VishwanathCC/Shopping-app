package com.dcb.OrderService.external.client;

import com.dcb.OrderService.exception.CustomException;
import com.dcb.OrderService.external.response.ProductResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name = "External", fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductService {

    @PutMapping("product/reduceQuantity/{productId}")
    ResponseEntity<Void> reduceQuantity(@PathVariable Long productId, @RequestParam long quantity);

    @GetMapping("product/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId);

    default void fallback(Exception ex) {
        throw new CustomException("Product service is down", "UNAVAILABLE", 500);
    }
}
