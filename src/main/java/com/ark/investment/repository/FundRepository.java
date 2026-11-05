package com.ark.investment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ark.investment.model.Fund;

public interface FundRepository extends JpaRepository<Fund, Long> {
}