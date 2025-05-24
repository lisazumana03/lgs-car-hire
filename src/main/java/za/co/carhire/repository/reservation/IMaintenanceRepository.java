package za.co.carhire.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.carhire.domain.reservation.Maintence;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Integer> {
}