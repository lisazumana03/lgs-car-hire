package za.co.carhire.repository.reservation;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
Updated: 16/10/2025 - Added query methods for maintenance records
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.domain.reservation.MaintenanceStatus;
import za.co.carhire.domain.reservation.MaintenanceType;

import java.util.Date;
import java.util.List;

@Repository
public interface IMaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    // Find all maintenance records for a specific car
    List<Maintenance> findByCarCarID(int carId);

    // Find maintenance records by status
    List<Maintenance> findByStatus(MaintenanceStatus status);

    // Find maintenance records by service type
    List<Maintenance> findByServiceType(MaintenanceType serviceType);

    // Find maintenance records for a car by status
    List<Maintenance> findByCarCarIDAndStatus(int carId, MaintenanceStatus status);

    // Find maintenance records within a date range
    List<Maintenance> findByMaintenanceDateBetween(Date startDate, Date endDate);

    // Find upcoming scheduled maintenance
    @Query("SELECT m FROM Maintenance m WHERE m.status = 'SCHEDULED' AND m.maintenanceDate >= CURRENT_DATE ORDER BY m.maintenanceDate ASC")
    List<Maintenance> findUpcomingScheduledMaintenance();

    // Find overdue maintenance (scheduled but date has passed)
    @Query("SELECT m FROM Maintenance m WHERE m.status = 'SCHEDULED' AND m.maintenanceDate < CURRENT_DATE")
    List<Maintenance> findOverdueMaintenance();

    // Find cars due for next service
    @Query("SELECT m FROM Maintenance m WHERE m.nextServiceDate IS NOT NULL AND m.nextServiceDate <= :date")
    List<Maintenance> findCarsDueForService(Date date);
}
