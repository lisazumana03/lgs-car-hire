/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 18/05/2025
 * Updated: 2025-10-16 - Completely rewritten to match new Insurance entity structure
 * */
package za.co.carhire.factory.reservation;

import za.co.carhire.domain.reservation.CoverageType;
import za.co.carhire.domain.reservation.Insurance;

import java.time.LocalDateTime;

public class InsuranceFactory {

    public static Insurance createInsurance(
            int insuranceID,
            LocalDateTime insuranceStartDate,
            LocalDateTime insuranceEndDate,
            double insuranceCost,
            String insuranceProvider,
            CoverageType coverageType,
            double deductible,
            String policyNumber,
            boolean isActive) {

        if (insuranceID < 0) {
            throw new IllegalArgumentException("Insurance ID cannot be negative");
        }
        if (insuranceStartDate == null) {
            throw new IllegalArgumentException("Insurance start date is required");
        }
        if (insuranceCost < 0) {
            throw new IllegalArgumentException("Insurance cost cannot be negative");
        }
        if (insuranceProvider == null || insuranceProvider.trim().isEmpty()) {
            throw new IllegalArgumentException("Insurance provider is required");
        }
        if (coverageType == null) {
            throw new IllegalArgumentException("Coverage type is required");
        }
        if (deductible < 0) {
            throw new IllegalArgumentException("Deductible cannot be negative");
        }
        if (policyNumber == null || policyNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Policy number is required");
        }

        return new Insurance.Builder()
                .setInsuranceID(insuranceID)
                .setInsuranceStartDate(insuranceStartDate)
                .setInsuranceEndDate(insuranceEndDate)
                .setInsuranceCost(insuranceCost)
                .setInsuranceProvider(insuranceProvider)
                .setCoverageType(coverageType)
                .setDeductible(deductible)
                .setPolicyNumber(policyNumber)
                .setActive(isActive)
                .build();
    }

    public static Insurance createBasicInsurance(
            String insuranceProvider,
            double insuranceCost,
            double deductible,
            String policyNumber,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        return createInsurance(
                0,  // ID will be auto-generated
                startDate,
                endDate,
                insuranceCost,
                insuranceProvider,
                CoverageType.BASIC,
                deductible,
                policyNumber,
                true  // Active by default
        );
    }

    public static Insurance createPremiumInsurance(
            String insuranceProvider,
            double insuranceCost,
            double deductible,
            String policyNumber,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        return createInsurance(
                0,  // ID will be auto-generated
                startDate,
                endDate,
                insuranceCost,
                insuranceProvider,
                CoverageType.PREMIUM,
                deductible,
                policyNumber,
                true  // Active by default
        );
    }

    public static Insurance createComprehensiveInsurance(
            String insuranceProvider,
            double insuranceCost,
            String policyNumber,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        return createInsurance(
                0,  // ID will be auto-generated
                startDate,
                endDate,
                insuranceCost,
                insuranceProvider,
                CoverageType.COMPREHENSIVE,
                0.0,  // No deductible for comprehensive
                policyNumber,
                true  // Active by default
        );
    }
}
