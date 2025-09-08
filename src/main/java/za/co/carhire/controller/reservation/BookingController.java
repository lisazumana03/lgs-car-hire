package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.service.reservation.impl.BookingService;

/**
Lisakhanya Zumana (230864821)
Date: 25/05/2025
 */

@RestController
@RequestMapping("/booking")
//@CrossOrigin(origins = "*")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Booking> create(@RequestBody Booking booking) {
        return new ResponseEntity<>(bookingService.create(booking), HttpStatus.CREATED);
    }
    @GetMapping("/read/{id}")
    public Booking read(@PathVariable int id){
        return bookingService.read(id);
    }

    @PutMapping("/update")
    public ResponseEntity<Booking> update(@RequestBody Booking booking){
        return new ResponseEntity<>(bookingService.update(booking), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        bookingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Booking> cancel(@PathVariable int id){
        bookingService.cancelBooking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
