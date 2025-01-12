package com.shivam.personal_finance_manager.service;

import com.shivam.personal_finance_manager.entity.Transaction;
import com.shivam.personal_finance_manager.repository.TransactionRepository;
import com.shivam.personal_finance_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final SavingsGoalService savingsGoalService;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, SavingsGoalService savingsGoalService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.savingsGoalService = savingsGoalService;
    }

    public Transaction addTransaction(Transaction transaction, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
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

        transaction.setAmount(updatedTransaction.getAmount());
        transaction.setDate(updatedTransaction.getDate());
        transaction.setCategory(updatedTransaction.getCategory());
        transaction.setDescription(updatedTransaction.getDescription());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
