package za.co.carhire.service.vehicle.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.vehicle.PricingRule;
import za.co.carhire.repository.vehicle.IPricingRuleRepository;
import za.co.carhire.service.vehicle.IPricingRuleService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PricingRuleService implements IPricingRuleService {

    @Autowired
    private IPricingRuleRepository pricingRuleRepository;

    @Override
    public PricingRule create(PricingRule pricingRule) {
        return pricingRuleRepository.save(pricingRule);
    }

    @Override
    @Transactional(readOnly = true)
    public PricingRule read(Integer id) {
        return pricingRuleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public PricingRule read(int pricingRuleID) {
        return pricingRuleRepository.findById(pricingRuleID).orElse(null);
    }

    @Override
    public PricingRule update(PricingRule pricingRule) {
        if (pricingRuleRepository.existsById(pricingRule.getPricingRuleID())) {
            return pricingRuleRepository.save(pricingRule);
        }
        return null;
    }

    @Override
    public void delete(int pricingRuleID) {
        pricingRuleRepository.deleteById(pricingRuleID);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingRule> getAllPricingRules() {
        return pricingRuleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingRule> getPricingRulesByCarType(int carTypeID) {
        return pricingRuleRepository.findByCarTypeCarTypeID(carTypeID);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingRule> getActivePricingRules() {
        return pricingRuleRepository.findByActive(true);
    }

    @Override
    @Transactional(readOnly = true)
    public PricingRule getActivePricingRuleForCarType(int carTypeID) {
        List<PricingRule> rules = pricingRuleRepository.findByCarTypeCarTypeIDAndActive(carTypeID, true);

        // Return the first active rule, or filter by current date
        LocalDate today = LocalDate.now();
        Optional<PricingRule> validRule = rules.stream()
                .filter(rule -> (rule.getValidFrom() == null || !rule.getValidFrom().isAfter(today)) &&
                               (rule.getValidTo() == null || !rule.getValidTo().isBefore(today)))
                .findFirst();

        return validRule.orElse(rules.isEmpty() ? null : rules.get(0));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PricingRule> getPricingRulesValidOnDate(LocalDate date) {
        return pricingRuleRepository.findByValidFromBeforeAndValidToAfter(date, date);
    }

    @Override
    @Transactional(readOnly = true)
    public double calculatePrice(int carTypeID, int numberOfDays, LocalDate rentalDate) {
        PricingRule rule = getActivePricingRuleForCarType(carTypeID);

        if (rule == null) {
            return 0.0; // No pricing rule found
        }

        double basePrice;

        // Determine which rate to use based on rental duration
        if (numberOfDays >= 30 && rule.getMonthlyRate() > 0) {
            // Monthly rate
            int months = numberOfDays / 30;
            int remainingDays = numberOfDays % 30;
            basePrice = (months * rule.getMonthlyRate()) + (remainingDays * rule.getBaseDailyRate());
        } else if (numberOfDays >= 7 && rule.getWeeklyRate() > 0) {
            // Weekly rate
            int weeks = numberOfDays / 7;
            int remainingDays = numberOfDays % 7;
            basePrice = (weeks * rule.getWeeklyRate()) + (remainingDays * rule.getBaseDailyRate());
        } else {
            // Daily rate
            basePrice = numberOfDays * rule.getBaseDailyRate();
        }

        // Apply seasonal multiplier
        if (rule.getSeasonalMultiplier() > 0) {
            basePrice *= rule.getSeasonalMultiplier();
        }

        return basePrice;
    }
}
