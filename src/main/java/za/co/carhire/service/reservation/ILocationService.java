package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.service.IService;

import java.util.List;

public interface ILocationService extends IService <Location, Integer> {
    List<Location> getLocations();
}
