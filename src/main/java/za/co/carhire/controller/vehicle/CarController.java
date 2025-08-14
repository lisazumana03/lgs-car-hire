package za.co.carhire.controller.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.service.vehicle.ICarService;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private ICarService carService;

    @PostMapping("/create")
    public Car create(@RequestBody Car car) {
        return carService.create(car);
    }

    @GetMapping("/read/{id}")
    public Car read(@PathVariable int id) {
        return carService.read(id);
    }

    @PostMapping("/update")
    public Car update(@RequestBody Car car) {
        return carService.update(car);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        carService.delete(id);
    }
}