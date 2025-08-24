package com.sandwich.app.service;

import com.sandwich.app.domain.dto.enums.PaymentStatus;
import com.sandwich.app.domain.dto.payment.PaymentRequest;
import com.sandwich.app.domain.dto.payment.PaymentResponse;
import com.sandwich.app.domain.entity.PaymentEntity;
import com.sandwich.app.domain.repository.PaymentRepository;
import com.sandwich.app.domain.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional
    public PaymentResponse create(PaymentRequest request) {
        // todo: эмуляция отправки заявки в сервис оплаты
        var externalId = UUID.randomUUID();

        var payment = new PaymentEntity()
            .setExternalId(externalId)
            .setAmount(request.getAmount())
            .setCurrency(request.getCurrency())
            .setOrderId(request.getOrderId())
            .setUserAccount(userAccountRepository.getReferenceById(request.getUserAccountId()))
            .setDescription(request.getDescription())
            .setStatus(PaymentStatus.PENDING);

        var newPayment = paymentRepository.save(payment);

        return new PaymentResponse()
            .setId(newPayment.getId())
            .setStatus(newPayment.getStatus());
    }

    @Transactional
    public PaymentResponse checkStatus(UUID id) {
        var payment = paymentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Платеж не найден"));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            return new PaymentResponse()
                .setId(payment.getId())
                .setStatus(payment.getStatus())
                .setErrorMessage(payment.getErrorMessage());
        }

        // todo: эмуляция проверки статуса платежа
        if (Math.random() < 0.5) {
            payment.setStatus(PaymentStatus.FAILED)
                .setErrorMessage("Что-то пошло не так!");
        } else {
            payment.setStatus(PaymentStatus.SUCCEEDED);
        }

        var updatedPayment = paymentRepository.save(payment);

        return new PaymentResponse()
            .setId(updatedPayment.getId())
            .setStatus(updatedPayment.getStatus())
            .setErrorMessage(updatedPayment.getErrorMessage());
    }
}
