package com.ark.investment.api;

import java.math.BigDecimal;

public record FundReport(Long fundId, String name, String description, long transactionCount, BigDecimal balance) {
}