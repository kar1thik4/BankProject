package com.hcl.bank.service;

import java.util.List;

import com.hcl.bank.model.Transaction;

public interface TransactionServIntf {

	public boolean save(Transaction transaction);

	public List<Transaction> findById(int userId);

	public List<?> findReportById(int userId);

}
