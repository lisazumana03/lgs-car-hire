package za.co.carhire.service.reservation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.repository.reservation.ILocationRepository;
import za.co.carhire.service.reservation.ILocationService;

import java.util.*;
import java.util.stream.Collectors;

/*
Lisakhanya Zumana (230864821)
Date: 24/05/2025
Updated: Complete implementation with all interface methods
 */

@Service
@Transactional
public class LocationService implements ILocationService {

    @Autowired
    private ILocationRepository locationRepository;

    @Override
    public Set<Location> getLocations() {
        return locationRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public Location create(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        return locationRepository.save(location);
    }

    @Override
    @Transactional(readOnly = true)
    public Location read(Integer locationID) {
        return locationRepository.findById(locationID).orElse(null);
    }

    @Override
    public Location update(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        if (this.locationRepository.existsById(location.getLocationID())) {
            return this.locationRepository.save(location);
        }
        return null;
    }

    @Override
    public void delete(int locationID) {
        locationRepository.deleteById(locationID);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getLocationsByCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return locationRepository.findAll().stream()
                .filter(location -> location.getCityOrTown() != null &&
                        location.getCityOrTown().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getLocationsByProvince(String province) {
        if (province == null || province.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return locationRepository.findAll().stream()
                .filter(location -> location.getProvinceOrState() != null &&
                        location.getProvinceOrState().equalsIgnoreCase(province))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getLocationsByCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return locationRepository.findAll().stream()
                .filter(location -> location.getCountry() != null &&
                        location.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getLocationsByPostalCode(String postalCode) {
        if (postalCode == null || postalCode.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return locationRepository.findAll().stream()
                .filter(location -> location.getPostalCode() != null &&
                        location.getPostalCode().equals(postalCode))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> searchLocations(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchTerm = query.toLowerCase().trim();

        return locationRepository.findAll().stream()
                .filter(location ->
                        (location.getLocationName() != null &&
                                location.getLocationName().toLowerCase().contains(searchTerm)) ||
                                (location.getStreetName() != null &&
                                        location.getStreetName().toLowerCase().contains(searchTerm)) ||
                                (location.getCityOrTown() != null &&
                                        location.getCityOrTown().toLowerCase().contains(searchTerm)) ||
                                (location.getProvinceOrState() != null &&
                                        location.getProvinceOrState().toLowerCase().contains(searchTerm)) ||
                                (location.getCountry() != null &&
                                        location.getCountry().toLowerCase().contains(searchTerm)) ||
                                (location.getPostalCode() != null &&
                                        location.getPostalCode().contains(searchTerm)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLocationActive(int locationID) {
        // A location is considered active if it exists and has a valid name
        Optional<Location> location = locationRepository.findById(locationID);
        return location.isPresent() &&
                location.get().getLocationName() != null &&
                !location.get().getLocationName().trim().isEmpty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Location> getActiveLocations() {
        // Return all locations that are considered active
        return locationRepository.findAll().stream()
                .filter(location -> location.getLocationName() != null &&
                        !location.getLocationName().trim().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public int getPickupCount(int locationID) {
        Optional<Location> location = locationRepository.findById(locationID);
        if (location.isPresent() && location.get().getPickUpLocations() != null) {
            return location.get().getPickUpLocations().size();
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = true)
    public int getDropOffCount(int locationID) {
        Optional<Location> location = locationRepository.findById(locationID);
        if (location.isPresent() && location.get().getDropOffLocations() != null) {
            return location.get().getDropOffLocations().size();
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Location getMostPopularPickupLocation() {
        List<Location> allLocations = locationRepository.findAll();

        if (allLocations.isEmpty()) {
            return null;
        }

        Location mostPopular = null;
        int maxPickups = 0;

        for (Location location : allLocations) {
            int pickupCount = (location.getPickUpLocations() != null) ?
                    location.getPickUpLocations().size() : 0;
            if (pickupCount > maxPickups) {
                maxPickups = pickupCount;
                mostPopular = location;
            }
        }

        return mostPopular;
    }

    @Override
    @Transactional(readOnly = true)
    public Location getMostPopularDropOffLocation() {
        List<Location> allLocations = locationRepository.findAll();

        if (allLocations.isEmpty()) {
            return null;
        }

        Location mostPopular = null;
        int maxDropOffs = 0;

        for (Location location : allLocations) {
            int dropOffCount = (location.getDropOffLocations() != null) ?
                    location.getDropOffLocations().size() : 0;
            if (dropOffCount > maxDropOffs) {
                maxDropOffs = dropOffCount;
                mostPopular = location;
            }
        }

        return mostPopular;
    }
}