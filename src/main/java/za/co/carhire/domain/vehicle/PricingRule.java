package za.co.carhire.domain.vehicle;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "pricing_rule")
public class PricingRule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pricing_rule_id")
    private int pricingRuleID;

    @ManyToOne
    @JoinColumn(name = "car_type_id", nullable = false)
    private CarType carType;

    @Column(name = "base_daily_rate", nullable = false)
    private double baseDailyRate;

    @Column(name = "weekly_rate")
    private double weeklyRate;

    @Column(name = "monthly_rate")
    private double monthlyRate;

    @Column(name = "weekend_rate")
    private double weekendRate;

    @Column(name = "seasonal_multiplier")
    private double seasonalMultiplier; // e.g., 1.2 for 20% increase during peak season

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "active")
    private boolean active;

    public PricingRule() {
    }

    private PricingRule(Builder builder) {
        this.pricingRuleID = builder.pricingRuleID;
        this.carType = builder.carType;
        this.baseDailyRate = builder.baseDailyRate;
        this.weeklyRate = builder.weeklyRate;
        this.monthlyRate = builder.monthlyRate;
        this.weekendRate = builder.weekendRate;
        this.seasonalMultiplier = builder.seasonalMultiplier;
        this.validFrom = builder.validFrom;
        this.validTo = builder.validTo;
        this.active = builder.active;
    }

    public int getPricingRuleID() {
        return pricingRuleID;
    }

    public void setPricingRuleID(int pricingRuleID) {
        this.pricingRuleID = pricingRuleID;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
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

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
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
        return "PricingRule{" +
                "pricingRuleID=" + pricingRuleID +
                ", carType=" + (carType != null ? carType.getCarTypeID() : "null") +
                ", baseDailyRate=" + baseDailyRate +
                ", weeklyRate=" + weeklyRate +
                ", monthlyRate=" + monthlyRate +
                ", weekendRate=" + weekendRate +
                ", seasonalMultiplier=" + seasonalMultiplier +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", active=" + active +
                '}';
    }

    public static class Builder {
        private int pricingRuleID;
        private CarType carType;
        private double baseDailyRate;
        private double weeklyRate;
        private double monthlyRate;
        private double weekendRate;
        private double seasonalMultiplier = 1.0;
        private LocalDate validFrom;
        private LocalDate validTo;
        private boolean active = true;

        public Builder setPricingRuleID(int pricingRuleID) {
            this.pricingRuleID = pricingRuleID;
            return this;
        }

        public Builder setCarType(CarType carType) {
            this.carType = carType;
            return this;
        }

        public Builder setBaseDailyRate(double baseDailyRate) {
            this.baseDailyRate = baseDailyRate;
            return this;
        }

        public Builder setWeeklyRate(double weeklyRate) {
            this.weeklyRate = weeklyRate;
            return this;
        }

        public Builder setMonthlyRate(double monthlyRate) {
            this.monthlyRate = monthlyRate;
            return this;
        }

        public Builder setWeekendRate(double weekendRate) {
            this.weekendRate = weekendRate;
            return this;
        }

        public Builder setSeasonalMultiplier(double seasonalMultiplier) {
            this.seasonalMultiplier = seasonalMultiplier;
            return this;
        }

        public Builder setValidFrom(LocalDate validFrom) {
            this.validFrom = validFrom;
            return this;
        }

        public Builder setValidTo(LocalDate validTo) {
            this.validTo = validTo;
            return this;
        }

        public Builder setActive(boolean active) {
            this.active = active;
            return this;
        }

        public Builder copy(PricingRule pricingRule) {
            this.pricingRuleID = pricingRule.pricingRuleID;
            this.carType = pricingRule.carType;
            this.baseDailyRate = pricingRule.baseDailyRate;
            this.weeklyRate = pricingRule.weeklyRate;
            this.monthlyRate = pricingRule.monthlyRate;
            this.weekendRate = pricingRule.weekendRate;
            this.seasonalMultiplier = pricingRule.seasonalMultiplier;
            this.validFrom = pricingRule.validFrom;
            this.validTo = pricingRule.validTo;
            this.active = pricingRule.active;
            return this;
        }

        public PricingRule build() {
            return new PricingRule(this);
        }
    }
}
