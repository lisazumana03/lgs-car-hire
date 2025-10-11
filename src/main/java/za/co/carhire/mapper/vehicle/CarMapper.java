package za.co.carhire.mapper.vehicle;

import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.dto.vehicle.CarDTO;
import java.util.Base64;

/**
 * Mapper class for converting between Car entity and CarDTO
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 * Updated: 31/08/2025 - Added image URL mapping
 * Updated: [Current Date] - Changed to BLOB storage with Base64 encoding
 */
public class CarMapper {

    /**
     * Convert Car entity to CarDTO with full CarType details and image data as Base64
     * @param car the entity to convert
     * @return the DTO representation with complete CarType information and image as Base64
     */
    public static CarDTO toDTO(Car car) {
        if (car == null) {
            return null;
        }

        CarDTO.Builder builder = new CarDTO.Builder()
                .setCarID(car.getCarID())
                .setModel(car.getModel())
                .setBrand(car.getBrand())
                .setYear(car.getYear())
                .setAvailability(car.isAvailability())
                .setRentalPrice(car.getRentalPrice());

        // Convert byte array to Base64 string for transmission
        if (car.getImageData() != null && car.getImageData().length > 0) {
            String base64Image = Base64.getEncoder().encodeToString(car.getImageData());
            builder.setImageBase64(base64Image);
        }
        builder.setImageName(car.getImageName())
               .setImageType(car.getImageType());

        if (car.getCarType() != null) {
            CarType carType = car.getCarType();
            builder.setCarTypeID(carType.getCarTypeID())
                    .setCarTypeName(carType.getType())
                    .setCarTypeFuelType(carType.getFuelType())
                    .setCarTypeNumberOfWheels(carType.getNumberOfWheels())
                    .setCarTypeNumberOfSeats(carType.getNumberOfSeats());
        }

        if (car.getInsurance() != null) {
            builder.setInsuranceID(car.getInsurance().getInsuranceID());
        }

        if (car.getBooking() != null) {
            builder.setBookingID(car.getBooking().getBookingID());
        }

        return builder.build();
    }

    /**
     * Convert CarDTO to Car entity with image data decoded from Base64
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
                .setAvailability(dto.isAvailability())
                .setRentalPrice(dto.getRentalPrice());

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
     * @param existingCar the existing entity to update
     * @param dto the DTO with new values
     * @return the updated entity
     */
    public static Car updateEntityFromDTO(Car existingCar, CarDTO dto) {
        if (existingCar == null || dto == null) {
            return existingCar;
        }

        existingCar.setModel(dto.getModel());
        existingCar.setBrand(dto.getBrand());
        existingCar.setYear(dto.getYear());
        existingCar.setAvailability(dto.isAvailability());
        existingCar.setRentalPrice(dto.getRentalPrice());

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