package za.co.carhire.controller.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.dto.vehicle.CarTypeDTO;
import za.co.carhire.mapper.vehicle.CarTypeMapper;
import za.co.carhire.service.vehicle.ICarTypeService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
Updated: 28/08/2025 - Added /api prefix to endpoints
 */

@RestController
@RequestMapping("/api/cartype") 
// @CrossOrigin(origins = "http://localhost:3046")
public class CarTypeController {
    @Autowired
    private ICarTypeService carTypeService;

    @PostMapping("/create")
    public ResponseEntity<CarTypeDTO> create(@RequestBody CarTypeDTO carTypeDto) {
        CarType carType = CarTypeMapper.toEntity(carTypeDto);
        CarType created = carTypeService.create(carType);
        CarTypeDTO responseDto = CarTypeMapper.toDTO(created);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CarTypeDTO> read(@PathVariable int id) {
        CarType carType = carTypeService.read(id);
        if (carType != null) {
            CarTypeDTO dto = CarTypeMapper.toDTO(carType);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<CarTypeDTO> update(@RequestBody CarTypeDTO carTypeDto) {
        CarType existingCarType = carTypeService.read(carTypeDto.getCarTypeID());
        if (existingCarType != null) {
            CarType updatedCarType = CarTypeMapper.updateEntityFromDTO(existingCarType, carTypeDto);
            CarType savedCarType = carTypeService.update(updatedCarType);
            CarTypeDTO responseDto = CarTypeMapper.toDTO(savedCarType);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        carTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarTypeDTO>> getAll() {
        Set<CarType> carTypes = carTypeService.getCarTypes();
        List<CarTypeDTO> carTypeDtos = carTypes.stream()
                .map(CarTypeMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(carTypeDtos, HttpStatus.OK);
    }

    @GetMapping("/fuel/{fuelType}")
    public ResponseEntity<List<CarTypeDTO>> getByFuelType(@PathVariable String fuelType) {
        List<CarType> carTypes = carTypeService.getCarTypesByFuelType(fuelType);
        List<CarTypeDTO> carTypeDtos = carTypes.stream()
                .map(CarTypeMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(carTypeDtos, HttpStatus.OK);
    }
}