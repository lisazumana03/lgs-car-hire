package za.co.carhire.repository.reservation;

/**
Lisakhanya Zumana (230864821)
Date: 19/05/2025
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;

import java.util.Optional;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Integer> {
  Optional<Booking> findByPickupLocation(Location pickupLocation);

  Optional<Booking> findByDropOffLocation(Location dropOffLocation);

  Optional<Booking> findBookingByCars(Car car);

  Optional<Booking> findById(int bookingID);
}
