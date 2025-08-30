package za.co.carhire.mapper.reservation;

import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.reservation.InsuranceDTO;

/**
 * Mapper class for converting between Insurance entity and InsuranceDTO
 * Author:Imtiyaaz Waggie 219374759
 */
public class InsuranceMapper {

    /**
     * Convert Insurance entity to InsuranceDTO
     * @param insurance the entity to convert
     * @return the DTO representation
     */
    public static InsuranceDTO toDTO(Insurance insurance) {
        if (insurance == null) {
            return null;
        }

        InsuranceDTO.Builder builder = new InsuranceDTO.Builder()
                .setInsuranceID(insurance.getInsuranceID())
                .setInsuranceStartDate(insurance.getInsuranceStartDate())
                .setInsuranceCost(insurance.getInsuranceCost())
                .setInsuranceProvider(insurance.getInsuranceProvider())
                .setStatus(insurance.getStatus())
                .setPolicyNumber(insurance.getPolicyNumber())
                .setMechanic(insurance.getMechanic());

        if (insurance.getCar() != null) {
            Car car = insurance.getCar();
            builder.setCarID(car.getCarID());
            String brandModel = car.getBrand() + " " + car.getModel();
            builder.setCarBrandModel(brandModel);
        }

        return builder.build();
    }

    /**
     * Convert InsuranceDTO to Insurance entity (without relationships)
     * @param dto the DTO to convert
     * @return the entity representation
     */
    public static Insurance toEntity(InsuranceDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Insurance.Builder()
                .setInsuranceID(dto.getInsuranceID())
                .setInsuranceStartDate(dto.getInsuranceStartDate())
                .setInsuranceCost(dto.getInsuranceCost())
                .setInsuranceProvider(dto.getInsuranceProvider())
                .setStatus(dto.getStatus())
                .setPolicyNumber(dto.getPolicyNumber())
                .setMechanic(dto.getMechanic())
                .build();
    }

    /**
     * Update existing Insurance entity from InsuranceDTO
     * @param existingInsurance the existing entity to update
     * @param dto the DTO with new values
     * @return the updated entity
     */
    public static Insurance updateEntityFromDTO(Insurance existingInsurance, InsuranceDTO dto) {
        if (existingInsurance == null || dto == null) {
            return existingInsurance;
        }

        return new Insurance.Builder()
                .setInsuranceID(existingInsurance.getInsuranceID())
                .setInsuranceStartDate(dto.getInsuranceStartDate())
                .setInsuranceCost(dto.getInsuranceCost())
                .setInsuranceProvider(dto.getInsuranceProvider())
                .setStatus(dto.getStatus())
                .setPolicyNumber(dto.getPolicyNumber())
                .setMechanic(dto.getMechanic())
                .setCar(existingInsurance.getCar())
                .build();
    }
}