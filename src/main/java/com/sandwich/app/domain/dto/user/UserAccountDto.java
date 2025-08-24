package com.sandwich.app.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandwich.app.domain.entity.UserAccountEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link UserAccountEntity}
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccountDto {
    private UUID id;
    @NotNull
    private UUID userId;
    @NotNull
    private BigDecimal balance;
}