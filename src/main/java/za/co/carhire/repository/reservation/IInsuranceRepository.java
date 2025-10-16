package za.co.carhire.repository.reservation;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
Updated: 2025-10-16 - Fixed query methods to match new Insurance entity
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.CoverageType;
import za.co.carhire.domain.reservation.Insurance;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInsuranceRepository extends JpaRepository<Insurance, Integer> {


    Optional<Insurance> findByPolicyNumber(String policyNumber);

    List<Insurance> findByCoverageType(CoverageType coverageType);

    List<Insurance> findByIsActive(boolean isActive);

    List<Insurance> findByInsuranceProvider(String insuranceProvider);

    List<Insurance> findByCoverageTypeAndIsActive(CoverageType coverageType, boolean isActive);
}
