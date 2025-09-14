package za.co.carhire.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;

/**
Lisakhanya Zumana (230864821)
Date: 19/05/2025
 */

import java.util.List;
import java.util.Optional;

@Repository
public interface ILocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findByPickUpLocations(List<Booking> pickupLocation);
    List<Location> findByDropOffLocations(List<Booking> dropOffLocations);
}
