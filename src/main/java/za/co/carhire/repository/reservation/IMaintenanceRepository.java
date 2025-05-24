package za.co.carhire.repository.reservation;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.carhire.domain.reservation.Maintenance;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Integer> {
}
