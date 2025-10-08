package za.co.carhire.controller.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.vehicle.CarDTO;
import za.co.carhire.mapper.vehicle.CarMapper;
import za.co.carhire.service.vehicle.ICarService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
Updated: 28/08/2025 - Added /api prefix to endpoints
 */

@RestController
@RequestMapping("/api/car")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class CarController {
    @Autowired
    private ICarService carService;

    @PostMapping("/create")
    public ResponseEntity<CarDTO> create(@RequestBody CarDTO carDto) {
        Car car = CarMapper.toEntity(carDto);
        Car createdCar = carService.create(car);
        CarDTO responseDto = CarMapper.toDTO(createdCar);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CarDTO> read(@PathVariable int id) {
        Car car = carService.read(id);
        if (car != null) {
            CarDTO dto = CarMapper.toDTO(car);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<CarDTO> update(@RequestBody CarDTO carDto) {
        Car existingCar = carService.read(carDto.getCarID());
        if (existingCar != null) {
            Car updatedCar = CarMapper.updateEntityFromDTO(existingCar, carDto);
            Car savedCar = carService.update(updatedCar);
            CarDTO responseDto = CarMapper.toDTO(savedCar);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        carService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarDTO>> getAll() {
        Set<Car> cars = carService.getCars();
        List<CarDTO> carDtos = cars.stream()
                .map(CarMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(carDtos, HttpStatus.OK);
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<CarDTO>> getByBrand(@PathVariable String brand) {
        List<Car> cars = carService.getCarsByBrand(brand);
        List<CarDTO> carDtos = cars.stream()
                .map(CarMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(carDtos, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<CarDTO>> getAvailableCars() {
        List<Car> cars = carService.getAvailableCars();
        List<CarDTO> carDtos = cars.stream()
                .map(CarMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(carDtos, HttpStatus.OK);
    }

    @PutMapping("/availability/{id}")
    public ResponseEntity<CarDTO> updateAvailability(@PathVariable int id, @RequestParam boolean available) {
        Car car = carService.updateAvailability(id, available);
        if (car != null) {
            CarDTO dto = CarMapper.toDTO(car);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<CarDTO>> getByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<Car> cars = carService.getCarsByPriceRange(minPrice, maxPrice);
        List<CarDTO> carDtos = cars.stream()
                .map(CarMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(carDtos, HttpStatus.OK);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<CarDTO>> getByYear(@PathVariable int year) {
        List<Car> cars = carService.getCarsByYear(year);
        List<CarDTO> carDtos = cars.stream()
                .map(CarMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(carDtos, HttpStatus.OK);
    }
}