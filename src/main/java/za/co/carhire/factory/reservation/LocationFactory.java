package za.co.carhire.factory.reservation;

/*
Lisakhanya Zumana (230864821)
Date: 12 May 2025
 */

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.util.Helper;

public class LocationFactory {
    public static Location createLocation(int locationID, String locationName, String streetName, String city, String provinceOrState, String country, Short postalCode) {
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
                .build();
    }
}
