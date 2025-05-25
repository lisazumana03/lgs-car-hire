package za.co.carhire.service.reservation;

import jakarta.persistence.SecondaryTable;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.service.IService;

import java.util.List;
import java.util.Set;

public interface ILocationService {
    Set<Location> getLocations();

    Location create(Location location);
    Location read(int locationID);
    Location update(Location location);
    void delete(int locationID);
}
