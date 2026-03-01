package com.bank.frauddetection.service;

import com.bank.frauddetection.model.Transaction;
import com.bank.frauddetection.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.List;


@Service
public class AnalyticsService {

    @Autowired
    private TransactionRepository transactionRepository;

    public String exportTrainingData() {

        List<Transaction> txs = transactionRepository.findAll();
        String filePath = "fraud-training-data.csv";

        try (FileWriter writer = new FileWriter(filePath)) {

            writer.append("amount,location,riskScore,status\n");

            for (Transaction tx : txs) {
                writer.append(String.valueOf(tx.getAmount())).append(",")
                        .append(tx.getLocation()).append(",")
                        .append(String.valueOf(tx.getRiskScore())).append(",")
                        .append(tx.getStatus()).append("\n");
            }

        } catch (Exception e) {
            throw new RuntimeException("CSV Export Failed", e);
        }

        return filePath;
    }
}