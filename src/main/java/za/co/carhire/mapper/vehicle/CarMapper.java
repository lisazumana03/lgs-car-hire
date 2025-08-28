package za.co.carhire.mapper.vehicle;

import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.dto.vehicle.CarDTO;

/**
 * Mapper class for converting between Car entity and CarDTO
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 */
public class CarMapper {
    
    /**
     * Convert Car entity to CarDTO
     * @param car the entity to convert
     * @return the DTO representation
     */
    public static CarDTO toDTO(Car car) {
        if (car == null) {
            return null;
        }
        
        return new CarDTO.Builder()
                .setCarID(car.getCarID())
                .setModel(car.getModel())
                .setBrand(car.getBrand())
                .setYear(car.getYear())
                .setAvailability(car.isAvailability())
                .setRentalPrice(car.getRentalPrice())
                .setCarTypeID(car.getCarType() != null ? car.getCarType().getCarTypeID() : null)
                .setCarTypeName(car.getCarType() != null ? car.getCarType().getType() : null)
                .setInsuranceID(car.getInsurance() != null ? car.getInsurance().getInsuranceID() : null)
                .setBookingID(car.getBooking() != null ? car.getBooking().getBookingID() : null)
                .build();
    }
    
    /**
     * Convert CarDTO to Car entity
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
                .build();
    }
    
    /**
     * Update existing Car entity from CarDTO
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
        
        return existingCar;
    }
}