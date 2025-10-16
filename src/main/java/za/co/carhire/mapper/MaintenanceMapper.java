package za.co.carhire.mapper;

import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.MaintenanceDTO;

/**
 * Mapper for converting between Maintenance entity and DTO
 * Updated: 16/10/2025 - Added new fields mapping
 */
public class MaintenanceMapper {

    public static MaintenanceDTO toDTO(Maintenance maintenance) {
        return new MaintenanceDTO(
                maintenance.getMaintenanceID(),
                maintenance.getMaintenanceDate(),
                maintenance.getServiceType(),
                maintenance.getDescription(),
                maintenance.getCost(),
                maintenance.getMileageAtService(),
                maintenance.getNextServiceDate(),
                maintenance.getNextServiceMileage(),
                maintenance.getMechanicName(),
                maintenance.getStatus(),
                maintenance.getNotes(),
                maintenance.getCar() != null ? maintenance.getCar().getCarID() : 0
        );
    }

    public static Maintenance toEntity(MaintenanceDTO dto, Car car) {
        return new Maintenance.Builder()
                .setMaintenanceID(dto.maintenanceID())
                .setMaintenanceDate(dto.maintenanceDate())
                .setServiceType(dto.serviceType())
                .setDescription(dto.description())
                .setCost(dto.cost())
                .setMileageAtService(dto.mileageAtService())
                .setNextServiceDate(dto.nextServiceDate())
                .setNextServiceMileage(dto.nextServiceMileage())
                .setMechanicName(dto.mechanicName())
                .setStatus(dto.status())
                .setNotes(dto.notes())
                .setCar(car)
                .build();
    }
}
