/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 12/08/2025
 * */
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
