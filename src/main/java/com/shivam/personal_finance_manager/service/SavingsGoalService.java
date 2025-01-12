package com.shivam.personal_finance_manager.service;

import com.shivam.personal_finance_manager.dto.SavingsGoalDTO;
import com.shivam.personal_finance_manager.entity.SavingsGoal;
import com.shivam.personal_finance_manager.entity.Transaction;
import com.shivam.personal_finance_manager.entity.TransactionType;
import com.shivam.personal_finance_manager.entity.User;
import com.shivam.personal_finance_manager.exception.ResourceNotFoundException;
import com.shivam.personal_finance_manager.repository.SavingsGoalRepository;
import com.shivam.personal_finance_manager.repository.TransactionRepository;
import com.shivam.personal_finance_manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SavingsGoalService {
    private final SavingsGoalRepository savingsGoalRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public SavingsGoalService(SavingsGoalRepository savingsGoalRepository,
                              UserRepository userRepository,
                              TransactionRepository transactionRepository) {
        this.savingsGoalRepository = savingsGoalRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    private SavingsGoalDTO convertToDTO(SavingsGoal savingsGoal) {
        SavingsGoalDTO dto = new SavingsGoalDTO();
        dto.setId(savingsGoal.getId());
        dto.setName(savingsGoal.getName());
        dto.setTargetAmount(savingsGoal.getTargetAmount());
        dto.setTargetDate(savingsGoal.getTargetDate());
        dto.setCurrentAmount(savingsGoal.getCurrentAmount());
        dto.setMonthlyTarget(savingsGoal.getMonthlyTarget());
        dto.setStartDate(savingsGoal.getStartDate());
        dto.setTrackAllSavings(savingsGoal.isTrackAllSavings());
        dto.setTrackedCategories(savingsGoal.getTrackedCategories());
        return dto;
    }

    public SavingsGoalDTO createSavingsGoal(SavingsGoalDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SavingsGoal goal = new SavingsGoal();
        goal.setName(dto.getName());
        goal.setTargetAmount(dto.getTargetAmount());
        goal.setTargetDate(dto.getTargetDate());
        goal.setStartDate(LocalDate.now());
        goal.setUser(user);
        goal.setTrackAllSavings(dto.isTrackAllSavings());
        goal.setTrackedCategories(dto.getTrackedCategories());

        long totalMonths = ChronoUnit.MONTHS.between(goal.getStartDate(), goal.getTargetDate());
        if (totalMonths <= 0) {
            throw new IllegalArgumentException("Target date must be in the future");
        }
        goal.setMonthlyTarget(goal.getTargetAmount().divide(BigDecimal.valueOf(totalMonths), 2, RoundingMode.HALF_UP));

        SavingsGoal savedGoal = savingsGoalRepository.save(goal);
        return convertToDTO(savedGoal);
    }

    public void updateGoalProgress(Transaction transaction) {
        List<SavingsGoal> userGoals = savingsGoalRepository.findByUserId(transaction.getUser().getId());

        for (SavingsGoal goal : userGoals) {
            if (shouldCountTransactionForGoal(transaction, goal)) {
                BigDecimal signedAmount = transaction.getType() == TransactionType.CREDIT ?
                        transaction.getAmount() :
                        transaction.getAmount().negate();
                updateGoalAmount(goal, signedAmount);
            }
        }
    }

    private boolean shouldCountTransactionForGoal(Transaction transaction, SavingsGoal goal) {
        if (goal.isTrackAllSavings()) {
            return true;
        }
        return goal.getTrackedCategories().contains(transaction.getCategory());
    }


    private void updateGoalAmount(SavingsGoal goal, BigDecimal amount) {
        goal.setCurrentAmount(goal.getCurrentAmount().add(amount));
        if (goal.getCurrentAmount().compareTo(BigDecimal.ZERO) < 0) {
            goal.setCurrentAmount(BigDecimal.ZERO);
        }
        savingsGoalRepository.save(goal);
    }


    public SavingsGoalDTO getGoalProgress(Long goalId) {
        SavingsGoal goal = savingsGoalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Savings goal not found"));

        SavingsGoalDTO dto = convertToDTO(goal);

        double progressPercentage = goal.getCurrentAmount()
                .divide(goal.getTargetAmount(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
        dto.setProgressPercentage(progressPercentage);

        long monthsElapsed = ChronoUnit.MONTHS.between(goal.getStartDate(), LocalDate.now());
        BigDecimal expectedProgress = goal.getMonthlyTarget().multiply(BigDecimal.valueOf(monthsElapsed));
        dto.setOnTrack(goal.getCurrentAmount().compareTo(expectedProgress) >= 0);

        return dto;
    }

    public List<SavingsGoalDTO> getSavingsGoalsByUser(Long userId) {
        return savingsGoalRepository.findByUserId(userId).stream()
                .map(goal -> getGoalProgress(goal.getId()))
                .collect(Collectors.toList());
    }
}