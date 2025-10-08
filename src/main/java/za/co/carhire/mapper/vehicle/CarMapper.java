package za.co.carhire.mapper.vehicle;

import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.dto.vehicle.CarDTO;
import za.co.carhire.service.vehicle.ICarTypeService;

/**
 * Mapper class for converting between Car entity and CarDTO
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 * Updated: 31/08/2025 - Added image URL mapping
 * Updated: 07/10/2025 - Added CarType relationship support
 */
public class CarMapper {

    /**
     * Convert Car entity to CarDTO with full CarType details and image URL
     * @param car the entity to convert
     * @return the DTO representation with complete CarType information and image URL
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
                .setRentalPrice(car.getRentalPrice())
                .setImageUrl(car.getImageUrl());

        if (car.getCarType() != null) {
            CarType carType = car.getCarType();
            builder.setCarTypeID(carType.getCarTypeID())
                    .setCarTypeName(carType.getType())
                    .setCarTypeFuelType(carType.getFuelType())
                    .setCarTypeNumberOfWheels(carType.getNumberOfWheels())
                    .setCarTypeNumberOfSeats(carType.getNumberOfSeats());
        }

        if (car.getInsurance() != null) {
            builder.setInsuranceID(car.getInsurance().getInsuranceID())
                    .setInsuranceProvider(car.getInsurance().getInsuranceProvider())
                    .setInsuranceCost(car.getInsurance().getInsuranceCost())
                    .setInsuranceStatus(car.getInsurance().getStatus())
                    .setInsurancePolicyNumber(car.getInsurance().getPolicyNumber());
        }

        if (car.getBooking() != null) {
            builder.setBookingID(car.getBooking().getBookingID());

            if (car.getBooking().getBookingStatus() != null) {
                builder.setBookingStatus(car.getBooking().getBookingStatus().name());
            }

            if (car.getBooking().getUser() != null) {
                builder.setBookingUserName(car.getBooking().getUser().getName());
            }

            if (car.getBooking().getStartDate() != null) {
                builder.setBookingStartDate(car.getBooking().getStartDate().toString());
            }

            if (car.getBooking().getEndDate() != null) {
                builder.setBookingEndDate(car.getBooking().getEndDate().toString());
            }
        }

        return builder.build();
    }

    /**
     * Convert CarDTO to Car entity with image URL (without CarType relationship)
     * @param dto the DTO to convert
     * @return the entity representation
     */
    public static Car toEntity(CarDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Car.Builder()
                .setCarID(dto.getCarID())
                .setModel(dto.getModel())
                .setBrand(dto.getBrand())
                .setYear(dto.getYear())
                .setAvailability(dto.isAvailability())
                .setRentalPrice(dto.getRentalPrice())
                .setImageUrl(dto.getImageUrl())
                .build();
    }

    /**
     * Convert CarDTO to Car entity with CarType relationship
     * @param dto the DTO to convert
     * @param carTypeService service to fetch the CarType
     * @return the entity representation with CarType linked
     */
    public static Car toEntity(CarDTO dto, ICarTypeService carTypeService) {
        if (dto == null) {
            return null;
        }

        Car.Builder builder = new Car.Builder()
                .setCarID(dto.getCarID())
                .setModel(dto.getModel())
                .setBrand(dto.getBrand())
                .setYear(dto.getYear())
                .setAvailability(dto.isAvailability())
                .setRentalPrice(dto.getRentalPrice())
                .setImageUrl(dto.getImageUrl());

        // Link CarType if carTypeID is provided
        if (dto.getCarTypeID() != null && carTypeService != null) {
            CarType carType = carTypeService.read(dto.getCarTypeID());
            if (carType != null) {
                builder.setCarType(carType);
            }
        }

        return builder.build();
    }

    /**
     * Update existing Car entity from CarDTO including image URL (without CarType)
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
        existingCar.setImageUrl(dto.getImageUrl());

        return existingCar;
    }

    /**
     * Update existing Car entity from CarDTO including CarType relationship
     * @param existingCar the existing entity to update
     * @param dto the DTO with new values
     * @param carTypeService service to fetch the CarType
     * @return the updated entity
     */
    public static Car updateEntityFromDTO(Car existingCar, CarDTO dto, ICarTypeService carTypeService) {
        if (existingCar == null || dto == null) {
            return existingCar;
        }

        existingCar.setModel(dto.getModel());
        existingCar.setBrand(dto.getBrand());
        existingCar.setYear(dto.getYear());
        existingCar.setAvailability(dto.isAvailability());
        existingCar.setRentalPrice(dto.getRentalPrice());
        existingCar.setImageUrl(dto.getImageUrl());

        // Update CarType if carTypeID is provided
        if (dto.getCarTypeID() != null && carTypeService != null) {
            CarType carType = carTypeService.read(dto.getCarTypeID());
            if (carType != null) {
                existingCar.setCarType(carType);
            }
        } else if (dto.getCarTypeID() == null) {
            // If carTypeID is explicitly null, remove the relationship
            existingCar.setCarType(null);
        }

        return existingCar;
    }
}