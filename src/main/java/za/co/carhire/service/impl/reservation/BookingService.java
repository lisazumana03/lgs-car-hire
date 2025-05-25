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

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public Set<Booking> getBookings() {
        return Set.of();
    }

    @Override
    public List<Booking> getBookingsByStatus(String bookingStatus) {
        return List.of();
    }

    @Override
    public Booking create(Booking booking) {
        return null;
    }

    @Override
    public Booking read(Integer integer) {
        return null;
    }

    @Override
    public Booking update(Booking booking) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }
}
