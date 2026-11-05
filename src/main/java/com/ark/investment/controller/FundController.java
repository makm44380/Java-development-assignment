package com.ark.investment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ark.investment.model.Fund;
import com.ark.investment.service.InvestmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/funds")
public class FundController {
	private final InvestmentService investmentService;

	public FundController(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}

	@GetMapping
	public List<Fund> getFunds() {
		return investmentService.getFunds();
	}

	@GetMapping("/{id}")
	public Fund getFund(@PathVariable Long id) {
		return investmentService.getFund(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Fund createFund(@Valid @RequestBody Fund fund) {
		return investmentService.createFund(fund);
	}

	@PutMapping("/{id}")
	public Fund updateFund(@PathVariable Long id, @Valid @RequestBody Fund fund) {
		return investmentService.updateFund(id, fund);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFund(@PathVariable Long id) {
		investmentService.deleteFund(id);
	}
}