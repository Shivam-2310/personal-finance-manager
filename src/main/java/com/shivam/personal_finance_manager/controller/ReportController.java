package com.shivam.personal_finance_manager.controller;

import com.shivam.personal_finance_manager.service.ChartService;
import com.shivam.personal_finance_manager.service.ReportingService;
import org.jfree.chart.JFreeChart;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportingService reportingService;
    private final ChartService chartService;

    public ReportController(ReportingService reportingService, ChartService chartService) {
        this.reportingService = reportingService;
        this.chartService = chartService;
    }

    @GetMapping("/monthly/{userId}/charts/spending-category")
    public ResponseEntity<byte[]> getSpendingCategoryChart(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) throws IOException {

        Map<String, Object> report = reportingService.generateMonthlyReport(userId, year, month);
        @SuppressWarnings("unchecked")
        Map<String, BigDecimal> categoryBreakdown =
                (Map<String, BigDecimal>) report.get("categoryBreakdown");

        JFreeChart chart = chartService.createSpendingCategoryPieChart(categoryBreakdown);
        byte[] imageBytes = chartService.generateChartImage(chart, 600, 400);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/monthly/{userId}/charts/spending-trend")
    public ResponseEntity<byte[]> getSpendingTrendChart(
            @PathVariable Long userId,
            @RequestParam int year,
            @RequestParam int month) throws IOException {

        Map<String, Object> report = reportingService.generateMonthlyReport(userId, year, month);
        @SuppressWarnings("unchecked")
        Map<LocalDate, BigDecimal> dailySpending =
                (Map<LocalDate, BigDecimal>) report.get("dailySpending");

        JFreeChart chart = chartService.createMonthlySpendingBarChart(dailySpending);
        byte[] imageBytes = chartService.generateChartImage(chart, 800, 400);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
