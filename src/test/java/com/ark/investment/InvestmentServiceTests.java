package com.ark.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ark.investment.api.TransactionRequest;
import com.ark.investment.model.Fund;
import com.ark.investment.model.Investor;
import com.ark.investment.model.TransactionType;
import com.ark.investment.service.InvestmentService;

@SpringBootTest
@Transactional
class InvestmentServiceTests {
	@Autowired
	private InvestmentService investmentService;

	@Test
	void reportsAddIncomeAndSubtractOutflows() {
		Fund fund = investmentService.createFund(new Fund("Test fund", "Test description"));
		Investor investor = investmentService.createInvestor(new Investor("Taylor Example", "taylor@example.com"));

		investmentService.createTransaction(transaction(investor, fund, "100.00", TransactionType.CONTRIBUTION));
		investmentService.createTransaction(transaction(investor, fund, "10.00", TransactionType.INTEREST_INCOME));
		investmentService.createTransaction(transaction(investor, fund, "5.00", TransactionType.DISTRIBUTION));
		investmentService.createTransaction(transaction(investor, fund, "2.00", TransactionType.GENERAL_EXPENSE));
		investmentService.createTransaction(transaction(investor, fund, "1.00", TransactionType.MANAGEMENT_FEE));

		assertEquals(new BigDecimal("102.00"), investmentService.getFundReports().get(0).balance());
		assertEquals(new BigDecimal("102.00"), investmentService.getInvestorReports().get(0).balance());
	}

	private TransactionRequest transaction(Investor investor, Fund fund, String amount, TransactionType type) {
		return new TransactionRequest(investor.getId(), fund.getId(), LocalDate.of(2026, 1, 15),
				new BigDecimal(amount), type);
	}
}