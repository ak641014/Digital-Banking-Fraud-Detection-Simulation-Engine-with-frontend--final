package com.bank.frauddetection.controller;

import com.bank.frauddetection.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/export")
    public ResponseEntity<Resource> exportTrainingData() {

        File file = new File(analyticsService.exportTrainingData());
        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=fraud-training-data.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }
}
