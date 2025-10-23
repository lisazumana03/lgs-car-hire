package za.co.carhire.controller.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.service.reservation.impl.LocationService;

import java.util.List;
import java.util.Set;

/**
 * Lisakhanya Zumana (230864821)
 * Date: 25/05/2025
 */

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Location> create(@RequestBody Location location) {
        return ResponseEntity.ok(locationService.create(location));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Location> read(@PathVariable int id) {
        Location location = locationService.read(id);
        if (location != null) {
            return ResponseEntity.ok(location);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")  // Changed from POST to PUT for consistency
    public ResponseEntity<Location> update(@RequestBody Location location) {
        Location updated = locationService.update(location);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Location>> getAll() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/location-set")
    public ResponseEntity<Set<Location>> readAll() {
        return ResponseEntity.ok(locationService.getLocations());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}