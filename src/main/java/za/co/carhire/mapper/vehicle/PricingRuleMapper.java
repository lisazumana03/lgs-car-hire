package za.co.carhire.mapper.vehicle;

import za.co.carhire.domain.vehicle.PricingRule;
import za.co.carhire.dto.vehicle.PricingRuleDTO;

import java.time.LocalDate;

public class PricingRuleMapper {

    public static PricingRuleDTO toDTO(PricingRule pricingRule) {
        if (pricingRule == null) {
            return null;
        }

        PricingRuleDTO dto = new PricingRuleDTO();
        dto.setPricingRuleID(pricingRule.getPricingRuleID());
        dto.setBaseDailyRate(pricingRule.getBaseDailyRate());
        dto.setWeeklyRate(pricingRule.getWeeklyRate());
        dto.setMonthlyRate(pricingRule.getMonthlyRate());
        dto.setWeekendRate(pricingRule.getWeekendRate());
        dto.setSeasonalMultiplier(pricingRule.getSeasonalMultiplier());
        dto.setActive(pricingRule.isActive());

        if (pricingRule.getValidFrom() != null) {
            dto.setValidFrom(pricingRule.getValidFrom().toString());
        }
        if (pricingRule.getValidTo() != null) {
            dto.setValidTo(pricingRule.getValidTo().toString());
        }

        if (pricingRule.getCarType() != null) {
            dto.setCarTypeID(pricingRule.getCarType().getCarTypeID());
            if (pricingRule.getCarType().getCategory() != null) {
                dto.setCarTypeName(pricingRule.getCarType().getCategory().name());
            }
        }

        return dto;
    }

    public static PricingRule toEntity(PricingRuleDTO dto) {
        if (dto == null) {
            return null;
        }

        PricingRule.Builder builder = new PricingRule.Builder()
                .setPricingRuleID(dto.getPricingRuleID())
                .setBaseDailyRate(dto.getBaseDailyRate())
                .setWeeklyRate(dto.getWeeklyRate())
                .setMonthlyRate(dto.getMonthlyRate())
                .setWeekendRate(dto.getWeekendRate())
                .setSeasonalMultiplier(dto.getSeasonalMultiplier())
                .setActive(dto.isActive());

        if (dto.getValidFrom() != null && !dto.getValidFrom().isEmpty()) {
            try {
                builder.setValidFrom(LocalDate.parse(dto.getValidFrom()));
            } catch (Exception e) {
            }
        }
        if (dto.getValidTo() != null && !dto.getValidTo().isEmpty()) {
            try {
                builder.setValidTo(LocalDate.parse(dto.getValidTo()));
            } catch (Exception e) {
            }
        }

        return builder.build();
    }

    public static PricingRule updateEntityFromDTO(PricingRule existingRule, PricingRuleDTO dto) {
        if (existingRule == null || dto == null) {
            return existingRule;
        }

        existingRule.setBaseDailyRate(dto.getBaseDailyRate());
        existingRule.setWeeklyRate(dto.getWeeklyRate());
        existingRule.setMonthlyRate(dto.getMonthlyRate());
        existingRule.setWeekendRate(dto.getWeekendRate());
        existingRule.setSeasonalMultiplier(dto.getSeasonalMultiplier());
        existingRule.setActive(dto.isActive());

        if (dto.getValidFrom() != null && !dto.getValidFrom().isEmpty()) {
            try {
                existingRule.setValidFrom(LocalDate.parse(dto.getValidFrom()));
            } catch (Exception e) {
            }
        }
        if (dto.getValidTo() != null && !dto.getValidTo().isEmpty()) {
            try {
                existingRule.setValidTo(LocalDate.parse(dto.getValidTo()));
            } catch (Exception e) {
            }
        }

        return existingRule;
    }
}
