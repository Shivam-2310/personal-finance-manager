package com.shivam.personal_finance_manager.controller;

import com.shivam.personal_finance_manager.service.ReportingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportingService reportingService;

    public ReportController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/monthly/{userId}")
    public ResponseEntity<Map<String, Object>> getMonthlyReport(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(reportingService.generateMonthlyReport(userId, year, month));
    }

    @GetMapping("/yearly/{userId}")
    public ResponseEntity<Map<String, Object>> getYearlyReport(
            @PathVariable Long userId,
            @RequestParam int year) {
        return ResponseEntity.ok(reportingService.generateYearlyReport(userId, year));
    }
}
