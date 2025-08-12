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
import za.co.carhire.service.reservation.impl.MaintenanceService;

@RestController
@RequestMapping("/maintenance")
@CrossOrigin(origins = "*")
public class MaintenanceController {
    @Autowired
    private MaintenanceService maintenanceService;

    @PostMapping("/create")
    public ResponseEntity<Maintenance> create(@RequestBody Maintenance maintenance) {
        return new ResponseEntity<>(maintenanceService.create(maintenance), HttpStatus.CREATED);
    }
    @GetMapping("/read/{id}")
    public Maintenance read(@PathVariable int id){
        return maintenanceService.read(id);
    }
    @PutMapping("/update")
    public Maintenance update(@RequestBody Maintenance maintenance){
        return maintenanceService.update(maintenance);
    }
    @DeleteMapping('/delete/{id}")
    public ResponseEntity<void> delete(@PathVariable int id) {
        maintenanceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping('/cancel/{id}")
    public ResponseEntity<Maintenance> cancel(@PathVariable int id) {
        maintenanceService.cancelMaintenance(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    }
