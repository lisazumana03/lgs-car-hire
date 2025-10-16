package za.co.carhire.controller.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.service.authentication.UserService;
import za.co.carhire.service.reservation.impl.BookingService;
import za.co.carhire.util.DateTimeUtil;

/**
 * Lisakhanya Zumana (230864821)
 * Date: 25/05/2025
 */

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    /**
     * Create booking with full Booking object
     * Automatically fetches User if only userId is provided
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Booking booking) {
        try {
            if (booking.getCar() == null) {
                return ResponseEntity.badRequest().body("Car is required");
            }
            if (booking.getStartDate() == null) {
                return ResponseEntity.badRequest().body("Start date is required");
            }
            if (booking.getEndDate() == null) {
                return ResponseEntity.badRequest().body("End date is required");
            }
            if (booking.getStartDate().isAfter(booking.getEndDate())) {
                return ResponseEntity.badRequest().body("Start date must be before end date");
            }
            if (DateTimeUtil.isPast(booking.getStartDate())) {
                return ResponseEntity.badRequest().body("Start date cannot be in the past");
            }
            if (booking.getUser() == null) {
                return ResponseEntity.badRequest().body("User is required");
            }

            // If booking has a user with only userId set, fetch the full user and rebuild
            // with Builder
            if (booking.getUser() != null && booking.getUser().getUserId() != null) {
                User fullUser = userService.read(booking.getUser().getUserId());
                if (fullUser == null) {
                    return ResponseEntity.badRequest().body("User not found");
                }

                // Rebuild booking with the full user using Builder pattern
                booking = new Booking.Builder()
                        .copy(booking) // Copy all existing fields
                        .setUser(fullUser) // Replace partial user with full user
                        .build();
            }

            Booking createdBooking = bookingService.create(booking);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error creating booking: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the booking");
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Booking> read(@PathVariable int id) {
        Booking booking = bookingService.read(id);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<Booking> update(@RequestBody Booking booking) {
        Booking updatedBooking = bookingService.update(booking);
        if (updatedBooking != null) {
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        bookingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Booking> cancel(@PathVariable int id) {
        Booking cancelledBooking = bookingService.cancelBooking(id);
        if (cancelledBooking != null) {
            return new ResponseEntity<>(cancelledBooking, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAll() {
        return ResponseEntity.ok(bookingService.getBookings().stream().toList());
    }

}
