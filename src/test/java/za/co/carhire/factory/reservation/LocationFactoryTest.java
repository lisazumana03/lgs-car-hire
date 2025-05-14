package za.co.carhire.factory.reservation;

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.Location;

import static org.junit.jupiter.api.Assertions.*;

/*
Lisakhanya Zumana (230864821)
Date: 13 May 2025
 */

class LocationFactoryTest {



    @Test
    void createLocation() {
        Location location01 = LocationFactory.createLocation(1, "Beaufort West Rentals", "Donkin Street", "Beaufort West", "Western Cape", "South Africa", (short)6970);
        assertNotNull(location01);
        System.out.println(location01);

        Location location02 = LocationFactory.createLocation(5, "Goldnerville Rentals", "Van Riebeeck St", "Laingsburg", "Western Cape", "South Africa", (short)6900);
        System.out.println(location02);
        assertNotNull(location02);

        Location location03 = LocationFactory.createLocation(7, "Miyazaki Rentals", "Iketani Road", "Kobayashi", "Miyazaki Prefecture", "Japan", (short)6980);
        System.out.println(location03);
        assertNotNull(location03);
    }
}