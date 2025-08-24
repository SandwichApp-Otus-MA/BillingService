package com.sandwich.app.controller;

import com.sandwich.app.domain.dto.payment.PaymentRequest;
import com.sandwich.app.domain.dto.payment.PaymentResponse;
import com.sandwich.app.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> create(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.create(request));
    }

    @PostMapping("/check-status/{id}")
    public ResponseEntity<PaymentResponse> checkStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(paymentService.checkStatus(id));
    }
}
