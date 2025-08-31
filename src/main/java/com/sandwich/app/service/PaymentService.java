package com.sandwich.app.service;

import com.sandwich.app.domain.dto.enums.PaymentStatus;
import com.sandwich.app.domain.dto.notificator.PaymentNotification;
import com.sandwich.app.domain.dto.payment.PaymentRequest;
import com.sandwich.app.domain.dto.payment.PaymentResponse;
import com.sandwich.app.domain.entity.PaymentEntity;
import com.sandwich.app.domain.repository.PaymentRepository;
import com.sandwich.app.domain.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserAccountRepository userAccountRepository;
    private final NotificationService notificationService;
    private final TransactionTemplate transactionTemplate;

    @Transactional
    public PaymentResponse create(PaymentRequest request) {
        // todo: эмуляция отправки заявки в сервис оплаты
        var externalId = UUID.randomUUID();

        var userAccount = userAccountRepository.findByUserIdWithLock(request.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("Не найден аккаунт пользователя!"));

        if (userAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("На счёте недостаточно средств!");
        }

        userAccount.setBalance(
            userAccount.getBalance().subtract(request.getAmount()));

        var payment = new PaymentEntity()
            .setExternalId(externalId)
            .setAmount(request.getAmount())
            .setCurrency(request.getCurrency())
            .setOrderId(request.getOrderId())
            .setUserAccount(userAccount)
            .setDescription(request.getDescription())
            .setStatus(PaymentStatus.PENDING);

        var newPayment = paymentRepository.save(payment);

        return new PaymentResponse()
            .setId(newPayment.getId())
            .setStatus(newPayment.getStatus());
    }

    public PaymentResponse checkStatus(UUID id) {
        var payment = getPayment(id);
        assert payment != null;

        var response = new PaymentResponse()
            .setId(payment.getId())
            .setStatus(payment.getStatus())
            .setErrorMessage(payment.getErrorMessage());

        notificationService.send(new PaymentNotification()
            .setUserId(payment.getUserAccount().getUserId())
            .setMessage(response));

        return response;
    }

    private PaymentEntity getPayment(UUID id) {
        return transactionTemplate.execute(status -> {
            var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Платеж не найден"));

            if (payment.getStatus() != PaymentStatus.PENDING) {
                return payment;
            }

            // todo: эмуляция проверки статуса платежа
            if (Math.random() < 0.5) {
                payment.setStatus(PaymentStatus.FAILED)
                    .setErrorMessage("Что-то пошло не так!");
            } else {
                payment.setStatus(PaymentStatus.SUCCEEDED);
            }

            return paymentRepository.save(payment);
        });
    }
}
