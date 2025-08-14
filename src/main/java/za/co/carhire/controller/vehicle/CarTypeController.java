package za.co.carhire.controller.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.service.vehicle.ICarTypeService;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

@RestController
@RequestMapping("/cartype")
public class CarTypeController {
    @Autowired
    private ICarTypeService carTypeService;

    @PostMapping("/create")
    public CarType create(@RequestBody CarType carType) {
        return carTypeService.create(carType);
    }

    @GetMapping("/read/{id}")
    public CarType read(@PathVariable int id) {
        return carTypeService.read(id);
    }

    @PostMapping("/update")
    public CarType update(@RequestBody CarType carType) {
        return carTypeService.update(carType);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        carTypeService.delete(id);
    }
}