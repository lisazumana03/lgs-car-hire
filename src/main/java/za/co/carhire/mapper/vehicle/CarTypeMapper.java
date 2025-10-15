package za.co.carhire.mapper.vehicle;

import za.co.carhire.domain.vehicle.*;
import za.co.carhire.dto.vehicle.CarTypeDTO;

/**
 * Mapper class for converting between CarType entity and CarTypeDTO
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 */
public class CarTypeMapper {

    /**
     * Convert CarType entity to CarTypeDTO
     *
     * @param carType the entity to convert
     * @return the DTO representation
     */
    public static CarTypeDTO toDTO(CarType carType) {
        if (carType == null) {
            return null;
        }

        CarTypeDTO.Builder builder = new CarTypeDTO.Builder()
                .setCarTypeID(carType.getCarTypeID())
                .setNumberOfSeats(carType.getNumberOfSeats())
                .setNumberOfDoors(carType.getNumberOfDoors())
                .setAirConditioned(carType.isAirConditioned())
                .setLuggageCapacity(carType.getLuggageCapacity())
                .setDescription(carType.getDescription());

        if (carType.getCategory() != null) {
            builder.setCategory(carType.getCategory().name());
        }
        if (carType.getFuelType() != null) {
            builder.setFuelType(carType.getFuelType().name());
        }
        if (carType.getTransmissionType() != null) {
            builder.setTransmissionType(carType.getTransmissionType().name());
        }

        return builder.build();
    }

    /**
     * Convert CarTypeDTO to CarType entity (without relationships)
     *
     * @param dto the DTO to convert
     * @return the entity representation
     */
    public static CarType toEntity(CarTypeDTO dto) {
        if (dto == null) {
            return null;
        }

        CarType.Builder builder = new CarType.Builder()
                .setCarTypeID(dto.getCarTypeID())
                .setNumberOfSeats(dto.getNumberOfSeats())
                .setNumberOfDoors(dto.getNumberOfDoors())
                .setAirConditioned(dto.isAirConditioned())
                .setLuggageCapacity(dto.getLuggageCapacity())
                .setDescription(dto.getDescription());

        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            try {
                builder.setCategory(VehicleCategory.valueOf(dto.getCategory()));
            } catch (IllegalArgumentException e) {
            }
        }

        if (dto.getFuelType() != null && !dto.getFuelType().isEmpty()) {
            try {
                builder.setFuelType(FuelType.valueOf(dto.getFuelType()));
            } catch (IllegalArgumentException e) {
            }
        }

        if (dto.getTransmissionType() != null && !dto.getTransmissionType().isEmpty()) {
            try {
                builder.setTransmissionType(TransmissionType.valueOf(dto.getTransmissionType()));
            } catch (IllegalArgumentException e) {
                // Handle invalid enum value - log or set default
            }
        }

        return builder.build();
    }

    /**
     * Update existing CarType entity from CarTypeDTO
     *
     * @param existingCarType the existing entity to update
     * @param dto             the DTO with new values
     * @return the updated entity
     */
    public static CarType updateEntityFromDTO(CarType existingCarType, CarTypeDTO dto) {
        if (existingCarType == null || dto == null) {
            return existingCarType;
        }

        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            try {
                existingCarType.setCategory(VehicleCategory.valueOf(dto.getCategory()));
            } catch (IllegalArgumentException e) {
            }
        }

        if (dto.getFuelType() != null && !dto.getFuelType().isEmpty()) {
            try {
                existingCarType.setFuelType(FuelType.valueOf(dto.getFuelType()));
            } catch (IllegalArgumentException e) {
            }
        }

        if (dto.getTransmissionType() != null && !dto.getTransmissionType().isEmpty()) {
            try {
                existingCarType.setTransmissionType(TransmissionType.valueOf(dto.getTransmissionType()));
            } catch (IllegalArgumentException e) {
            }
        }

        existingCarType.setNumberOfSeats(dto.getNumberOfSeats());
        existingCarType.setNumberOfDoors(dto.getNumberOfDoors());
        existingCarType.setAirConditioned(dto.isAirConditioned());
        existingCarType.setLuggageCapacity(dto.getLuggageCapacity());
        existingCarType.setDescription(dto.getDescription());

        return existingCarType;
    }
}