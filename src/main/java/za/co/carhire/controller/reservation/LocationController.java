package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.dto.reservation.LocationDTO;
import za.co.carhire.mapper.reservation.LocationMapper;
import za.co.carhire.service.reservation.ILocationService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
Lisakhanya Zumana (230864821)
Date: 25/05/2025

Updated: Added DTO support and /api prefix
 */

@RestController
@RequestMapping("/api/location")
// @CrossOrigin(origins = "http://localhost:3046")
public class LocationController {

    @Autowired
    private ILocationService locationService;

    @PostMapping("/create")
    public ResponseEntity<LocationDTO> create(@RequestBody LocationDTO locationDto) {
        Location location = LocationMapper.toEntity(locationDto);
        Location createdLocation = locationService.create(location);
        LocationDTO responseDto = LocationMapper.toDTO(createdLocation);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<LocationDTO> read(@PathVariable int id) {
        Location location = locationService.read(id);
        if (location != null) {
            LocationDTO dto = LocationMapper.toDTO(location);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<LocationDTO> update(@RequestBody LocationDTO locationDto) {
        Location existingLocation = locationService.read(locationDto.getLocationID());
        if (existingLocation != null) {
            Location updatedLocation = LocationMapper.updateEntityFromDTO(existingLocation, locationDto);
            Location savedLocation = locationService.update(updatedLocation);
            LocationDTO responseDto = LocationMapper.toDTO(savedLocation);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LocationDTO>> getAll() {
        Set<Location> locations = locationService.getLocations();
        List<LocationDTO> locationDtos = locations.stream()
                .map(LocationMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(locationDtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        locationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<LocationDTO>> getByCity(@PathVariable String city) {
        List<Location> locations = locationService.getLocationsByCity(city);
        List<LocationDTO> locationDtos = locations.stream()
                .map(LocationMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(locationDtos, HttpStatus.OK);
    }

    @GetMapping("/province/{province}")
    public ResponseEntity<List<LocationDTO>> getByProvince(@PathVariable String province) {
        List<Location> locations = locationService.getLocationsByProvince(province);
        List<LocationDTO> locationDtos = locations.stream()
                .map(LocationMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(locationDtos, HttpStatus.OK);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<LocationDTO>> getByCountry(@PathVariable String country) {
        List<Location> locations = locationService.getLocationsByCountry(country);
        List<LocationDTO> locationDtos = locations.stream()
                .map(LocationMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(locationDtos, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<LocationDTO>> searchLocations(@RequestParam String query) {
        List<Location> locations = locationService.searchLocations(query);
        List<LocationDTO> locationDtos = locations.stream()
                .map(LocationMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(locationDtos, HttpStatus.OK);
    }
}