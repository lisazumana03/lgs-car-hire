package za.co.carhire.factory.vehicle;

import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.domain.vehicle.PricingRule;

import java.time.LocalDate;

public class PricingRuleFactory {

    public static PricingRule createBasicPricingRule(int pricingRuleID, CarType carType, double baseDailyRate) {
        return new PricingRule.Builder()
                .setPricingRuleID(pricingRuleID)
                .setCarType(carType)
                .setBaseDailyRate(baseDailyRate)
                .setWeeklyRate(baseDailyRate * 6.5) // 10% discount for weekly
                .setMonthlyRate(baseDailyRate * 25) // ~17% discount for monthly
                .setActive(true)
                .build();
    }

    public static PricingRule createCompletePricingRule(int pricingRuleID, CarType carType,
                                                        double baseDailyRate, double weeklyRate,
                                                        double monthlyRate, double weekendRate) {
        return new PricingRule.Builder()
                .setPricingRuleID(pricingRuleID)
                .setCarType(carType)
                .setBaseDailyRate(baseDailyRate)
                .setWeeklyRate(weeklyRate)
                .setMonthlyRate(monthlyRate)
                .setWeekendRate(weekendRate)
                .setSeasonalMultiplier(1.0)
                .setActive(true)
                .build();
    }

    public static PricingRule createSeasonalPricingRule(int pricingRuleID, CarType carType,
                                                        double baseDailyRate, double seasonalMultiplier,
                                                        LocalDate validFrom, LocalDate validTo) {
        return new PricingRule.Builder()
                .setPricingRuleID(pricingRuleID)
                .setCarType(carType)
                .setBaseDailyRate(baseDailyRate)
                .setWeeklyRate(baseDailyRate * 6.5)
                .setMonthlyRate(baseDailyRate * 25)
                .setSeasonalMultiplier(seasonalMultiplier)
                .setValidFrom(validFrom)
                .setValidTo(validTo)
                .setActive(true)
                .build();
    }

    public static PricingRule createEconomyPricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 350.0);
    }

    public static PricingRule createCompactPricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 380.0);
    }

    public static PricingRule createSedanPricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 450.0);
    }

    public static PricingRule createSUVPricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 600.0);
    }

    public static PricingRule createLuxuryPricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 1200.0);
    }

    public static PricingRule createSportsPricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 1000.0);
    }

    public static PricingRule createConvertiblePricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 700.0);
    }

    public static PricingRule createMinivanPricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 580.0);
    }

    public static PricingRule createElectricPricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 750.0);
    }

    public static PricingRule createHybridPricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 400.0);
    }

    public static PricingRule createVanPricing(int pricingRuleID, CarType carType) {
        return createBasicPricingRule(pricingRuleID, carType, 550.0);
    }
}
