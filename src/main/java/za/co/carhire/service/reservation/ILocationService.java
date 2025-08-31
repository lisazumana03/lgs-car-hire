package za.co.carhire.service.reservation;

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.service.IService;

import java.util.List;
import java.util.Set;

/**
 * Co-Author: Imtiyaaz Waggie 219374759
 * Author: Lisakhanya Zumana
 * Date: 2025
 */
public interface ILocationService extends IService<Location, Integer> {

    Set<Location> getLocations();
    void delete(int locationID);

    List<Location> getLocationsByCity(String city);
    List<Location> getLocationsByProvince(String province);
    List<Location> getLocationsByCountry(String country);
    List<Location> getLocationsByPostalCode(String postalCode);

    List<Location> searchLocations(String query);

    boolean isLocationActive(int locationID);
    List<Location> getActiveLocations();

    int getPickupCount(int locationID);
    int getDropOffCount(int locationID);
    Location getMostPopularPickupLocation();
    Location getMostPopularDropOffLocation();
}