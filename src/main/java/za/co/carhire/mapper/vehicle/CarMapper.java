package za.co.carhire.mapper.vehicle;

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarCondition;
import za.co.carhire.domain.vehicle.CarStatus;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.dto.vehicle.CarDTO;
import java.util.Base64;

/**
 * Mapper class for converting between Car entity and CarDTO
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 * Updated: 31/08/2025 - Added image URL mapping
 * Updated: 15/10/2025 - Refactored for new Car fields and enums
 */
public class CarMapper {

    /**
     * Convert Car entity to CarDTO WITHOUT image data (lightweight version)
     * Use this for list/search operations to avoid 431 errors
     *
     * @param car the entity to convert
     * @return the DTO representation without Base64 image data
     */
    public static CarDTO toDTO(Car car) {
        return toDTO(car, false);
    }

    /**
     * Convert Car entity to CarDTO with optional image data
     *
     * @param car          the entity to convert
     * @param includeImage whether to include Base64 image data
     * @return the DTO representation
     */
    public static CarDTO toDTO(Car car, boolean includeImage) {
        if (car == null) {
            return null;
        }

        CarDTO.Builder builder = new CarDTO.Builder()
                .setCarID(car.getCarID())
                .setModel(car.getModel())
                .setBrand(car.getBrand())
                .setYear(car.getYear())
                .setLicensePlate(car.getLicensePlate())
                .setVin(car.getVin())
                .setColor(car.getColor())
                .setMileage(car.getMileage());

        if (car.getStatus() != null) {
            builder.setStatus(car.getStatus().name());
        }
        if (car.getCondition() != null) {
            builder.setCondition(car.getCondition().name());
        }

        if (car.getCurrentLocation() != null) {
            Location location = car.getCurrentLocation();
            builder.setCurrentLocationID(location.getLocationID())
                   .setCurrentLocationName(location.getLocationName());
        }

        builder.setImageData(car.getImageData());

        if (includeImage && car.getImageData() != null && car.getImageData().length > 0) {
            String base64Image = Base64.getEncoder().encodeToString(car.getImageData());
            builder.setImageBase64(base64Image);
        }

        builder.setImageName(car.getImageName())
                .setImageType(car.getImageType());

        if (car.getCarType() != null) {
            CarType carType = car.getCarType();
            builder.setCarTypeID(carType.getCarTypeID())
                    .setCarTypeNumberOfSeats(carType.getNumberOfSeats())
                    .setCarTypeNumberOfDoors(carType.getNumberOfDoors())
                    .setCarTypeAirConditioned(carType.isAirConditioned())
                    .setCarTypeLuggageCapacity(carType.getLuggageCapacity())
                    .setCarTypeDescription(carType.getDescription());

            if (carType.getCategory() != null) {
                builder.setCarTypeCategory(carType.getCategory().name());
            }
            if (carType.getFuelType() != null) {
                builder.setCarTypeFuelType(carType.getFuelType().name());
            }
            if (carType.getTransmissionType() != null) {
                builder.setCarTypeTransmissionType(carType.getTransmissionType().name());
            }
        }

        return builder.build();
    }

    /**
     * Convert CarDTO to Car entity with image data decoded from Base64
     *
     * @param dto the DTO to convert
     * @return the entity representation
     */
    public static Car toEntity(CarDTO dto) {
        if (dto == null) {
            return null;
        }

        Car.Builder builder = new Car.Builder()
                .setCarID(dto.getCarID())
                .setModel(dto.getModel())
                .setBrand(dto.getBrand())
                .setYear(dto.getYear())
                .setLicensePlate(dto.getLicensePlate())
                .setVin(dto.getVin())
                .setColor(dto.getColor())
                .setMileage(dto.getMileage());

        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                builder.setStatus(CarStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
                // Default to AVAILABLE if invalid status
                builder.setStatus(CarStatus.AVAILABLE);
            }
        }

        if (dto.getCondition() != null && !dto.getCondition().isEmpty()) {
            try {
                builder.setCondition(CarCondition.valueOf(dto.getCondition()));
            } catch (IllegalArgumentException e) {
                builder.setCondition(CarCondition.GOOD);
            }
        }

        // Convert Base64 string to byte array for storage
        if (dto.getImageBase64() != null && !dto.getImageBase64().isEmpty()) {
            try {
                byte[] imageData = Base64.getDecoder().decode(dto.getImageBase64());
                builder.setImageData(imageData);
            } catch (IllegalArgumentException e) {
                // Handle invalid Base64 string - log or set to null
                builder.setImageData(null);
            }
        }
        builder.setImageName(dto.getImageName())
                .setImageType(dto.getImageType());

        return builder.build();
    }

    /**
     * Update existing Car entity from CarDTO including image data
     *
     * @param existingCar the existing entity to update
     * @param dto         the DTO with new values
     * @return the updated entity
     */
    public static Car updateEntityFromDTO(Car existingCar, CarDTO dto) {
        if (existingCar == null || dto == null) {
            return existingCar;
        }

        existingCar.setModel(dto.getModel());
        existingCar.setBrand(dto.getBrand());
        existingCar.setYear(dto.getYear());
        existingCar.setLicensePlate(dto.getLicensePlate());
        existingCar.setVin(dto.getVin());
        existingCar.setColor(dto.getColor());
        existingCar.setMileage(dto.getMileage());

        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                existingCar.setStatus(CarStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
            }
        }

        if (dto.getCondition() != null && !dto.getCondition().isEmpty()) {
            try {
                existingCar.setCondition(CarCondition.valueOf(dto.getCondition()));
            } catch (IllegalArgumentException e) {
            }
        }

        // Update image data if provided
        if (dto.getImageBase64() != null && !dto.getImageBase64().isEmpty()) {
            try {
                byte[] imageData = Base64.getDecoder().decode(dto.getImageBase64());
                existingCar.setImageData(imageData);
                existingCar.setImageName(dto.getImageName());
                existingCar.setImageType(dto.getImageType());
            } catch (IllegalArgumentException e) {
                // Handle invalid Base64 string - keep existing image
            }
        }

        return existingCar;
    }
}
