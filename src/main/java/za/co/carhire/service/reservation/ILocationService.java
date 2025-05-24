package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.service.IService;

import java.util.List;

public interface ILocationService {
    Location createLocation(Location location);
    Location readLocation(int locationID);
    Location updateLocation(Location location);
    void deleteLocation(int locationID);
    List<Location> getLocations();
}
