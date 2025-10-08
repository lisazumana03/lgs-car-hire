package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.dto.reservation.LocationDTO;
import za.co.carhire.mapper.reservation.LocationMapper;
import za.co.carhire.service.reservation.impl.LocationService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
Lisakhanya Zumana (230864821)
Date: 25/05/2025
Updated: 08/10/2025 - Added DTO support for better API responses
 */

@RestController
@RequestMapping("/api/location")
//@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/create")
    public ResponseEntity<LocationDTO> create(@RequestBody Location location){
        Location created = locationService.create(location);
        return ResponseEntity.ok(LocationMapper.toDTO(created));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<LocationDTO> read(@PathVariable int id){
        Location location = locationService.read(id);
        if (location == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(LocationMapper.toDTO(location));
    }

    @PutMapping("/update")
    public ResponseEntity<LocationDTO> update(@RequestBody Location location){
        Location updated = locationService.update(location);
        if(updated == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(LocationMapper.toDTO(updated));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LocationDTO>> getAll(){
        List<LocationDTO> locations = locationService.getAllLocations().stream()
                .map(LocationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/location-set")
    public ResponseEntity<Set<LocationDTO>> readAll(){
        Set<LocationDTO> locations = locationService.getLocations().stream()
                .map(LocationMapper::toDTO)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(locations);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
