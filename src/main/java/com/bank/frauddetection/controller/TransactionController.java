package com.bank.frauddetection.controller;

import com.bank.frauddetection.model.Transaction;
import com.bank.frauddetection.service.TransactionService;
import com.bank.frauddetection.service.TransactionSimulationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionSimulationService simulationService;

    @Autowired
    private TransactionService service;

    @PostMapping("/save")
    public String saveTransaction(@Valid @ModelAttribute Transaction transaction,
                                  BindingResult result) {

        if (result.hasErrors()) {
            return "transactions";
        }

        service.createTransaction(transaction);  // CORRECT METHOD

        return "redirect:/dashboard";
    }
}