package com.sandwich.app.controller;

import com.sandwich.app.domain.dto.user.UserAccountDto;
import com.sandwich.app.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/v1/user-account")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userAccountService.get(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<UUID> create(@Valid @RequestBody UserAccountDto user) {
        return ResponseEntity.ok(userAccountService.create(user));
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> edit(@Valid @RequestBody UserAccountDto user) {
        userAccountService.edit(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deposit/{id}")
    public ResponseEntity<BigDecimal> deposit(@PathVariable UUID id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(userAccountService.deposit(id, amount));
    }

    @PostMapping("/withdraw/{id}")
    public ResponseEntity<BigDecimal> withdraw(@PathVariable UUID id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(userAccountService.withdraw(id, amount));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userAccountService.delete(id);
        return ResponseEntity.ok().build();
    }
}
