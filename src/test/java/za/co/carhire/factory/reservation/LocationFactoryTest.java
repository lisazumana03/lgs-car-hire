package za.co.carhire.factory.reservation;

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.reservation.Location;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Lisakhanya Zumana (230864821)
Date: 13 May 2025
 */

class LocationFactoryTest {



    @Test
    void createLocation() {
        int locationID = 14;
        String locationName = "Langeberg Rentals";
        String streetName = "Robert Street";
        String city = "Kommetjie";
        String provinceOrState = "Western Cape";
        String country = "South Africa";
        String postalCode = "7345";
        List<Booking> pickUpLocations = new ArrayList<>();
        pickUpLocations.add(new Booking());
        List<Booking> dropOffLocations = new ArrayList<>();
        dropOffLocations.add(new Booking());

        Location location = LocationFactory.createLocation(locationID, locationName, streetName, city, provinceOrState, country, postalCode, pickUpLocations, dropOffLocations);

        System.out.println(location);
        assertNotNull(location);

    }
}