package za.co.carhire.dto;

import za.co.carhire.domain.reservation.MaintenanceStatus;
import za.co.carhire.domain.reservation.MaintenanceType;

import java.util.Date;

public record MaintenanceDTO(
        int maintenanceID,
        Date maintenanceDate,
        MaintenanceType serviceType,
        String description,
        double cost,
        int mileageAtService,
        Date nextServiceDate,
        Integer nextServiceMileage,
        String mechanicName,
        MaintenanceStatus status,
        String notes,
        int carId
) {
}
