package za.co.carhire.mapper;

import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.dto.InsuranceDTO;

public class InsuranceMapper {
    // Convert InsuranceDTO to Insurance entity
    public static InsuranceDTO toDTO(Insurance insurance){
        return new InsuranceDTO(
                insurance.getInsuranceID(),
                insurance.getInsuranceStartDate(),
                insurance.getInsuranceCost(),
                insurance.getInsuranceProvider(),
                insurance.getStatus(),
                insurance.getPolicyNumber(),
                insurance.getMechanic(),
                insurance.getCar() != null ? insurance.getCar().getCarID() : null
        );

    }

    // Convert Insurance entity to InsuranceDTO
    public static Insurance fromDTO(InsuranceDTO dto) {
        return new Insurance.Builder()
                .setInsuranceID(dto.insuranceID())
                .setInsuranceStartDate(dto.insuranceStartDate())
                .setInsuranceCost(dto.insuranceCost())
                .setInsuranceProvider(dto.insuranceProvider())
                .setStatus(dto.status())
                .setPolicyNumber(dto.policyNumber())
                .setMechanic(dto.mechanic())
                .build();
    }

}
