package za.co.carhire.repository.reservation;

/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
Updated: 30/08/2025 - Added query methods
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IInsuranceRepository extends JpaRepository<Insurance, Integer> {

    Optional<Insurance> findByPolicyNumber(long policyNumber);
    List<Insurance> findByStatus(String status);
    List<Insurance> findByInsuranceProvider(String provider);
    List<Insurance> findByMechanic(String mechanic);

    Optional<Insurance> findByCar(Car car);
    Optional<Insurance> findByCarCarID(int carID);

    List<Insurance> findByInsuranceStartDateBetween(Date startDate, Date endDate);
    List<Insurance> findByInsuranceStartDateAfter(Date date);
    List<Insurance> findByInsuranceStartDateBefore(Date date);

    List<Insurance> findByInsuranceCostBetween(double minCost, double maxCost);
    List<Insurance> findByInsuranceCostLessThan(double cost);
    List<Insurance> findByInsuranceCostGreaterThan(double cost);

    List<Insurance> findByStatusAndInsuranceProvider(String status, String provider);
    List<Insurance> findByStatusAndInsuranceCostBetween(String status, double minCost, double maxCost);

    @Query("SELECT i FROM Insurance i WHERE i.status = 'ACTIVE' AND i.insuranceStartDate <= :currentDate")
    List<Insurance> findActiveInsurances(@Param("currentDate") Date currentDate);

    @Query("SELECT i FROM Insurance i WHERE i.car IS NULL")
    List<Insurance> findUnassignedInsurances();

    @Query("SELECT i FROM Insurance i WHERE i.car IS NOT NULL")
    List<Insurance> findAssignedInsurances();

    @Query("SELECT COUNT(i) FROM Insurance i WHERE i.status = :status")
    long countByStatus(@Param("status") String status);

    @Query("SELECT AVG(i.insuranceCost) FROM Insurance i WHERE i.status = 'ACTIVE'")
    Double findAverageActiveInsuranceCost();

    @Query("SELECT i FROM Insurance i WHERE i.insuranceProvider = :provider ORDER BY i.insuranceCost ASC")
    List<Insurance> findByProviderOrderByCost(@Param("provider") String provider);

    boolean existsByPolicyNumber(long policyNumber);
    boolean existsByCarCarID(int carID);
}