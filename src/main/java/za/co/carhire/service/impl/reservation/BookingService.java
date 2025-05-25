package za.co.carhire.service.impl.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.service.reservation.IBookingService;

/*
Lisakhanya Zumana (230864821)
Date: 24/05/2025
 */

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public Set<Booking> getBookings() {
        return bookingRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public Booking create(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking read(int bookingID) {
        return bookingRepository.findById(bookingID).orElse(null);
    }

    @Override
    public Booking update(Booking booking) {
        if(this.bookingRepository.existsById(booking.getBookingID()))
            return this.bookingRepository.save(booking);
        return null;
    }

    @Override
    public void delete(int bookingID) {
        bookingRepository.deleteById(bookingID);
    }
}
