package com.shivam.personal_finance_manager.service;

import com.shivam.personal_finance_manager.entity.Transaction;
import com.shivam.personal_finance_manager.entity.TransactionType;
import com.shivam.personal_finance_manager.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingService {
    private final TransactionRepository transactionRepository;

    public ReportingService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Map<String, Object> generateMonthlyReport(Long userId, int year, int month) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        List<Transaction> monthlyTransactions = transactions.stream()
                .filter(t -> t.getDate().getYear() == year && t.getDate().getMonthValue() == month)
                .collect(Collectors.toList());

        Map<String, Object> report = new HashMap<>();

        // Calculate total income and expenses
        BigDecimal totalIncome = calculateTotalByType(monthlyTransactions, TransactionType.CREDIT);
        BigDecimal totalExpenses = calculateTotalByType(monthlyTransactions, TransactionType.DEBIT);

        // Category-wise breakdown
        Map<String, BigDecimal> categoryBreakdown = monthlyTransactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Transaction::getAmount,
                                BigDecimal::add
                        )
                ));

        // Daily spending trend
        Map<LocalDate, BigDecimal> dailySpending = monthlyTransactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Transaction::getAmount,
                                BigDecimal::add
                        )
                ));

        report.put("totalIncome", totalIncome);
        report.put("totalExpenses", totalExpenses);
        report.put("netSavings", totalIncome.subtract(totalExpenses));
        report.put("categoryBreakdown", categoryBreakdown);
        report.put("dailySpending", dailySpending);

        return report;
    }

    public Map<String, Object> generateYearlyReport(Long userId, int year) {
        // Similar to monthly report but aggregated by month
        // Implementation details...
        return null; // TODO: Implement
    }

    private BigDecimal calculateTotalByType(List<Transaction> transactions, TransactionType type) {
        return transactions.stream()
                .filter(t -> t.getType() == type)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

