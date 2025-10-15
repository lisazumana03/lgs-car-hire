package za.co.carhire.controller.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarStatus;
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
Updated: 15/10/2025 - Refactored for new Car model
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

    @PutMapping("/status/{id}")
    public ResponseEntity<CarDTO> updateStatus(@PathVariable int id, @RequestParam String status) {
        try {
            CarStatus carStatus = CarStatus.valueOf(status.toUpperCase());
            Car car = carService.updateStatus(id, carStatus);
            if (car != null) {
                CarDTO dto = CarMapper.toDTO(car);
                return new ResponseEntity<>(dto, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/mileage/{id}")
    public ResponseEntity<CarDTO> updateMileage(@PathVariable int id, @RequestParam int mileage) {
        Car car = carService.updateMileage(id, mileage);
        if (car != null) {
            CarDTO dto = CarMapper.toDTO(car);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CarDTO>> getByStatus(@PathVariable String status) {
        try {
            CarStatus carStatus = CarStatus.valueOf(status.toUpperCase());
            List<Car> cars = carService.getCarsByStatus(carStatus);
            List<CarDTO> carDtos = cars.stream()
                    .map(CarMapper::toDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(carDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/license-plate/{licensePlate}")
    public ResponseEntity<CarDTO> getByLicensePlate(@PathVariable String licensePlate) {
        Car car = carService.getCarByLicensePlate(licensePlate);
        if (car != null) {
            CarDTO dto = CarMapper.toDTO(car);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/vin/{vin}")
    public ResponseEntity<CarDTO> getByVin(@PathVariable String vin) {
        Car car = carService.getCarByVin(vin);
        if (car != null) {
            CarDTO dto = CarMapper.toDTO(car);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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