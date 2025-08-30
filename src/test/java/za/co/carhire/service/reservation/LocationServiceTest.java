package za.co.carhire.service.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.factory.reservation.LocationFactory;
import za.co.carhire.repository.reservation.ILocationRepository;
import za.co.carhire.service.reservation.impl.LocationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/*
Lisakhanya Zumana (230864821)
Date: 25/05/2025
 */

class LocationServiceTest {
    @InjectMocks
    private LocationService locationService;

    @Mock
    private ILocationRepository locationRepository;

    private Location location;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        location = LocationFactory.createLocation(location.getLocationID(), location.getLocationName(), location.getStreetName(), location.getCityOrTown(), location.getProvinceOrState(), location.getCountry(), location.getPostalCode(), location.getPickUpLocations(), location.getDropOffLocations());
    }

    @Test
    void getLocations() {
    }

    @Test
    void create() {
        when(locationRepository.save(location)).thenReturn(location);
        assertEquals(location, locationService.create(location));
    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}