package com.ark.investment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ark.investment.api.TransactionRequest;
import com.ark.investment.api.TransactionResponse;
import com.ark.investment.service.InvestmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	private final InvestmentService investmentService;

	public TransactionController(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}

	@GetMapping
	public List<TransactionResponse> getTransactions() {
		return investmentService.getTransactions();
	}

	@GetMapping("/{id}")
	public TransactionResponse getTransaction(@PathVariable Long id) {
		return investmentService.getTransaction(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionResponse createTransaction(@Valid @RequestBody TransactionRequest request) {
		return investmentService.createTransaction(request);
	}
}