package com.ark.investment.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ark.investment.api.FundReport;
import com.ark.investment.api.InvestorReport;
import com.ark.investment.api.TransactionRequest;
import com.ark.investment.api.TransactionResponse;
import com.ark.investment.model.Fund;
import com.ark.investment.model.InvestmentTransaction;
import com.ark.investment.model.Investor;
import com.ark.investment.repository.FundRepository;
import com.ark.investment.repository.InvestmentTransactionRepository;
import com.ark.investment.repository.InvestorRepository;

@Service
public class InvestmentService {
	private final FundRepository fundRepository;
	private final InvestorRepository investorRepository;
	private final InvestmentTransactionRepository transactionRepository;

	public InvestmentService(FundRepository fundRepository, InvestorRepository investorRepository,
			InvestmentTransactionRepository transactionRepository) {
		this.fundRepository = fundRepository;
		this.investorRepository = investorRepository;
		this.transactionRepository = transactionRepository;
	}

	@Transactional(readOnly = true)
	public List<Fund> getFunds() {
		return fundRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Fund getFund(Long id) {
		return fundRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fund not found"));
	}

	@Transactional
	public Fund createFund(Fund fund) {
		fund.setId(null);
		return fundRepository.save(fund);
	}

	@Transactional
	public Fund updateFund(Long id, Fund updatedFund) {
		Fund fund = getFund(id);
		fund.setName(updatedFund.getName());
		fund.setDescription(updatedFund.getDescription());
		return fundRepository.save(fund);
	}

	@Transactional
	public void deleteFund(Long id) {
		Fund fund = getFund(id);
		if (transactionRepository.existsByFundId(id)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Fund has transactions and cannot be deleted");
		}
		fundRepository.delete(fund);
	}

	@Transactional(readOnly = true)
	public List<Investor> getInvestors() {
		return investorRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Investor getInvestor(Long id) {
		return investorRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Investor not found"));
	}

	@Transactional
	public Investor createInvestor(Investor investor) {
		investor.setId(null);
		return investorRepository.save(investor);
	}

	@Transactional
	public Investor updateInvestor(Long id, Investor updatedInvestor) {
		Investor investor = getInvestor(id);
		investor.setFullName(updatedInvestor.getFullName());
		investor.setEmail(updatedInvestor.getEmail());
		return investorRepository.save(investor);
	}

	@Transactional
	public void deleteInvestor(Long id) {
		Investor investor = getInvestor(id);
		if (transactionRepository.existsByInvestorId(id)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Investor has transactions and cannot be deleted");
		}
		investorRepository.delete(investor);
	}

	@Transactional(readOnly = true)
	public List<TransactionResponse> getTransactions() {
		return transactionRepository.findAll().stream().map(TransactionResponse::from).toList();
	}

	@Transactional(readOnly = true)
	public TransactionResponse getTransaction(Long id) {
		InvestmentTransaction transaction = transactionRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
		return TransactionResponse.from(transaction);
	}

	@Transactional
	public TransactionResponse createTransaction(TransactionRequest request) {
		Investor investor = getInvestor(request.investorId());
		Fund fund = getFund(request.fundId());
		InvestmentTransaction transaction = new InvestmentTransaction(investor, fund, request.transactionDate(),
				request.amount(), request.type());
		return TransactionResponse.from(transactionRepository.save(transaction));
	}

	@Transactional(readOnly = true)
	public List<FundReport> getFundReports() {
		List<InvestmentTransaction> transactions = transactionRepository.findAll();
		return fundRepository.findAll().stream().map(fund -> {
			List<InvestmentTransaction> fundTransactions = transactions.stream()
					.filter(transaction -> transaction.getFund().getId().equals(fund.getId())).toList();
			return new FundReport(fund.getId(), fund.getName(), fund.getDescription(), fundTransactions.size(),
					calculateBalance(fundTransactions));
		}).toList();
	}

	@Transactional(readOnly = true)
	public List<InvestorReport> getInvestorReports() {
		List<InvestmentTransaction> transactions = transactionRepository.findAll();
		return investorRepository.findAll().stream().map(investor -> {
			List<InvestmentTransaction> investorTransactions = transactions.stream()
					.filter(transaction -> transaction.getInvestor().getId().equals(investor.getId())).toList();
			return new InvestorReport(investor.getId(), investor.getFullName(), investor.getEmail(),
					investorTransactions.size(), calculateBalance(investorTransactions));
		}).toList();
	}

	private BigDecimal calculateBalance(List<InvestmentTransaction> transactions) {
		return transactions.stream().map(transaction -> transaction.getType().addsToBalance()
				? transaction.getAmount()
				: transaction.getAmount().negate()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}