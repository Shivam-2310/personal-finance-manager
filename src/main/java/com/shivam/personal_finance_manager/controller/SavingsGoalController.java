package com.shivam.personal_finance_manager.controller;

import com.shivam.personal_finance_manager.dto.SavingsGoalDTO;
import com.shivam.personal_finance_manager.service.SavingsGoalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-goals")
public class SavingsGoalController {

    private final SavingsGoalService savingsGoalService;

    public SavingsGoalController(SavingsGoalService savingsGoalService) {
        this.savingsGoalService = savingsGoalService;
    }


    @PostMapping("/{userId}")
    public ResponseEntity<SavingsGoalDTO> createSavingsGoal(
            @PathVariable Long userId,
            @Valid @RequestBody SavingsGoalDTO savingsGoalDTO) {
        return ResponseEntity.ok(savingsGoalService.createSavingsGoal(savingsGoalDTO, userId));
    }

    @GetMapping("/{goalId}/progress")
    public ResponseEntity<SavingsGoalDTO> getGoalProgress(@PathVariable Long goalId) {
        return ResponseEntity.ok(savingsGoalService.getGoalProgress(goalId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavingsGoalDTO>> getSavingsGoals(@PathVariable Long userId) {
        return ResponseEntity.ok(savingsGoalService.getSavingsGoalsByUser(userId));
    }
}