package za.co.carhire.dto;

import za.co.carhire.domain.reservation.CoverageType;
import java.time.LocalDateTime;

public record InsuranceDTO(
        int insuranceID,
        LocalDateTime insuranceStartDate,
        LocalDateTime insuranceEndDate,
        double insuranceCost,
        String insuranceProvider,
        CoverageType coverageType,
        double deductible,
        String policyNumber,
        boolean isActive
) {

}
