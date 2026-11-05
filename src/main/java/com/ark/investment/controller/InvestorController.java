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

import com.ark.investment.model.Investor;
import com.ark.investment.service.InvestmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/investors")
public class InvestorController {
	private final InvestmentService investmentService;

	public InvestorController(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}

	@GetMapping
	public List<Investor> getInvestors() {
		return investmentService.getInvestors();
	}

	@GetMapping("/{id}")
	public Investor getInvestor(@PathVariable Long id) {
		return investmentService.getInvestor(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Investor createInvestor(@Valid @RequestBody Investor investor) {
		return investmentService.createInvestor(investor);
	}

	@PutMapping("/{id}")
	public Investor updateInvestor(@PathVariable Long id, @Valid @RequestBody Investor investor) {
		return investmentService.updateInvestor(id, investor);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteInvestor(@PathVariable Long id) {
		investmentService.deleteInvestor(id);
	}
}