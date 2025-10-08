package za.co.carhire.dto;

import java.util.Date;

public record MaintenanceDTO(
        int maintenanceID,
        Date maintenanceDate,
        String description,
        double cost,
        String mechanicName,
        int carId
) {
}
