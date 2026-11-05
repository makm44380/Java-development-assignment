package com.ark.investment.api;

import java.math.BigDecimal;

public record InvestorReport(Long investorId, String fullName, String email, long transactionCount,
		BigDecimal balance) {
}