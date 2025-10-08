package za.co.carhire.mapper.reservation;

import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.dto.reservation.InsuranceDTO;

/*
    Imtiyaaz Waggie 219374759
    Date: 09/10/2025
*/

public class InsuranceMapper {

    /**
     * Convert Insurance entity to InsuranceDTO
     */
    public static InsuranceDTO toDTO(Insurance insurance) {
        if (insurance == null) {
            return null;
        }

        InsuranceDTO dto = new InsuranceDTO();
        dto.setInsuranceID(insurance.getInsuranceID());
        dto.setInsuranceStartDate(insurance.getInsuranceStartDate());
        dto.setInsuranceCost(insurance.getInsuranceCost());
        dto.setInsuranceProvider(insurance.getInsuranceProvider());
        dto.setStatus(insurance.getStatus());
        dto.setPolicyNumber(insurance.getPolicyNumber());
        dto.setMechanic(insurance.getMechanic());

        // Include car ID if car is linked
        if (insurance.getCar() != null) {
            dto.setCarID(insurance.getCar().getCarID());
        }

        return dto;
    }

    /**
     * Convert InsuranceDTO to Insurance entity
     */
    public static Insurance toEntity(InsuranceDTO dto) {
        if (dto == null) {
            return null;
        }

        Insurance.Builder builder = new Insurance.Builder();

        if (dto.getInsuranceID() != null) {
            builder.setInsuranceID(dto.getInsuranceID());
        }
        if (dto.getInsuranceStartDate() != null) {
            builder.setInsuranceStartDate(dto.getInsuranceStartDate());
        }
        if (dto.getInsuranceCost() != null) {
            builder.setInsuranceCost(dto.getInsuranceCost());
        }
        if (dto.getInsuranceProvider() != null) {
            builder.setInsuranceProvider(dto.getInsuranceProvider());
        }
        if (dto.getStatus() != null) {
            builder.setStatus(dto.getStatus());
        }
        if (dto.getPolicyNumber() != null) {
            builder.setPolicyNumber(dto.getPolicyNumber());
        }
        if (dto.getMechanic() != null) {
            builder.setMechanic(dto.getMechanic());
        }

        return builder.build();
    }

    /**
     * Update existing Insurance entity from InsuranceDTO
     */
    public static Insurance updateEntityFromDTO(Insurance existingInsurance, InsuranceDTO dto) {
        if (existingInsurance == null || dto == null) {
            return existingInsurance;
        }

        if (dto.getInsuranceStartDate() != null) {
            existingInsurance.setInsuranceStartDate(dto.getInsuranceStartDate());
        }
        if (dto.getInsuranceCost() != null) {
            existingInsurance.setInsuranceCost(dto.getInsuranceCost());
        }
        if (dto.getInsuranceProvider() != null) {
            existingInsurance.setInsuranceProvider(dto.getInsuranceProvider());
        }
        if (dto.getStatus() != null) {
            existingInsurance.setStatus(dto.getStatus());
        }
        if (dto.getPolicyNumber() != null) {
            existingInsurance.setPolicyNumber(dto.getPolicyNumber());
        }
        if (dto.getMechanic() != null) {
            existingInsurance.setMechanic(dto.getMechanic());
        }

        return existingInsurance;
    }
}
