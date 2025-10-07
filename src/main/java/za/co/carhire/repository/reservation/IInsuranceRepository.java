package za.co.carhire.repository.reservation;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Insurance;

import java.util.List;

@Repository
public interface IInsuranceRepository extends JpaRepository<Insurance, Integer> {
    Insurance findByPolicyNumber(long policyNumber);
    List<Insurance> findByStatus(String status);
}
