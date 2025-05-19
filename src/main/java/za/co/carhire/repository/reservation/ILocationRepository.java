package za.co.carhire.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;

/*
Lisakhanya Zumana (230864821)
Date: 19/05/2025
 */

import java.util.List;

@Repository
public interface ILocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByPickupLocation(Location pickupLocation);
    List<Location> findByDropOffLocation(Location dropOffLocation);
    List<Location> findByCar(Car car);
    List<Location> findByStatus(String bookingStatus);
}
