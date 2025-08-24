package com.sandwich.app.domain.repository;

import com.sandwich.app.domain.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID>,
    JpaSpecificationExecutor<PaymentEntity>,
    QuerydslPredicateExecutor<PaymentEntity> {

}