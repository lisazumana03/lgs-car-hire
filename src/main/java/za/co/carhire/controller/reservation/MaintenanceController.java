package za.co.carhire.controller.reservation;
/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 12/08/2025
 * */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.dto.MaintenanceDTO;
import za.co.carhire.service.reservation.impl.MaintenanceService;

import java.util.List;

@RestController
@RequestMapping("/maintenance")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    // Create Maintenance
    @PostMapping("/create")
    public ResponseEntity<MaintenanceDTO> create(@RequestBody MaintenanceDTO dto) {
        MaintenanceDTO created = maintenanceService.create(dto);
        return ResponseEntity.ok(created);
    }

    // Read Maintenance by ID
    @GetMapping("/read/{id}")
    public ResponseEntity<MaintenanceDTO> read(@PathVariable int id) {
        return maintenanceService.read(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all Maintenances
    @GetMapping("/all")
    public ResponseEntity<List<MaintenanceDTO>> getAll() {
        List<MaintenanceDTO> all = maintenanceService.getAll();
        return ResponseEntity.ok(all);
    }

    // Update Maintenance
    @PutMapping("/update/{id}")
    public ResponseEntity<MaintenanceDTO> update(@PathVariable int id, @RequestBody MaintenanceDTO dto) {
        MaintenanceDTO updated = maintenanceService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete Maintenance
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        maintenanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    
}
