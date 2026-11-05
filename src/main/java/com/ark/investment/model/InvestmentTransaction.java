package com.ark.investment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "investment_transactions")
public class InvestmentTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "investor_id", nullable = false)
	private Investor investor;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "fund_id", nullable = false)
	private Fund fund;

	@NotNull
	@Column(nullable = false)
	private LocalDate transactionDate;

	@NotNull
	@DecimalMin("0.01")
	@Digits(integer = 15, fraction = 2)
	@Column(nullable = false, precision = 17, scale = 2)
	private BigDecimal amount;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionType type;

	protected InvestmentTransaction() {
	}

	public InvestmentTransaction(Investor investor, Fund fund, LocalDate transactionDate, BigDecimal amount,
			TransactionType type) {
		this.investor = investor;
		this.fund = fund;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public Investor getInvestor() {
		return investor;
	}

	public Fund getFund() {
		return fund;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public TransactionType getType() {
		return type;
	}
}