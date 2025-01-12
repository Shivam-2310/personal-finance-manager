package com.shivam.personal_finance_manager.controller;

import com.shivam.personal_finance_manager.dto.SavingsGoalDTO;
import com.shivam.personal_finance_manager.service.SavingsGoalService;
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
            @RequestBody SavingsGoalDTO savingsGoalDTO) {
        return ResponseEntity.ok(savingsGoalService.createSavingsGoal(savingsGoalDTO, userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SavingsGoalDTO>> getSavingsGoals(@PathVariable Long userId) {
        return ResponseEntity.ok(savingsGoalService.getSavingsGoalsByUser(userId));
    }

    @PutMapping("/{goalId}")
    public ResponseEntity<SavingsGoalDTO> updateSavingsGoal(
            @PathVariable Long goalId,
            @RequestBody SavingsGoalDTO savingsGoalDTO) {
        return ResponseEntity.ok(savingsGoalService.updateSavingsGoal(goalId, savingsGoalDTO));
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteSavingsGoal(@PathVariable Long goalId) {
        savingsGoalService.deleteSavingsGoal(goalId);
        return ResponseEntity.noContent().build();
    }
}
