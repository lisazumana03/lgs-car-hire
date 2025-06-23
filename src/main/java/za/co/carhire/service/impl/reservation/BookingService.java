package za.co.carhire.service.impl.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.service.reservation.IBookingService;

/*
Lisakhanya Zumana (230864821)
Date: 24/05/2025
 */

import java.awt.print.Book;
import java.time.LocalDateTime;
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

    public void delete(int bookingID){
        bookingRepository.deleteById(bookingID);
    }

    @Override
    public Booking cancelBooking(int bookingID){
        Booking booking = bookingRepository.findById(bookingID)
                .orElse(null);

        assert booking != null;
        if(booking.getBookingStatus() == BookingStatus.CANCELLED){
            throw new IllegalStateException("You already cancelled this booking!");
        }

        if(booking.getBookingDateAndTime().isBefore(LocalDateTime.now().plusMinutes(45))){
            throw new IllegalStateException("You are too late to cancel this booking!");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

}
