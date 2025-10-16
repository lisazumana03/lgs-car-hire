package za.co.carhire.mapper;

import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.dto.InsuranceDTO;

public class InsuranceMapper {

    public static InsuranceDTO toDTO(Insurance insurance) {
        if (insurance == null) {
            return null;
        }

        return new InsuranceDTO(
                insurance.getInsuranceID(),
                insurance.getInsuranceStartDate(),
                insurance.getInsuranceEndDate(),
                insurance.getInsuranceCost(),
                insurance.getInsuranceProvider(),
                insurance.getCoverageType(),
                insurance.getDeductible(),
                insurance.getPolicyNumber(),
                insurance.isActive()
        );
    }

    public static Insurance fromDTO(InsuranceDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Insurance.Builder()
                .setInsuranceID(dto.insuranceID())
                .setInsuranceStartDate(dto.insuranceStartDate())
                .setInsuranceEndDate(dto.insuranceEndDate())
                .setInsuranceCost(dto.insuranceCost())
                .setInsuranceProvider(dto.insuranceProvider())
                .setCoverageType(dto.coverageType())
                .setDeductible(dto.deductible())
                .setPolicyNumber(dto.policyNumber())
                .setActive(dto.isActive())
                .build();
    }
}
