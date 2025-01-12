package com.shivam.personal_finance_manager.service;

import com.shivam.personal_finance_manager.entity.Transaction;
import com.shivam.personal_finance_manager.entity.TransactionType;
import com.shivam.personal_finance_manager.repository.TransactionRepository;
import com.shivam.personal_finance_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final SavingsGoalService savingsGoalService;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              SavingsGoalService savingsGoalService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.savingsGoalService = savingsGoalService;
    }

    public Transaction addTransaction(Transaction transaction, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }

        transaction.setUser(user);
        Transaction savedTransaction = transactionRepository.save(transaction);

        savingsGoalService.updateGoalProgress(savedTransaction);

        return savedTransaction;
    }

    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Transaction updateTransaction(Long transactionId, Transaction updatedTransaction) {
        var transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        if (updatedTransaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }

        transaction.setAmount(updatedTransaction.getAmount());
        transaction.setType(updatedTransaction.getType());
        transaction.setDate(updatedTransaction.getDate());
        transaction.setCategory(updatedTransaction.getCategory());
        transaction.setDescription(updatedTransaction.getDescription());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public BigDecimal getCurrentBalance(Long userId) {
        List<Transaction> transactions = getTransactionsByUser(userId);
        return transactions.stream()
                .map(Transaction::getSignedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
