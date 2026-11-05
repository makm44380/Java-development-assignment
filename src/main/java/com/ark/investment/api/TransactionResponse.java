package com.ark.investment.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ark.investment.model.InvestmentTransaction;
import com.ark.investment.model.TransactionType;

public record TransactionResponse(
		Long id,
		Long investorId,
		String investorName,
		Long fundId,
		String fundName,
		LocalDate transactionDate,
		BigDecimal amount,
		TransactionType type) {
	public static TransactionResponse from(InvestmentTransaction transaction) {
		return new TransactionResponse(
				transaction.getId(),
				transaction.getInvestor().getId(),
				transaction.getInvestor().getFullName(),
				transaction.getFund().getId(),
				transaction.getFund().getName(),
				transaction.getTransactionDate(),
				transaction.getAmount(),
				transaction.getType());
	}
}