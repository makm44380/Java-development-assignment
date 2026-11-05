package com.ark.investment.model;

public enum TransactionType {
	CONTRIBUTION,
	INTEREST_INCOME,
	DISTRIBUTION,
	GENERAL_EXPENSE,
	MANAGEMENT_FEE;

	public boolean addsToBalance() {
		return this == CONTRIBUTION || this == INTEREST_INCOME;
	}
}