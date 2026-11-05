package com.ark.investment.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ark.investment.model.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
		@NotNull Long investorId,
		@NotNull Long fundId,
		@NotNull LocalDate transactionDate,
		@NotNull @DecimalMin("0.01") @Digits(integer = 15, fraction = 2) BigDecimal amount,
		@NotNull TransactionType type) {
}