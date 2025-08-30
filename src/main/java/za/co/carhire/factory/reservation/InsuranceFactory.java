/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 18/05/2025
 * Updated: 30/08/2025 - Improved validation with exceptions
 * */
package za.co.carhire.factory.reservation;

import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;
import java.util.Date;

/**
 *
 * Author: Imtiyaaz Waggie 219374759
 * Updated Factory
 */


public class InsuranceFactory {

    /**
     * Creates a basic Insurance instance with validation
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public static Insurance createInsurance(int insuranceID, Date insuranceStartDate, double insuranceCost,
                                            String insuranceProvider, String status, long policyNumber, String mechanic) {

        if (insuranceID < 0 || insuranceID > 100000) {
            throw new IllegalArgumentException("Insurance ID must be between 0 and 100000");
        }

        if (insuranceStartDate == null) {
            throw new IllegalArgumentException("Insurance start date cannot be null");
        }

        if (insuranceCost < 0) {
            throw new IllegalArgumentException("Insurance cost cannot be negative");
        }

        if (insuranceProvider == null || insuranceProvider.trim().isEmpty()) {
            throw new IllegalArgumentException("Insurance provider cannot be null or empty");
        }

        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }

        if (policyNumber < 0) {
            throw new IllegalArgumentException("Policy number cannot be negative");
        }

        if (mechanic == null || mechanic.trim().isEmpty()) {
            throw new IllegalArgumentException("Mechanic cannot be null or empty");
        }

        return new Insurance.Builder()
                .setInsuranceID(insuranceID)
                .setInsuranceStartDate(insuranceStartDate)
                .setInsuranceCost(insuranceCost)
                .setInsuranceProvider(insuranceProvider)
                .setStatus(status)
                .setPolicyNumber(policyNumber)
                .setMechanic(mechanic)
                .build();
    }

    public static Insurance createInsuranceWithCar(int insuranceID, Date insuranceStartDate, double insuranceCost,
                                                   String insuranceProvider, String status, long policyNumber,
                                                   String mechanic, Car car) {

        Insurance insurance = createInsurance(insuranceID, insuranceStartDate, insuranceCost,
                insuranceProvider, status, policyNumber, mechanic);

        return new Insurance.Builder()
                .copy(insurance)
                .setCar(car)
                .build();
    }

    public static Insurance createActiveInsurance(int insuranceID, Date insuranceStartDate, double insuranceCost,
                                                  String insuranceProvider, long policyNumber, String mechanic) {
        return createInsurance(insuranceID, insuranceStartDate, insuranceCost,
                insuranceProvider, "ACTIVE", policyNumber, mechanic);
    }

    public static Insurance createCancelledInsurance(int insuranceID, Date insuranceStartDate, double insuranceCost,
                                                     String insuranceProvider, long policyNumber, String mechanic) {
        return createInsurance(insuranceID, insuranceStartDate, insuranceCost,
                insuranceProvider, "CANCELLED", policyNumber, mechanic);
    }

    public static Insurance copy(Insurance original, int newInsuranceID) {
        if (original == null) {
            throw new IllegalArgumentException("Original Insurance cannot be null");
        }

        return new Insurance.Builder()
                .copy(original)
                .setInsuranceID(newInsuranceID)
                .build();
    }

    public static Insurance createMinimalInsurance(int insuranceID, double insuranceCost, String insuranceProvider) {
        return createInsurance(insuranceID, new Date(), insuranceCost,
                insuranceProvider, "NEW", 1000L, "Default Mechanic");
    }
}