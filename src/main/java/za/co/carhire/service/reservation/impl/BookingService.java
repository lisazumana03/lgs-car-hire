package za.co.carhire.service.reservation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.reservation.BookingDTO;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.repository.reservation.IInsuranceRepository;
import za.co.carhire.repository.reservation.ILocationRepository;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.reservation.IBookingService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*
Enhanced Booking Service Implementation
Author: Lisakhanya Zumana / Fixed Integration
Date: 24/05/2025 / Updated 2025
Updated: Full integration with Car, User, Insurance, and Location
 */

@Service
@Transactional
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ICarRepository carRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IInsuranceRepository insuranceRepository;

    @Autowired
    private ILocationRepository locationRepository;

    @Override
    public Set<Booking> getBookings() {
        return bookingRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public Booking create(Booking booking) {
        validateBooking(booking);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking createFullBooking(BookingDTO bookingDto) {
        // Validate and fetch all related entities
        User user = userRepository.findById(bookingDto.getUserID())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Car car = carRepository.findById(bookingDto.getCarID())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        // Check car availability
        if (!car.isAvailability()) {
            throw new IllegalArgumentException("Car is not available");
        }

        Location pickupLocation = locationRepository.findById(bookingDto.getPickupLocationID())
                .orElseThrow(() -> new IllegalArgumentException("Pickup location not found"));

        Location dropOffLocation = locationRepository.findById(bookingDto.getDropOffLocationID())
                .orElseThrow(() -> new IllegalArgumentException("Drop-off location not found"));

        // Optional: Add insurance if provided
        Insurance insurance = null;
        if (bookingDto.getInsuranceID() != null) {
            insurance = insuranceRepository.findById(bookingDto.getInsuranceID())
                    .orElse(null);
        }

        // Calculate total cost
        double totalCost = calculateTotalCost(car, insurance,
                bookingDto.getStartDate(), bookingDto.getEndDate());

        // Create the booking
        Booking booking = new Booking.Builder()
                .setUser(user)
                .setCar(car)
                .setInsurance(insurance)
                .setBookingDateAndTime(LocalDateTime.now())
                .setStartDate(bookingDto.getStartDate())
                .setEndDate(bookingDto.getEndDate())
                .setPickupLocation(pickupLocation)
                .setDropOffLocation(dropOffLocation)
                .setBookingStatus(BookingStatus.PENDING)
                .setTotalCost(totalCost)
                .build();

        // Update car availability
        car.setAvailability(false);
        carRepository.save(car);

        return bookingRepository.save(booking);
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
        if (this.bookingRepository.existsById(booking.getBookingID())) {
            return this.bookingRepository.save(booking);
        }
        return null;
    }

    @Override
    public void delete(int bookingID) {
        // Release car when booking is deleted
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingID);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            if (booking.getCar() != null) {
                Car car = booking.getCar();
                car.setAvailability(true);
                carRepository.save(car);
            }
        }
        bookingRepository.deleteById(bookingID);
    }

    @Override
    public Booking cancelBooking(int bookingID) {
        Booking booking = bookingRepository.findById(bookingID)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }

        if (booking.getStartDate().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new IllegalStateException("Cannot cancel booking less than 24 hours before start");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);

        // Release the car
        if (booking.getCar() != null) {
            Car car = booking.getCar();
            car.setAvailability(true);
            carRepository.save(car);
        }

        return bookingRepository.save(booking);
    }

    @Override
    public Booking confirmBooking(int bookingID) {
        Booking booking = bookingRepository.findById(bookingID)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Only pending bookings can be confirmed");
        }

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByUser(int userId) {
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getUser() != null &&
                        booking.getUser().getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByCar(int carId) {
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getCar() != null &&
                        booking.getCar().getCarID() == carId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByStatus(String status) {
        BookingStatus bookingStatus = BookingStatus.valueOf(status.toUpperCase());
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getBookingStatus() == bookingStatus)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByLocation(int locationId) {
        return bookingRepository.findAll().stream()
                .filter(booking ->
                        (booking.getPickupLocation() != null &&
                                booking.getPickupLocation().getLocationID() == locationId) ||
                                (booking.getDropOffLocation() != null &&
                                        booking.getDropOffLocation().getLocationID() == locationId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getActiveBookings() {
        LocalDateTime now = LocalDateTime.now();
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getBookingStatus() == BookingStatus.CONFIRMED &&
                        booking.getStartDate().isBefore(now) &&
                        booking.getEndDate().isAfter(now))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getUpcomingBookings() {
        LocalDateTime now = LocalDateTime.now();
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getBookingStatus() == BookingStatus.CONFIRMED &&
                        booking.getStartDate().isAfter(now))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getPastBookings() {
        LocalDateTime now = LocalDateTime.now();
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getEndDate().isBefore(now))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkCarAvailability(int carId, String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);

        List<Booking> conflictingBookings = getConflictingBookings(carId, startDate, endDate);
        return conflictingBookings.isEmpty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getConflictingBookings(int carId, String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);

        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getCar() != null &&
                        booking.getCar().getCarID() == carId &&
                        booking.getBookingStatus() != BookingStatus.CANCELLED &&
                        isOverlapping(booking.getStartDate(), booking.getEndDate(), start, end))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public double calculateTotalRevenue(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);

        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getBookingStatus() == BookingStatus.CONFIRMED &&
                        booking.getBookingDateAndTime().isAfter(start) &&
                        booking.getBookingDateAndTime().isBefore(end))
                .mapToDouble(Booking::getTotalCost)
                .sum();
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalBookingsCount(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);

        return (int) bookingRepository.findAll().stream()
                .filter(booking -> booking.getBookingDateAndTime().isAfter(start) &&
                        booking.getBookingDateAndTime().isBefore(end))
                .count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByDateRange(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);

        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getBookingDateAndTime().isAfter(start) &&
                        booking.getBookingDateAndTime().isBefore(end))
                .collect(Collectors.toList());
    }

    // Helper methods
    private void validateBooking(Booking booking) {
        if (booking.getUser() == null) {
            throw new IllegalArgumentException("User is required");
        }
        if (booking.getCar() == null) {
            throw new IllegalArgumentException("Car is required");
        }
        if (booking.getStartDate() == null || booking.getEndDate() == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        if (booking.getStartDate().isAfter(booking.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (booking.getStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }
    }

    private boolean isOverlapping(LocalDateTime start1, LocalDateTime end1,
                                  LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

    private double calculateTotalCost(Car car, Insurance insurance,
                                      LocalDateTime startDate, LocalDateTime endDate) {
        long days = Duration.between(startDate, endDate).toDays();
        if (days < 1) days = 1;

        double totalCost = car.getRentalPrice() * days;

        if (insurance != null) {
            totalCost += insurance.getInsuranceCost();
        }

        return totalCost;
    }
}