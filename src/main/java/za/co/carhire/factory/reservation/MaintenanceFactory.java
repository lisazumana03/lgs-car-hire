/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 17/05/2025
 * Updated: 16/10/2025 - Added comprehensive factory methods for enhanced Maintenance entity
 * */
package za.co.carhire.factory.reservation;

import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.domain.reservation.MaintenanceStatus;
import za.co.carhire.domain.reservation.MaintenanceType;
import za.co.carhire.domain.vehicle.Car;
import java.util.Date;

public class MaintenanceFactory {

  @Deprecated
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

  public static Maintenance buildMaintenance(
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
      Car car) {

    // Validation
    if (maintenanceDate == null) {
      return null;
    }
    if (serviceType == null) {
      return null;
    }
    if (description == null || description.isEmpty()) {
      return null;
    }
    if (cost < 0) {
      return null;
    }
    if (mileageAtService < 0) {
      return null;
    }
    if (mechanicName == null || mechanicName.isEmpty()) {
      return null;
    }
    if (status == null) {
      return null;
    }
    if (car == null) {
      return null;
    }

    return new Maintenance.Builder()
        .setMaintenanceID(maintenanceID)
        .setMaintenanceDate(maintenanceDate)
        .setServiceType(serviceType)
        .setDescription(description)
        .setCost(cost)
        .setMileageAtService(mileageAtService)
        .setNextServiceDate(nextServiceDate)
        .setNextServiceMileage(nextServiceMileage)
        .setMechanicName(mechanicName)
        .setStatus(status)
        .setNotes(notes)
        .setCar(car)
        .build();
  }

  // Simplified factory method for new maintenance records (without ID)
  public static Maintenance createMaintenance(
      Date maintenanceDate,
      MaintenanceType serviceType,
      String description,
      double cost,
      int mileageAtService,
      String mechanicName,
      MaintenanceStatus status,
      Car car) {

    return buildMaintenance(
        0, // ID will be auto-generated
        maintenanceDate,
        serviceType,
        description,
        cost,
        mileageAtService,
        null, // No next service date
        null, // No next service mileage
        mechanicName,
        status,
        null, // No notes
        car
    );
  }

  // Factory method with next service scheduling
  public static Maintenance createMaintenanceWithScheduling(
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
      Car car) {

    return buildMaintenance(
        0, // ID will be auto-generated
        maintenanceDate,
        serviceType,
        description,
        cost,
        mileageAtService,
        nextServiceDate,
        nextServiceMileage,
        mechanicName,
        status,
        notes,
        car
    );
  }
}
