package com.shivam.personal_finance_manager.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class SavingsGoalDTO {
    private Long id;
    private String name;
    private BigDecimal targetAmount;
    private LocalDate targetDate;
    private BigDecimal currentAmount;
    private BigDecimal monthlyTarget;
    private LocalDate startDate;
    private boolean trackAllSavings;
    private Set<String> trackedCategories;
    private double progressPercentage;
    private boolean isOnTrack;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public BigDecimal getMonthlyTarget() {
        return monthlyTarget;
    }

    public void setMonthlyTarget(BigDecimal monthlyTarget) {
        this.monthlyTarget = monthlyTarget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isTrackAllSavings() {
        return trackAllSavings;
    }

    public void setTrackAllSavings(boolean trackAllSavings) {
        this.trackAllSavings = trackAllSavings;
    }

    public Set<String> getTrackedCategories() {
        return trackedCategories;
    }

    public void setTrackedCategories(Set<String> trackedCategories) {
        this.trackedCategories = trackedCategories;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public boolean isOnTrack() {
        return isOnTrack;
    }

    public void setOnTrack(boolean onTrack) {
        isOnTrack = onTrack;
    }
}