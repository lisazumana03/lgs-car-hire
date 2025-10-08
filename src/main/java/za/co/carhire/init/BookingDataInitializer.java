package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.service.authentication.UserService;
import za.co.carhire.service.reservation.IBookingService;
import za.co.carhire.service.reservation.ILocationService;
import za.co.carhire.service.vehicle.ICarService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
    Imtiyaaz Waggie 219374759
    Date: 09/10/2025
*/

@Component
@Order(5) // Run after LocationDataInitializer (Order 4)
public class BookingDataInitializer implements CommandLineRunner {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private ILocationService locationService;

    @Autowired
    private ICarService carService;

    @Autowired
    private UserService userService;

    @Value("${app.init.create-sample-bookings:true}")
    private boolean createSampleBookings;

    @Override
    public void run(String... args) throws Exception {
        if (!createSampleBookings) {
            System.out.println("Sample booking creation is disabled.");
            return;
        }

        // Check if bookings already exist
        if (!bookingService.getBookings().isEmpty()) {
            System.out.println("Bookings already exist. Skipping sample data creation.");
            return;
        }

        System.out.println("============================================");
        System.out.println("Creating sample booking data...");
        System.out.println("============================================");

        int bookingCount = createSampleBookings();

        System.out.println("Sample booking data created successfully!");
        System.out.println("Total Bookings: " + bookingCount);
        System.out.println("============================================");
    }

    private int createSampleBookings() {
        // Get locations
        Set<Location> locationSet = locationService.getLocations();
        List<Location> locations = new ArrayList<>(locationSet);

        if (locations.isEmpty()) {
            System.out.println("No locations available for bookings. Please run LocationDataInitializer first.");
            return 0;
        }

        // Get cars
        Set<Car> carSet = carService.getCars();
        List<Car> cars = new ArrayList<>(carSet);

        if (cars.isEmpty()) {
            System.out.println("No cars available for bookings. Please run VehicleDataInitializer first.");
            return 0;
        }

        // Get users
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            System.out.println("No users available for bookings. Please run UserDataInitializer first.");
            return 0;
        }

        int bookingCount = 0;

        // Booking 1: Active booking - User renting Toyota Corolla
        if (cars.size() > 0 && users.size() > 1) {
            Booking booking1 = new Booking.Builder()
                    .setUser(users.get(1)) // Test User
                    .setCar(List.of(cars.get(0))) // Toyota Corolla
                    .setBookingDateAndTime(LocalDateTime.now().minusDays(2))
                    .setStartDate(LocalDateTime.now().plusDays(1))
                    .setEndDate(LocalDateTime.now().plusDays(5))
                    .setPickupLocation(locations.get(0)) // Cape Town Airport
                    .setDropOffLocation(locations.get(1)) // V&A Waterfront
                    .setBookingStatus(BookingStatus.CONFIRMED)
                    .build();
            bookingService.create(booking1);

            // Update car to link to booking
            cars.get(0).setBooking(booking1);
            cars.get(0).setAvailability(false);
            carService.update(cars.get(0));

            bookingCount++;
            System.out.println("  ✓ Created booking: " + users.get(1).getName() + " - " +
                cars.get(0).getBrand() + " " + cars.get(0).getModel() + " (CONFIRMED)");
        }

        // Booking 2: Multi-car booking - Admin renting BMW and Honda
        if (cars.size() > 2 && users.size() > 0) {
            Booking booking2 = new Booking.Builder()
                    .setUser(users.get(0)) // Admin User
                    .setCar(List.of(cars.get(2), cars.get(1))) // BMW 3 Series and Honda CR-V
                    .setBookingDateAndTime(LocalDateTime.now().minusDays(5))
                    .setStartDate(LocalDateTime.now().plusDays(3))
                    .setEndDate(LocalDateTime.now().plusDays(10))
                    .setPickupLocation(locations.get(2)) // OR Tambo Airport
                    .setDropOffLocation(locations.get(3)) // Sandton City
                    .setBookingStatus(BookingStatus.CONFIRMED)
                    .build();
            bookingService.create(booking2);

            // Update cars to link to booking
            cars.get(1).setBooking(booking2);
            cars.get(1).setAvailability(false);
            carService.update(cars.get(1));

            cars.get(2).setBooking(booking2);
            cars.get(2).setAvailability(false);
            carService.update(cars.get(2));

            bookingCount++;
            System.out.println("  ✓ Created booking: " + users.get(0).getName() +
                " - BMW 3 Series & Honda CR-V (CONFIRMED)");
        }

        // Booking 3: Pending booking
        if (cars.size() > 3 && users.size() > 1) {
            Booking booking3 = new Booking.Builder()
                    .setUser(users.get(1))
                    .setCar(List.of(cars.get(3))) // VW Polo
                    .setBookingDateAndTime(LocalDateTime.now().minusHours(3))
                    .setStartDate(LocalDateTime.now().plusDays(7))
                    .setEndDate(LocalDateTime.now().plusDays(14))
                    .setPickupLocation(locations.get(4)) // Durban Airport
                    .setDropOffLocation(locations.get(5)) // Durban Beach
                    .setBookingStatus(BookingStatus.PENDING)
                    .build();
            bookingService.create(booking3);
            bookingCount++;
            System.out.println("  ✓ Created booking: " + users.get(1).getName() + " - " +
                cars.get(3).getBrand() + " " + cars.get(3).getModel() + " (PENDING)");
        }

        // Booking 4: Booked (past booking)
        if (cars.size() > 4 && users.size() > 0) {
            Booking booking4 = new Booking.Builder()
                    .setUser(users.get(0))
                    .setCar(List.of(cars.get(4))) // Tesla Model 3
                    .setBookingDateAndTime(LocalDateTime.now().minusDays(20))
                    .setStartDate(LocalDateTime.now().minusDays(15))
                    .setEndDate(LocalDateTime.now().minusDays(10))
                    .setPickupLocation(locations.get(0)) // Cape Town Airport
                    .setDropOffLocation(locations.get(0)) // Same location
                    .setBookingStatus(BookingStatus.BOOKED)
                    .build();
            bookingService.create(booking4);
            bookingCount++;
            System.out.println("  ✓ Created booking: " + users.get(0).getName() + " - " +
                cars.get(4).getBrand() + " " + cars.get(4).getModel() + " (BOOKED)");
        }

        // Booking 5: Cancelled booking
        if (cars.size() > 5 && users.size() > 1) {
            Booking booking5 = new Booking.Builder()
                    .setUser(users.get(1))
                    .setCar(List.of(cars.get(5))) // Toyota Prius
                    .setBookingDateAndTime(LocalDateTime.now().minusDays(8))
                    .setStartDate(LocalDateTime.now().minusDays(5))
                    .setEndDate(LocalDateTime.now().minusDays(2))
                    .setPickupLocation(locations.get(6)) // Hatfield
                    .setDropOffLocation(locations.get(7)) // Stellenbosch
                    .setBookingStatus(BookingStatus.CANCELLED)
                    .build();
            bookingService.create(booking5);
            bookingCount++;
            System.out.println("  ✓ Created booking: " + users.get(1).getName() + " - " +
                cars.get(5).getBrand() + " " + cars.get(5).getModel() + " (CANCELLED)");
        }

        return bookingCount;
    }
}
