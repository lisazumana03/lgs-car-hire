package za.co.carhire.factory.reservation;

/*
Lisakhanya Zumana (230864821)
Date: 12 May 2025
 */

import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.util.Helper;

import java.util.List;

public class LocationFactory {
    public static Location createLocation(int locationID, String locationName, String streetName, String city, String provinceOrState, String country, Short postalCode, List<Car>cars, List<Booking>pickUpLocations, List<Booking>dropOffLocations) {
        if(Helper.isNullOrEmpty(locationName)){
            return null;
        }
        return new Location.Builder()
                .setLocationID(locationID)
                .setLocationName(locationName)
                .setStreetName(streetName)
                .setCity(city)
                .setProvinceOrState(provinceOrState)
                .setCountry(country)
                .setPostalCode(postalCode)
                .setCars(cars)
                .setPickUpLocations(pickUpLocations)
                .setDropOffLocations(dropOffLocations)
                .build();
    }
}
