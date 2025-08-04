package za.co.carhire.controller.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.service.vehicle.ICarTypeService;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/car-types")
public class CarTypeController {

    @Autowired
    private ICarTypeService carTypeService;

    @PostMapping
    public ResponseEntity<CarType> create(@RequestBody CarType carType) {
        CarType createdCarType = carTypeService.create(carType);
        return new ResponseEntity<>(createdCarType, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarType> read(@PathVariable int id) {
        CarType carType = carTypeService.read(id);
        if (carType != null) {
            return new ResponseEntity<>(carType, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarType> update(@PathVariable int id, @RequestBody CarType carType) {
        CarType existingCarType = carTypeService.read(id);
        if (existingCarType != null) {
            CarType updatedCarType = carTypeService.update(carType);
            return new ResponseEntity<>(updatedCarType, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        CarType existingCarType = carTypeService.read(id);
        if (existingCarType != null) {
            carTypeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
