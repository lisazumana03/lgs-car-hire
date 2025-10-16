package za.co.carhire.repository.reservation;

/**
Lisakhanya Zumana (230864821)
Date: 19/05/2025
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Integer> {
  Optional<Booking> findByPickupLocation(Location pickupLocation);

  Optional<Booking> findByDropOffLocation(Location dropOffLocation);

  Optional<Booking> findBookingByCar(Car car);

  Optional<Booking> findById(int bookingID);

  @Query("SELECT b FROM Booking b WHERE b.car.carID = :carId " +
         "AND b.bookingStatus != :cancelledStatus " +
         "AND b.startDate < :endDate " +
         "AND b.endDate > :startDate")
  List<Booking> findOverlappingBookings(
      @Param("carId") int carId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      @Param("cancelledStatus") BookingStatus cancelledStatus
  );
}
