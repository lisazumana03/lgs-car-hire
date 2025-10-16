package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.authentication.Role;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.CoverageType;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarStatus;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.repository.reservation.IInsuranceRepository;
import za.co.carhire.repository.reservation.ILocationRepository;
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.reservation.BookingPricingService;
import za.co.carhire.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Order(5)
public class BookingDataInitializer implements CommandLineRunner {

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICarRepository carRepository;

    @Autowired
    private ILocationRepository locationRepository;

    @Autowired
    private IInsuranceRepository insuranceRepository;

    @Autowired
    private BookingPricingService pricingService;

    @Value("${app.database.init.bookings.enabled:true}")
    private boolean initEnabled;

    @Value("${app.database.init.bookings.clear-existing:false}")
    private boolean clearExisting;

    private static final Random RANDOM = new Random();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!initEnabled) {
            System.out.println("Booking initialization is disabled. Set app.database.init.bookings.enabled=true to enable.");
            return;
        }

        List<Booking> existingBookings = bookingRepository.findAll();

        if (!existingBookings.isEmpty() && !clearExisting) {
            System.out.println("Database already contains " + existingBookings.size() + " bookings.");
            System.out.println("Skipping booking initialization. Set app.database.init.bookings.clear-existing=true to override.");
            return;
        }

        if (clearExisting && !existingBookings.isEmpty()) {
            System.out.println("Clearing existing bookings as requested...");
            bookingRepository.deleteAll();
            System.out.println("Existing bookings cleared.");
        }

        System.out.println("============================================");
        System.out.println("Initializing booking data...");
        System.out.println("============================================");

        createSampleBookings();

        System.out.println("============================================");
        System.out.println("Booking initialization completed successfully!");
        System.out.println("--------------------------------------------");
        System.out.println("Created " + bookingRepository.count() + " bookings");
        System.out.println("--------------------------------------------");
        displayBookingSummary();
        System.out.println("============================================");
    }

    private void createSampleBookings() {
        // Get required data
        List<User> customers = userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.CUSTOMER)
                .toList();

        List<Car> availableCars = carRepository.findAll();
        List<Location> locations = locationRepository.findAll();
        List<Insurance> insurancePolicies = insuranceRepository.findAll().stream()
                .filter(Insurance::isActive)
                .toList();

        if (customers.isEmpty()) {
            System.out.println("⚠ No customer users found. Skipping booking creation.");
            System.out.println("  Enable user initialization: app.database.init.users.create-samples=true");
            return;
        }

        if (availableCars.isEmpty()) {
            System.out.println("⚠ No cars found. Skipping booking creation.");
            System.out.println("  Enable vehicle initialization: app.database.init.enabled=true");
            return;
        }

        if (locations.isEmpty()) {
            System.out.println("⚠ No locations found. Skipping booking creation.");
            System.out.println("  Enable location initialization: app.database.init.locations.enabled=true");
            return;
        }

        System.out.println("Creating sample bookings with:");
        System.out.println("  * " + customers.size() + " customer(s)");
        System.out.println("  * " + availableCars.size() + " car(s)");
        System.out.println("  * " + locations.size() + " location(s)");
        System.out.println("  * " + insurancePolicies.size() + " active insurance policy/policies");
        System.out.println();

        List<Booking> bookings = new ArrayList<>();

        // Create past completed bookings
        if (customers.size() > 0 && availableCars.size() > 0) {
            bookings.add(createBooking(
                customers.get(0),
                availableCars.get(0),
                locations.get(0),
                locations.size() > 1 ? locations.get(1) : locations.get(0),
                insurancePolicies.isEmpty() ? null : insurancePolicies.get(0),
                LocalDateTime.now().minusDays(30),
                LocalDateTime.now().minusDays(27),
                LocalDateTime.now().minusDays(30),
                BookingStatus.CONFIRMED,
                "Past confirmed booking"
            ));
        }

        if (customers.size() > 1 && availableCars.size() > 1) {
            bookings.add(createBooking(
                customers.get(1),
                availableCars.get(1),
                locations.size() > 2 ? locations.get(2) : locations.get(0),
                locations.get(0),
                insurancePolicies.size() > 1 ? insurancePolicies.get(1) : null,
                LocalDateTime.now().minusDays(20),
                LocalDateTime.now().minusDays(17),
                LocalDateTime.now().minusDays(20),
                BookingStatus.CONFIRMED,
                "Past confirmed booking with different insurance"
            ));
        }

        // Create current active bookings
        if (customers.size() > 0 && availableCars.size() > 2) {
            bookings.add(createBooking(
                customers.get(0),
                availableCars.get(2),
                locations.get(0),
                locations.get(0),
                insurancePolicies.size() > 2 ? insurancePolicies.get(2) : null,
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().minusDays(2),
                BookingStatus.CONFIRMED,
                "Current active rental"
            ));
        }

        // Create future pending bookings
        if (customers.size() > 1 && availableCars.size() > 3) {
            bookings.add(createBooking(
                customers.get(1),
                availableCars.get(3),
                locations.size() > 1 ? locations.get(1) : locations.get(0),
                locations.size() > 2 ? locations.get(2) : locations.get(0),
                null, // No insurance selected
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(12),
                LocalDateTime.now(),
                BookingStatus.PENDING,
                "Future pending booking without insurance"
            ));
        }

        if (customers.size() > 2 && availableCars.size() > 4) {
            bookings.add(createBooking(
                customers.size() > 2 ? customers.get(2) : customers.get(0),
                availableCars.get(4),
                locations.get(0),
                locations.size() > 3 ? locations.get(3) : locations.get(0),
                insurancePolicies.size() > 3 ? insurancePolicies.get(3) : null,
                LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(14),
                LocalDateTime.now(),
                BookingStatus.PENDING,
                "Future pending booking - 1 week rental"
            ));
        }

        // Create cancelled booking
        if (customers.size() > 0 && availableCars.size() > 5) {
            bookings.add(createBooking(
                customers.get(0),
                availableCars.get(5),
                locations.get(0),
                locations.get(0),
                null,
                LocalDateTime.now().plusDays(10),
                LocalDateTime.now().plusDays(15),
                LocalDateTime.now().minusDays(1),
                BookingStatus.CANCELLED,
                "Cancelled booking"
            ));
        }

        // Create booking without insurance (customer's choice)
        if (customers.size() > 1 && availableCars.size() > 6) {
            bookings.add(createBooking(
                customers.get(1),
                availableCars.get(6),
                locations.size() > 1 ? locations.get(1) : locations.get(0),
                locations.get(0),
                null, // Customer declined insurance
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(8),
                LocalDateTime.now().minusDays(10),
                BookingStatus.CONFIRMED,
                "Past booking - customer declined insurance"
            ));
        }

        // Create long-term booking (30 days) with comprehensive insurance
        if (customers.size() > 2 && availableCars.size() > 7) {
            Insurance comprehensiveInsurance = insurancePolicies.stream()
                    .filter(i -> i.getCoverageType() == CoverageType.COMPREHENSIVE)
                    .findFirst()
                    .orElse(insurancePolicies.isEmpty() ? null : insurancePolicies.get(0));

            bookings.add(createBooking(
                customers.size() > 2 ? customers.get(2) : customers.get(0),
                availableCars.get(7),
                locations.get(0),
                locations.get(0),
                comprehensiveInsurance,
                LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(33),
                LocalDateTime.now(),
                BookingStatus.PENDING,
                "Long-term rental (30 days) with comprehensive insurance"
            ));
        }

        // Save all bookings
        System.out.println("Creating bookings...");
        for (Booking booking : bookings) {
            if (booking != null) {
                Booking saved = bookingRepository.save(booking);
                System.out.println("✓ Created booking #" + saved.getBookingID() +
                                 " - User: " + saved.getUser().getName() +
                                 " - Car: " + saved.getCar().getBrand() + " " + saved.getCar().getModel() +
                                 " - Status: " + saved.getBookingStatus() +
                                 " - Insurance: " + (saved.getInsurance() != null ?
                                     saved.getInsurance().getCoverageType() : "None") +
                                 " - Total: R" + String.format("%.2f", saved.getTotalAmount()));
            }
        }

        System.out.println("\n" + bookings.stream().filter(b -> b != null).count() +
                         " bookings created successfully.");
    }

    private Booking createBooking(User user, Car car, Location pickup, Location dropoff,
                                 Insurance insurance, LocalDateTime startDate,
                                 LocalDateTime endDate, LocalDateTime bookingDate,
                                 BookingStatus status, String description) {
        try {
            // Build booking without pricing first
            Booking booking = new Booking.Builder()
                    .setUser(user)
                    .setCar(car)
                    .setPickupLocation(pickup)
                    .setDropOffLocation(dropoff)
                    .setInsurance(insurance)
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .setBookingDateAndTime(bookingDate)
                    .setBookingStatus(status)
                    .setCurrency("ZAR")
                    .build();

            // Calculate pricing
            booking = pricingService.calculatePricing(booking);

            return booking;
        } catch (Exception e) {
            System.err.println("⚠ Failed to create booking: " + description);
            System.err.println("  Error: " + e.getMessage());
            return null;
        }
    }

    private void displayBookingSummary() {
        List<Booking> allBookings = bookingRepository.findAll();

        if (allBookings.isEmpty()) {
            System.out.println("No bookings created.");
            return;
        }

        // Count by status
        System.out.println("Bookings by Status:");
        allBookings.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    Booking::getBookingStatus,
                    java.util.stream.Collectors.counting()
                ))
                .forEach((status, count) ->
                    System.out.println("  * " + status + ": " + count)
                );

        System.out.println();

        // Count with/without insurance
        long withInsurance = allBookings.stream()
                .filter(b -> b.getInsurance() != null)
                .count();
        long withoutInsurance = allBookings.size() - withInsurance;

        System.out.println("Insurance Coverage:");
        System.out.println("  * With Insurance: " + withInsurance);
        System.out.println("  * Without Insurance: " + withoutInsurance);

        System.out.println();

        // Revenue statistics
        double totalRevenue = allBookings.stream()
                .filter(b -> b.getBookingStatus() == BookingStatus.CONFIRMED)
                .mapToDouble(Booking::getTotalAmount)
                .sum();

        double potentialRevenue = allBookings.stream()
                .filter(b -> b.getBookingStatus() == BookingStatus.PENDING)
                .mapToDouble(Booking::getTotalAmount)
                .sum();

        System.out.println("Revenue Statistics:");
        System.out.println("  * Confirmed Bookings Revenue: R" + String.format("%.2f", totalRevenue));
        System.out.println("  * Pending Bookings (Potential): R" + String.format("%.2f", potentialRevenue));

        System.out.println();

        // Popular locations
        System.out.println("Most Popular Pickup Locations:");
        allBookings.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    b -> b.getPickupLocation().getLocationName(),
                    java.util.stream.Collectors.counting()
                ))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .forEach(entry ->
                    System.out.println("  * " + entry.getKey() + ": " + entry.getValue() + " booking(s)")
                );
    }
}
