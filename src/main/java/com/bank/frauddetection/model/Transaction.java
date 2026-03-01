package com.bank.frauddetection.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ ACCOUNT NUMBER VALIDATION
    @NotBlank(message = "Account number is required")
    @Size(min = 8, max = 8, message = "Account number must be exactly 8 characters")
    @Pattern(
            regexp = "^[A-Z]{4}[0-9A-F]{4}$",
            message = "Account number must be 4 letters followed by 4 hexadecimal characters (Example: ABCD1A2F)"
    )
    @Column(nullable = false, length = 8)
    private String accountNumber;

    // ✅ AMOUNT VALIDATION
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    @Column(nullable = false)
    private Double amount;

    // ✅ LOCATION VALIDATION
    @NotBlank(message = "Location is required")
    private String location;

    // Auto time
    private LocalDateTime transactionTime;

    private String status;

    // ✅ RISK SCORE VALIDATION
    @NotNull(message = "Risk score is required")
    @Min(value = 0, message = "Risk score must be at least 0")
    @Max(value = 100, message = "Risk score must not exceed 100")
    private Integer riskScore;

    // Automatically set time before saving
    @PrePersist
    public void prePersist() {
        if (this.transactionTime == null) {
            this.transactionTime = LocalDateTime.now();
        }
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        if (accountNumber != null) {
            this.accountNumber = accountNumber.toUpperCase(); // 🔥 auto uppercase
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }
}