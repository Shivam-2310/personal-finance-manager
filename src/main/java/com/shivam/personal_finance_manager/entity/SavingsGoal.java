package com.shivam.personal_finance_manager.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "savings_goals")
public class SavingsGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal targetAmount;

    @Column(nullable = false)
    private LocalDate targetDate;

    @Column(nullable = false)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal monthlyTarget;

    @Column(nullable = false)
    private LocalDate startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean trackAllSavings = true;

    @ElementCollection
    private Set<String> trackedCategories = new HashSet<>();

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}