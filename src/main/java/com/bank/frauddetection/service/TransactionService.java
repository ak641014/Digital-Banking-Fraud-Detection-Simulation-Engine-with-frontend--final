package com.bank.frauddetection.service;

import com.bank.frauddetection.model.Transaction;
import com.bank.frauddetection.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> getFraudTransactions() {
        return transactionRepository.findByStatus("FRAUD");
    }

    // ===== NEW METHODS =====

    public List<Transaction> getSuspiciousTransactions() {
        return transactionRepository.findByStatus("SUSPICIOUS");
    }

    public List<Transaction> getNormalTransactions() {
        return transactionRepository.findByStatus("NORMAL");
    }


}