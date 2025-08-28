package za.co.carhire.mapper.vehicle;

import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.dto.vehicle.CarTypeDTO;

/**
 * Mapper class for converting between CarType entity and CarTypeDTO
 * Author: Imtiyaaz Waggie 219374759
 * Date: 28/08/2025
 */
public class CarTypeMapper {
    
    /**
     * Convert CarType entity to CarTypeDTO
     * @param carType the entity to convert
     * @return the DTO representation
     */
    public static CarTypeDTO toDTO(CarType carType) {
        if (carType == null) {
            return null;
        }
        
        CarTypeDTO.Builder builder = new CarTypeDTO.Builder()
                .setCarTypeID(carType.getCarTypeID())
                .setType(carType.getType())
                .setFuelType(carType.getFuelType())
                .setNumberOfWheels(carType.getNumberOfWheels())
                .setNumberOfSeats(carType.getNumberOfSeats());
        
        if (carType.getCar() != null) {
            Car car = carType.getCar();
            builder.setCarID(car.getCarID());
            String brandModel = car.getBrand() + " " + car.getModel();
            builder.setCarBrandModel(brandModel);
        }
        
        return builder.build();
    }
    
    /**
     * Convert CarTypeDTO to CarType entity (without relationships)
     * @param dto the DTO to convert
     * @return the entity representation
     */
    public static CarType toEntity(CarTypeDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new CarType.Builder()
                .setCarTypeID(dto.getCarTypeID())
                .setType(dto.getType())
                .setFuelType(dto.getFuelType())
                .setNumberOfWheels(dto.getNumberOfWheels())
                .setNumberOfSeats(dto.getNumberOfSeats())
                .build();
    }
    
    /**
     * Update existing CarType entity from CarTypeDTO
     * @param existingCarType the existing entity to update
     * @param dto the DTO with new values
     * @return the updated entity
     */
    public static CarType updateEntityFromDTO(CarType existingCarType, CarTypeDTO dto) {
        if (existingCarType == null || dto == null) {
            return existingCarType;
        }
        
        existingCarType.setType(dto.getType());
        existingCarType.setFuelType(dto.getFuelType());
        existingCarType.setNumberOfWheels(dto.getNumberOfWheels());
        existingCarType.setNumberOfSeats(dto.getNumberOfSeats());
        
        return existingCarType;
    }
}