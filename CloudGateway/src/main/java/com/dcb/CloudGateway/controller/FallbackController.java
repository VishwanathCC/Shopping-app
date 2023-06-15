package com.dcb.CloudGateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class FallbackController {
    @GetMapping("/orderServiceFallBack")
    public ResponseEntity<String> orderServiceFallBack() {
        return new ResponseEntity("orderServiceFallBack!", HttpStatus.OK);
    }

    @GetMapping("/productServiceFallBack")
    public ResponseEntity<String> productServiceFallBack() {
        return new ResponseEntity("productServiceFallBack!",HttpStatus.OK);
    }

    @GetMapping("/paymentServiceFallBack")
    public ResponseEntity<String> paymentServiceFallBack() {
        return new ResponseEntity("paymentServiceFallBack!",HttpStatus.OK);
    }
}
