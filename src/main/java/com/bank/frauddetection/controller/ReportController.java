package com.bank.frauddetection.controller;

import com.bank.frauddetection.service.ExcelReportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class ReportController {

    @Autowired
    private ExcelReportService excelReportService;

    @GetMapping("/download-fraud-report")
    public void downloadFraudReport(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment; filename=fraud_report.xlsx");

        excelReportService.generateFraudReport(response);  // Make sure method name same
    }
}