package za.co.carhire.service.vehicle;

import za.co.carhire.domain.vehicle.PricingRule;
import za.co.carhire.service.IService;

import java.time.LocalDate;
import java.util.List;

public interface IPricingRuleService extends IService<PricingRule, Integer> {

    PricingRule create(PricingRule pricingRule);

    PricingRule read(int pricingRuleID);

    PricingRule update(PricingRule pricingRule);

    void delete(int pricingRuleID);

    List<PricingRule> getAllPricingRules();

    List<PricingRule> getPricingRulesByCarType(int carTypeID);

    List<PricingRule> getActivePricingRules();

    PricingRule getActivePricingRuleForCarType(int carTypeID);

    List<PricingRule> getPricingRulesValidOnDate(LocalDate date);

    double calculatePrice(int carTypeID, int numberOfDays, LocalDate rentalDate);
}
