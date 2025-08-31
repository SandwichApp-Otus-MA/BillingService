package com.sandwich.app.service;

import com.sandwich.app.domain.dto.user.UserAccountDto;
import com.sandwich.app.domain.entity.UserAccountEntity;
import com.sandwich.app.domain.repository.UserAccountRepository;
import com.sandwich.app.mapper.UserAccountMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;

    @Transactional(readOnly = true)
    public UserAccountDto get(UUID id) {
        return userAccountRepository.findByUserId(id)
            .map(userAccountMapper::convert)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public UUID create(UserAccountDto user) {
        Optional.ofNullable(user.getUserId())
            .flatMap(id -> userAccountRepository.findByUserId(user.getUserId())).ifPresent(o -> {
                throw new IllegalStateException("Пользователь c id: %s уже существует!".formatted(user.getUserId()));
            });

        var newUserAccount = userAccountMapper.convert(new UserAccountEntity(), user)
            .setUserId(user.getUserId());
        return userAccountRepository.save(newUserAccount).getId();
    }

    @Transactional
    public void edit(UserAccountDto user) {
        var existUser = Optional.ofNullable(user.getUserId())
            .flatMap(id -> userAccountRepository.findByUserIdWithLock(user.getUserId()))
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userAccountMapper.convert(existUser, user);
    }

    @Transactional
    public BigDecimal deposit(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма пополнения должна быть положительной");
        }

        var account = userAccountRepository.findByUserIdWithLock(userId)
            .orElseThrow(() -> new EntityNotFoundException("Аккаунт не найден"));

        var add = account.getBalance().add(amount);
        account.setBalance(add);
        return account.getBalance();
    }

    @Transactional
    public BigDecimal withdraw(UUID userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма снятия должна быть положительной");
        }

        var account = userAccountRepository.findByUserIdWithLock(userId)
            .orElseThrow(() -> new EntityNotFoundException("Аккаунт не найден"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Недостаточно средств на счете");
        }

        var subtract = account.getBalance().subtract(amount);
        account.setBalance(subtract);
        return account.getBalance();
    }

    @Transactional
    public void delete(UUID id) {
        userAccountRepository.deleteByUserId(id);
    }
}
