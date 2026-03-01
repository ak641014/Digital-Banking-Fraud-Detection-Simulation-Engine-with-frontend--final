package com.bank.frauddetection.controller;

import com.bank.frauddetection.model.Transaction;
import com.bank.frauddetection.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private TransactionService transactionService;

    // ================= DASHBOARD =================
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {

        List<Transaction> transactions = transactionService.getAll();
        List<Transaction> fraudTransactions = transactionService.getFraudTransactions();

        int totalTransactions = transactions.size();
        int fraudCount = fraudTransactions.size();
        int safeCount = totalTransactions - fraudCount;

        model.addAttribute("transactions", transactions);
        model.addAttribute("totalTransactions", totalTransactions);
        model.addAttribute("fraudCount", fraudCount);
        model.addAttribute("safeCount", safeCount);

        // 🔥 ADD THIS
        model.addAttribute("transaction", new Transaction());

        return "dashboard";
    }

    // ================= CREATE TRANSACTION =================
    @PostMapping("/transaction/create")
    public String createTransaction(
            @Valid @ModelAttribute("transaction") Transaction transaction,
            BindingResult result,
            Model model) {

        // Validation errors aaye to dashboard wapas load karo
        if (result.hasErrors()) {

            model.addAttribute("transactions", transactionService.getAll());
            model.addAttribute("totalTransactions", transactionService.getAll().size());
            model.addAttribute("fraudCount", transactionService.getFraudTransactions().size());
            model.addAttribute("safeCount",
                    transactionService.getAll().size() - transactionService.getFraudTransactions().size());

            return "dashboard";
        }

        transaction.setTransactionTime(LocalDateTime.now());

        if (transaction.getRiskScore() >= 70) {
            transaction.setStatus("FRAUD");
        } else if (transaction.getRiskScore() >= 40) {
            transaction.setStatus("SUSPICIOUS");
        } else {
            transaction.setStatus("NORMAL");
        }

        transactionService.createTransaction(transaction);

        return "redirect:/dashboard";
    }

    // ================= REPORTS =================

    @GetMapping("/fraud-report")
    public String fraudReport(Model model) {
        model.addAttribute("transactions",
                transactionService.getFraudTransactions());
        return "fraud-report";
    }

    @GetMapping("/suspicious-report")
    public String suspiciousReport(Model model) {
        model.addAttribute("transactions",
                transactionService.getSuspiciousTransactions());
        return "suspicious-report";
    }

    @GetMapping("/normal-report")
    public String normalReport(Model model) {
        model.addAttribute("transactions",
                transactionService.getNormalTransactions());
        return "normal-report";
    }

    // ================= DELETE =================
    @GetMapping("/transaction/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return "redirect:/dashboard";
    }
}