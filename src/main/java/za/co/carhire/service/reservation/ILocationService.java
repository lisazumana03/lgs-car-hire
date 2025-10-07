package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.service.IService;

import java.util.List;
import java.util.Set;

public interface ILocationService extends IService<Location, Integer> {
    Set<Location> getLocations();
    void delete(int locationID);
}
