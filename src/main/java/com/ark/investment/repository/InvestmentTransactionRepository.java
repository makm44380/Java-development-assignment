package com.ark.investment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ark.investment.model.InvestmentTransaction;

public interface InvestmentTransactionRepository extends JpaRepository<InvestmentTransaction, Long> {
	boolean existsByFundId(Long fundId);

	boolean existsByInvestorId(Long investorId);
}