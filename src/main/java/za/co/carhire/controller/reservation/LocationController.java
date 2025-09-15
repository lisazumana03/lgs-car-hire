package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.service.reservation.impl.LocationService;

import java.util.List;
import java.util.Set;

/**
Lisakhanya Zumana (230864821)
Date: 25/05/2025
 */

@RestController
@RequestMapping("/api/location")
//@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/create")
    public ResponseEntity<Location> create(@RequestBody Location location){
        return ResponseEntity.ok(locationService.create(location));
    }

    @GetMapping("/read/{id}")
    public Location read(@PathVariable int id){
        return locationService.read(id);
    }

    @PostMapping("/update")
    public Location update(@RequestBody Location location){
        return locationService.update(location);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Location>> getAll(){
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/location-set")
    public ResponseEntity<Set<Location>> readAll(){
        return ResponseEntity.ok(locationService.getLocations());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
