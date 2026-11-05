package com.ark.investment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ark.investment.api.FundReport;
import com.ark.investment.api.InvestorReport;
import com.ark.investment.service.InvestmentService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
	private final InvestmentService investmentService;

	public ReportController(InvestmentService investmentService) {
		this.investmentService = investmentService;
	}

	@GetMapping("/funds")
	public List<FundReport> getFundReports() {
		return investmentService.getFundReports();
	}

	@GetMapping("/investors")
	public List<InvestorReport> getInvestorReports() {
		return investmentService.getInvestorReports();
	}
}