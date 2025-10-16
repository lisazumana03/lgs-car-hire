package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.dto.reservation.LocationDTO;
import za.co.carhire.mapper.reservation.LocationMapper;
import za.co.carhire.service.reservation.impl.LocationService;

import java.util.List;
import java.util.stream.Collectors;

/**
Lisakhanya Zumana (230864821)
Date: 25/05/2025
Updated: 2025-10-16 - Added DTO support
 */

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/create")
    public ResponseEntity<LocationDTO> create(@RequestBody LocationDTO locationDTO){
        Location location = LocationMapper.toEntity(locationDTO);
        Location createdLocation = locationService.create(location);
        LocationDTO responseDTO = LocationMapper.toDTO(createdLocation);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<LocationDTO> read(@PathVariable int id){
        Location location = locationService.read(id);
        if (location != null) {
            LocationDTO dto = LocationMapper.toDTO(location);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/update")
    public ResponseEntity<LocationDTO> update(@RequestBody LocationDTO locationDTO){
        Location existingLocation = locationService.read(locationDTO.getLocationID());
        if (existingLocation != null) {
            Location updatedLocation = LocationMapper.updateEntityFromDTO(existingLocation, locationDTO);
            Location savedLocation = locationService.update(updatedLocation);
            LocationDTO responseDTO = LocationMapper.toDTO(savedLocation);
            return ResponseEntity.ok(responseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<LocationDTO>> getAll(){
        List<Location> locations = locationService.getAllLocations();
        List<LocationDTO> locationDTOs = locations.stream()
                .map(LocationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(locationDTOs);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        Location location = locationService.read(id);
        if (location != null) {
            locationService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
