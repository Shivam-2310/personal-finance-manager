package com.shivam.personal_finance_manager.controller;

import com.shivam.personal_finance_manager.entity.Transaction;
import com.shivam.personal_finance_manager.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Transaction> addTransaction(
            @PathVariable Long userId,
            @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.addTransaction(transaction, userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getTransactionsByUser(userId));
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long transactionId,
            @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.updateTransaction(transactionId, transaction));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}
