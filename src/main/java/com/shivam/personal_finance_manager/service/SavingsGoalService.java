package com.shivam.personal_finance_manager.service;

import com.shivam.personal_finance_manager.dto.SavingsGoalDTO;
import com.shivam.personal_finance_manager.entity.SavingsGoal;
import com.shivam.personal_finance_manager.entity.Transaction;
import com.shivam.personal_finance_manager.entity.User;
import com.shivam.personal_finance_manager.repository.SavingsGoalRepository;
import com.shivam.personal_finance_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingsGoalService {

    private final SavingsGoalRepository savingsGoalRepository;
    private final UserRepository userRepository;

    public SavingsGoalService(SavingsGoalRepository savingsGoalRepository, UserRepository userRepository) {
        this.savingsGoalRepository = savingsGoalRepository;
        this.userRepository = userRepository;
    }

    public SavingsGoalDTO createSavingsGoal(SavingsGoalDTO savingsGoalDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        SavingsGoal savingsGoal = convertToEntity(savingsGoalDTO);
        savingsGoal.setUser(user);
        savingsGoal.setCurrentAmount(BigDecimal.ZERO);
        SavingsGoal savedGoal = savingsGoalRepository.save(savingsGoal);
        return convertToDTO(savedGoal);
    }

    public List<SavingsGoalDTO> getSavingsGoalsByUser(Long userId) {
        return savingsGoalRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SavingsGoalDTO updateSavingsGoal(Long goalId, SavingsGoalDTO updatedGoalDTO) {
        SavingsGoal existingGoal = savingsGoalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Savings goal not found"));

        existingGoal.setName(updatedGoalDTO.getName());
        existingGoal.setTargetAmount(updatedGoalDTO.getTargetAmount());
        existingGoal.setTargetDate(updatedGoalDTO.getTargetDate());

        SavingsGoal savedGoal = savingsGoalRepository.save(existingGoal);
        return convertToDTO(savedGoal);
    }

    public void deleteSavingsGoal(Long goalId) {
        savingsGoalRepository.deleteById(goalId);
    }

    public void updateGoalProgress(Transaction transaction) {
        List<SavingsGoal> userGoals = savingsGoalRepository.findByUserId(transaction.getUser().getId());
        for (SavingsGoal goal : userGoals) {
            if (transaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                goal.setCurrentAmount(goal.getCurrentAmount().add(transaction.getAmount()));
                savingsGoalRepository.save(goal);
            }
        }
    }

    private SavingsGoalDTO convertToDTO(SavingsGoal savingsGoal) {
        SavingsGoalDTO dto = new SavingsGoalDTO();
        dto.setId(savingsGoal.getId());
        dto.setName(savingsGoal.getName());
        dto.setTargetAmount(savingsGoal.getTargetAmount());
        dto.setTargetDate(savingsGoal.getTargetDate());
        dto.setCurrentAmount(savingsGoal.getCurrentAmount());
        return dto;
    }

    private SavingsGoal convertToEntity(SavingsGoalDTO dto) {
        SavingsGoal entity = new SavingsGoal();
        entity.setName(dto.getName());
        entity.setTargetAmount(dto.getTargetAmount());
        entity.setTargetDate(dto.getTargetDate());
        return entity;
    }
}
