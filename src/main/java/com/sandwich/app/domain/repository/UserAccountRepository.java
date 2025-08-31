package com.sandwich.app.domain.repository;

import com.sandwich.app.domain.entity.UserAccountEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, UUID>,
    JpaSpecificationExecutor<UserAccountEntity>,
    QuerydslPredicateExecutor<UserAccountEntity> {

    Optional<UserAccountEntity> findByUserId(UUID id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM UserAccountEntity a WHERE a.userId = :id")
    Optional<UserAccountEntity> findByUserIdWithLock(@Param("id") UUID id);

    @Modifying
    @Query("delete from UserAccountEntity where userId = :id")
    void deleteByUserId(@Param("id") UUID id);
}