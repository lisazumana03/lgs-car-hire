package za.co.carhire.service.impl.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.repository.reservation.ILocationRepository;
import za.co.carhire.service.reservation.impl.LocationService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
        Location location = new Location.Builder()
                .setLocationID(15)
                .setLocationName("Location")
                .setCity("Cape Town")
                .setProvinceOrState("Western Cape")
                .setCountry("South Africa")
                .build();
    }

    @Test
    void getLocations() {
        List<Location> locations = Arrays.asList(location);
        when(locationRepository.findAll()).thenReturn(locations);

        Set<Location> result = locationService.getLocations();
        assertEquals(locations.size(), result.size());
        assertTrue(result.containsAll(locations));
    }

    @Test
    void create() {
        when(locationRepository.save(location)).thenReturn(location);
        assertEquals(location, locationService.create(location));
    }

    @Test
    void read() {
        when(locationRepository.findById(location.getLocationID())).thenReturn(Optional.of(location));
        assertEquals(location, locationService.read(location.getLocationID()));
    }

    @Test
    void update() {
        when(locationRepository.findById(location.getLocationID())).thenReturn(Optional.of(location));
        when(locationRepository.save(location)).thenReturn(location);
        assertEquals(location, locationService.update(location));
    }

    @Test
    void delete() {
        locationRepository.delete(location);
        verify(locationRepository).delete(location);
    }
}
