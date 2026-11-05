package com.ark.investment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ark.investment.model.Investor;

public interface InvestorRepository extends JpaRepository<Investor, Long> {
}