package za.co.carhire.service.reservation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.repository.reservation.ILocationRepository;
import za.co.carhire.service.reservation.IBookingService;

/**
Lisakhanya Zumana (230864821)
Date: 24/05/2025
 */

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ILocationRepository locationRepository;

    @Override
    public Set<Booking> getBookings() {
        return bookingRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public Booking create(Booking booking) {
        // Resolve location names to location IDs if needed
        booking = resolveLocations(booking);
        return bookingRepository.save(booking);
    }

    private Booking resolveLocations(Booking booking) {
        Location pickupLocation = booking.getPickupLocation();
        Location dropOffLocation = booking.getDropOffLocation();
        boolean needsRebuild = false;

        // If pickupLocation has name but no ID, look it up or create it
        if (pickupLocation != null &&
                pickupLocation.getLocationID() == 0 &&
                pickupLocation.getLocationName() != null) {
            final String pickupName = pickupLocation.getLocationName();
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
            final String dropOffName = dropOffLocation.getLocationName();
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
                .orElse(null);

        assert booking != null;
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("You already cancelled this booking!");
        }

        if (booking.getBookingDateAndTime().isBefore(LocalDateTime.now().plusMinutes(45))) {
            throw new IllegalStateException("You are too late to cancel this booking!");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

}
