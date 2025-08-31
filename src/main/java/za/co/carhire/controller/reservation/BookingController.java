package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.dto.reservation.BookingDTO;
import za.co.carhire.mapper.reservation.BookingMapper;
import za.co.carhire.service.reservation.IBookingService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
Lisakhanya Zumana (230864821)
Date: 25/05/2025

Imtiyaaz Waggie 219374759
Updated: Added DTO support and proper integration
 */

@RestController
@RequestMapping("/api/booking")
// @CrossOrigin(origins = "http://localhost:3046")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<BookingDTO> create(@RequestBody BookingDTO bookingDto) {
        try {
            Booking booking = bookingService.createFullBooking(bookingDto);
            BookingDTO responseDto = BookingMapper.toDTO(booking);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BookingDTO> read(@PathVariable int id) {
        Booking booking = bookingService.read(id);
        if (booking != null) {
            BookingDTO dto = BookingMapper.toDTO(booking);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<BookingDTO> update(@RequestBody BookingDTO bookingDto) {
        Booking existingBooking = bookingService.read(bookingDto.getBookingID());
        if (existingBooking != null) {
            Booking updatedBooking = BookingMapper.updateEntityFromDTO(existingBooking, bookingDto);
            Booking savedBooking = bookingService.update(updatedBooking);
            BookingDTO responseDto = BookingMapper.toDTO(savedBooking);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        bookingService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<BookingDTO> cancel(@PathVariable int id) {
        try {
            Booking cancelledBooking = bookingService.cancelBooking(id);
            BookingDTO dto = BookingMapper.toDTO(cancelledBooking);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAll() {
        Set<Booking> bookings = bookingService.getBookings();
        List<BookingDTO> bookingDtos = bookings.stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getByUser(@PathVariable int userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        List<BookingDTO> bookingDtos = bookings.stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<BookingDTO>> getByCar(@PathVariable int carId) {
        List<Booking> bookings = bookingService.getBookingsByCar(carId);
        List<BookingDTO> bookingDtos = bookings.stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookingDTO>> getByStatus(@PathVariable String status) {
        List<Booking> bookings = bookingService.getBookingsByStatus(status);
        List<BookingDTO> bookingDtos = bookings.stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookingDtos, HttpStatus.OK);
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<BookingDTO> confirm(@PathVariable int id) {
        try {
            Booking confirmedBooking = bookingService.confirmBooking(id);
            BookingDTO dto = BookingMapper.toDTO(confirmedBooking);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/check-availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(@RequestBody Map<String, Object> request) {
        int carId = (int) request.get("carId");
        String startDate = (String) request.get("startDate");
        String endDate = (String) request.get("endDate");

        boolean available = bookingService.checkCarAvailability(carId, startDate, endDate);

        return new ResponseEntity<>(Map.of(
                "carId", carId,
                "available", available,
                "message", available ? "Car is available" : "Car is not available for selected dates"
        ), HttpStatus.OK);
    }
}