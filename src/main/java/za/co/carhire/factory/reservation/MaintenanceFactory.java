/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 17/05/2025
 * */
package za.co.carhire.factory.reservation;

import za.co.carhire.domain.reservation.Maintenance;
import java.util.Date;

public class MaintenanceFactory {

  public static Maintenance createMaintenance(int maintenanceID, int carID, Date serviceDate, String description,
      double cost, String status, String mechanic) {
    if (maintenanceID < 0 || maintenanceID > 100000) {
      return null;
    }
    if (serviceDate == null) {
      return null;
    }
    if (description == null || description.isEmpty()) {
      return null;
    }
    if (cost < 0) {
      return null;
    }
    if (mechanic == null || mechanic.isEmpty()) {
      return null;
    }
    return new Maintenance.Builder()
        .setMaintenanceID(maintenanceID)
        .setMaintenanceDate(serviceDate)
        .setDescription(description)
        .setCost(cost)
        .setMechanicName(mechanic)
        .build();
  }
}
