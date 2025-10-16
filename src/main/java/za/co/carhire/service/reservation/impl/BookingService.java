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

        // Handle pickup location
        if (pickupLocation != null) {
            if (pickupLocation.getLocationID() == 0) {
                // New location from Google Maps - create it
                pickupLocation = handleMapLocation(pickupLocation);
                needsRebuild = true;
            }
            // If locationID exists, use the existing location (already set)
        }

        // Handle dropoff location
        if (dropOffLocation != null) {
            if (dropOffLocation.getLocationID() == 0) {
                // New location from Google Maps - create it
                dropOffLocation = handleMapLocation(dropOffLocation);
                needsRebuild = true;
            }
            // If locationID exists, use the existing location (already set)
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

    /**
     * Handle location from Google Maps - create new location in database
     * @param location Location object with Google Maps data
     * @return Saved location with generated ID
     */
    private Location handleMapLocation(Location location) {
        System.out.println("Creating new location from map: " + location.getLocationName());
        
        // Create new location with all the details from Google Maps
        Location newLocation = new Location.Builder()
                .setLocationName(location.getLocationName())
                .setStreetName(location.getStreetName())
                .setStreetNumber(location.getStreetNumber())
                .setCity(location.getCityOrTown())
                .setProvinceOrState(location.getProvinceOrState())
                .setCountry(location.getCountry())
                .setPostalCode(location.getPostalCode())
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
                .setFullAddress(location.getFullAddress())
                .build();
        
        Location savedLocation = locationRepository.save(newLocation);
        System.out.println("Location saved with ID: " + savedLocation.getLocationID());
        return savedLocation;
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
