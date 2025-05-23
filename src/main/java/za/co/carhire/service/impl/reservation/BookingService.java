package za.co.carhire.service.impl.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.repository.reservation.IBookingRepository;
import za.co.carhire.service.reservation.IBookingService;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private IBookingRepository bookingRepository;
}
