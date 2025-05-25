package za.co.carhire.service.impl.reservation;

import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.service.reservation.ILocationService;

import java.util.List;

/*
Lisakhanya Zumana (230864821)
Date: 24/05/2025
 */

@Service
public class LocationService implements ILocationService {
    @Override
    public List<Location> getLocations() {
        return List.of();
    }

    @Override
    public Location create(Location location) {
        return null;
    }

    @Override
    public Location read(Integer integer) {
        return null;
    }

    @Override
    public Location update(Location location) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }
}
