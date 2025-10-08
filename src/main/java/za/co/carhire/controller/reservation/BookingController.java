package za.co.carhire.controller.reservation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.dto.reservation.BookingDTO;
import za.co.carhire.mapper.reservation.BookingMapper;
import za.co.carhire.service.reservation.impl.BookingService;

/**
Lisakhanya Zumana (230864821)
Date: 25/05/2025
Updated: 08/10/2025 - Added DTO support for better API responses
 */

@RestController
@RequestMapping("/api/booking")
//@CrossOrigin(origins = "*")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<BookingDTO> create(@RequestBody Booking booking) {
        Booking created = bookingService.create(booking);
        return new ResponseEntity<>(BookingMapper.toDTO(created), HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BookingDTO> read(@PathVariable int id){
        Booking booking = bookingService.read(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PutMapping("/update")
    public ResponseEntity<BookingDTO> update(@RequestBody Booking booking){
        Booking updated = bookingService.update(booking);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(BookingMapper.toDTO(updated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        bookingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<BookingDTO> cancel(@PathVariable int id){
        Booking cancelled = bookingService.cancelBooking(id);
        return ResponseEntity.ok(BookingMapper.toDTO(cancelled));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAll() {
        List<BookingDTO> bookings = bookingService.getBookings().stream()
                .map(BookingMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookings);
    }

}
