/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 12/08/2025
 * */
package za.co.carhire.mapper;

import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.MaintenanceDTO;

public class MaintenanceMapper {

    public static MaintenanceDTO toDTO(Maintenance maintenance) {
        return new MaintenanceDTO(
                maintenance.getMaintenanceID(),
                maintenance.getMaintenanceDate(),
                maintenance.getDescription(),
                maintenance.getCost(),
                maintenance.getMechanicName(),
                maintenance.getCar() != null ? maintenance.getCar().getCarID() : 0
        );
    }

    public static Maintenance toEntity(MaintenanceDTO dto, Car car) {
        return new Maintenance.Builder()
                .setMaintenanceID(dto.maintenanceID())
                .setMaintenanceDate(dto.maintenanceDate())
                .setDescription(dto.description())
                .setCost(dto.cost())
                .setMechanicName(dto.mechanicName())
                .setCar(car)
                .build();
    }
}
