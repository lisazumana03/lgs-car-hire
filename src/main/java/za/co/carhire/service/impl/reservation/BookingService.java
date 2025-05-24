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

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

}
