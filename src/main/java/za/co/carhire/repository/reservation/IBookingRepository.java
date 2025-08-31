package za.co.carhire.repository.reservation;

/*
    Lisakhanya Zumana (230864821)
    Date: 19/05/2025
    Updated - Imtiyaaz Waggie 219374759
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.vehicle.Car;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findBookingByCar(Car car);

    Optional<Booking> findFirstByPickupLocation(Location pickupLocation);

    Optional<Booking> findFirstByDropOffLocation(Location dropOffLocation);

    Optional<Booking> findById(int bookingID);

    List<Booking> findAll();

    List<Booking> findByUser(User user);

    List<Booking> findByBookingStatus(BookingStatus status);

    List<Booking> findByCar(Car car);

    List<Booking> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Booking> findByEndDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Booking> findByPickupLocation(Location pickupLocation);

    List<Booking> findByDropOffLocation(Location dropOffLocation);

    @Query("SELECT b FROM Booking b WHERE b.car = :car " +
            "AND b.bookingStatus != 'CANCELLED' " +
            "AND ((b.startDate <= :endDate AND b.endDate >= :startDate))")
    List<Booking> findConflictingBookings(@Param("car") Car car,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    long countByBookingStatus(BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.user = :user " +
            "AND b.bookingStatus = 'CONFIRMED' " +
            "AND b.endDate >= :currentDate")
    List<Booking> findActiveBookingsByUser(@Param("user") User user,
                                           @Param("currentDate") LocalDateTime currentDate);
}