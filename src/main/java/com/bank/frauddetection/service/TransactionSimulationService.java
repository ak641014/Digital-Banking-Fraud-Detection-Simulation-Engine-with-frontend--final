package com.bank.frauddetection.service;

import com.bank.frauddetection.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TransactionSimulationService {

    private static final String[] LOCATIONS = {
            "Delhi", "Mumbai", "London", "New York", "Dubai", "Singapore"
    };

    private static final String HEX = "0123456789ABCDEF";
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public List<Transaction> generateTransactions(int count) {

        List<Transaction> transactions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {

            Transaction tx = new Transaction();

            // ✅ Generate valid account number (ABCD1A2F format)
            tx.setAccountNumber(generateValidAccount(random));

            tx.setLocation(LOCATIONS[random.nextInt(LOCATIONS.length)]);

            double amount;
            int risk;

            if (random.nextBoolean()) {
                amount = 80000 + random.nextInt(50000);
                risk = 80 + random.nextInt(20); // FRAUD
            } else {
                amount = 500 + random.nextInt(5000);
                risk = random.nextInt(40); // NORMAL
            }

            tx.setAmount(amount);
            tx.setRiskScore(risk);

            transactions.add(tx);
        }

        return transactions;
    }

    private String generateValidAccount(Random random) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            sb.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }

        for (int i = 0; i < 4; i++) {
            sb.append(HEX.charAt(random.nextInt(HEX.length())));
        }

        return sb.toString();
    }
}