package za.co.carhire.dto.vehicle;

import java.io.Serializable;

/**
 * Data Transfer Object for PricingRule entity
 * Author: Claude Code
 * Date: 2025-10-15
 */
public class PricingRuleDTO implements Serializable {

    private int pricingRuleID;
    private Integer carTypeID;
    private String carTypeName;
    private double baseDailyRate;
    private double weeklyRate;
    private double monthlyRate;
    private double weekendRate;
    private double seasonalMultiplier;
    private String validFrom;
    private String validTo;
    private boolean active;

    public PricingRuleDTO() {
    }

    public int getPricingRuleID() {
        return pricingRuleID;
    }

    public void setPricingRuleID(int pricingRuleID) {
        this.pricingRuleID = pricingRuleID;
    }

    public Integer getCarTypeID() {
        return carTypeID;
    }

    public void setCarTypeID(Integer carTypeID) {
        this.carTypeID = carTypeID;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public double getBaseDailyRate() {
        return baseDailyRate;
    }

    public void setBaseDailyRate(double baseDailyRate) {
        this.baseDailyRate = baseDailyRate;
    }

    public double getWeeklyRate() {
        return weeklyRate;
    }

    public void setWeeklyRate(double weeklyRate) {
        this.weeklyRate = weeklyRate;
    }

    public double getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(double monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public double getWeekendRate() {
        return weekendRate;
    }

    public void setWeekendRate(double weekendRate) {
        this.weekendRate = weekendRate;
    }

    public double getSeasonalMultiplier() {
        return seasonalMultiplier;
    }

    public void setSeasonalMultiplier(double seasonalMultiplier) {
        this.seasonalMultiplier = seasonalMultiplier;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "PricingRuleDTO{" +
                "pricingRuleID=" + pricingRuleID +
                ", carTypeID=" + carTypeID +
                ", carTypeName='" + carTypeName + '\'' +
                ", baseDailyRate=" + baseDailyRate +
                ", weeklyRate=" + weeklyRate +
                ", monthlyRate=" + monthlyRate +
                ", weekendRate=" + weekendRate +
                ", seasonalMultiplier=" + seasonalMultiplier +
                ", validFrom='" + validFrom + '\'' +
                ", validTo='" + validTo + '\'' +
                ", active=" + active +
                '}';
    }
}
