package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.factory.reservation.BookingFactory;
import za.co.carhire.service.impl.reservation.BookingService;

/*
Lisakhanya Zumana (230864821)
Date: 25/05/2025
 */

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:5173/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Booking> create(@RequestBody Booking booking) {
        return new ResponseEntity<>(bookingService.create(booking), HttpStatus.CREATED);
    }
    @GetMapping("/read{id}")
    public Booking read(@PathVariable int id){
        return bookingService.read(id);
    }

    @PostMapping("/update")
    public Booking update(@RequestBody Booking booking){
        return bookingService.update(booking);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        bookingService.delete(id);
    }

}
