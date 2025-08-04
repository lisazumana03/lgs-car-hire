package za.co.carhire.controller.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.service.vehicle.ICarService;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/cars")

public class CarController {

    @Autowired
    private ICarService carService;

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody Car car) {
        Car createdCar = carService.create(car);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> read(@PathVariable int id) {
        Car car = carService.read(id);
        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> update(@PathVariable int id, @RequestBody Car car) {
        Car existingCar = carService.read(id);
        if (existingCar != null) {
            Car updatedCar = carService.update(car);
            return new ResponseEntity<>(updatedCar, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Car existingCar = carService.read(id);
        if (existingCar != null) {
            carService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}