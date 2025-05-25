package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.service.IService;

import java.util.List;

public interface ILocationService {
    List<Location> getLocations();

    Location create(Location location);
    Location read(int locationID);
    Location update(Location location);
    void delete(int locationID);
}
