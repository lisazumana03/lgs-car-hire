package za.co.carhire.service.reservation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarStatus;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.repository.reservation.ILocationRepository;
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.reservation.BookingPricingService;
import za.co.carhire.service.reservation.IBookingService;
import za.co.carhire.util.DateTimeUtil;

/**
Lisakhanya Zumana (230864821)
Date: 24/05/2025
 */

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ILocationRepository locationRepository;

    @Autowired
    private BookingPricingService pricingService;

    @Autowired
    private ICarRepository carRepository;

    @Override
    public Set<Booking> getBookings() {
        return bookingRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public Booking create(Booking booking) {
        // Validate car availability
        validateCarAvailability(booking);

        // Resolve location names to location IDs if needed
        booking = resolveLocations(booking);

        // Calculate pricing
        booking = pricingService.calculatePricing(booking);

        // Mark car as rented if booking is confirmed
        if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
            updateCarStatus(booking.getCar(), CarStatus.RENTED);
        }

        return bookingRepository.save(booking);
    }

    /**
     * Validate that the car is available for the requested dates
     */
    private void validateCarAvailability(Booking booking) {
        Car car = booking.getCar();

        if (car == null) {
            throw new IllegalArgumentException("Booking must have a car assigned");
        }

        // Check if car exists in database
        Car existingCar = carRepository.findById(car.getCarID()).orElse(null);
        if (existingCar == null) {
            throw new IllegalArgumentException("Car with ID " + car.getCarID() + " does not exist");
        }

        // Check if car is available
        if (existingCar.getStatus() != CarStatus.AVAILABLE && existingCar.getStatus() != CarStatus.RESERVED) {
            throw new IllegalStateException("Car is not available for booking. Current status: " + existingCar.getStatus());
        }

        // Check for overlapping bookings
        List<Booking> overlappingBookings = findOverlappingBookings(
                car.getCarID(),
                booking.getStartDate(),
                booking.getEndDate()
        );

        if (!overlappingBookings.isEmpty()) {
            throw new IllegalStateException("Car is already booked for the selected dates");
        }
    }

    /**
     * Find bookings that overlap with the requested date range for a specific car
     * Uses optimized database query instead of loading all bookings into memory
     */
    private List<Booking> findOverlappingBookings(int carId, LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findOverlappingBookings(
                carId,
                startDate,
                endDate,
                BookingStatus.CANCELLED
        );
    }

    /**
     * Update car status
     */
    private void updateCarStatus(Car car, CarStatus newStatus) {
        Car existingCar = carRepository.findById(car.getCarID()).orElse(null);
        if (existingCar != null) {
            existingCar.setStatus(newStatus);
            carRepository.save(existingCar);
        }
    }

    /**
     * Normalize location name to prevent duplicates
     * Converts to lowercase and trims whitespace
     */
    private String normalizeLocationName(String locationName) {
        if (locationName == null) {
            return null;
        }
        return locationName.trim().toLowerCase();
    }

    private Booking resolveLocations(Booking booking) {
        Location pickupLocation = booking.getPickupLocation();
        Location dropOffLocation = booking.getDropOffLocation();
        boolean needsRebuild = false;

        // If pickupLocation has name but no ID, look it up or create it
        if (pickupLocation != null &&
                pickupLocation.getLocationID() == 0 &&
                pickupLocation.getLocationName() != null) {
            final String pickupName = normalizeLocationName(pickupLocation.getLocationName());
            pickupLocation = locationRepository
                    .findByLocationName(pickupName)
                    .orElseGet(() -> {
                        // Create new location if it doesn't exist
                        Location newLocation = new Location.Builder()
                                .setLocationName(pickupName)
                                .build();
                        return locationRepository.save(newLocation);
                    });
            needsRebuild = true;
        }

        // If dropOffLocation has name but no ID, look it up or create it
        if (dropOffLocation != null &&
                dropOffLocation.getLocationID() == 0 &&
                dropOffLocation.getLocationName() != null) {
            final String dropOffName = normalizeLocationName(dropOffLocation.getLocationName());
            dropOffLocation = locationRepository
                    .findByLocationName(dropOffName)
                    .orElseGet(() -> {
                        // Create new location if it doesn't exist
                        Location newLocation = new Location.Builder()
                                .setLocationName(dropOffName)
                                .build();
                        return locationRepository.save(newLocation);
                    });
            needsRebuild = true;
        }

        // Rebuild booking with resolved locations if needed
        if (needsRebuild) {
            return new Booking.Builder()
                    .copy(booking)
                    .setPickupLocation(pickupLocation)
                    .setDropOffLocation(dropOffLocation)
                    .build();
        }

        return booking;
    }

    @Override
    public Booking read(int bookingID) {
        return bookingRepository.findById(bookingID).orElse(null);
    }

    @Override
    public Booking read(Integer bookingID) {
        return bookingRepository.findById(bookingID).orElse(null);
    }

    @Override
    public Booking update(Booking booking) {
        if (this.bookingRepository.existsById(booking.getBookingID()))
            return this.bookingRepository.save(booking);
        return null;
    }

    @Override
    public void delete(int bookingID) {
        bookingRepository.deleteById(bookingID);
    }

    @Override
    public Booking cancelBooking(int bookingID) {
        Booking booking = bookingRepository.findById(bookingID)
                .orElseThrow(() -> new IllegalArgumentException("Booking with ID " + bookingID + " does not exist"));

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("You already cancelled this booking!");
        }

        // Use timezone-aware comparison for cancellation deadline
        LocalDateTime cancellationDeadline = booking.getBookingDateAndTime().plusMinutes(45);
        if (DateTimeUtil.isPast(cancellationDeadline)) {
            throw new IllegalStateException("You are too late to cancel this booking!");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);

        // Return car to available status when booking is cancelled
        if (booking.getCar() != null) {
            updateCarStatus(booking.getCar(), CarStatus.AVAILABLE);
        }

        return bookingRepository.save(booking);
    }

}
