package za.co.carhire.controller.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.vehicle.CarDTO;
import za.co.carhire.mapper.vehicle.CarMapper;
import za.co.carhire.service.vehicle.ICarService;

import java.io.IOException;
import java.util.Base64;
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

    /**
     * Upload or update car image using multipart/form-data
     * @param id the car ID
     * @param file the image file
     * @return the updated car with image
     */
    @PostMapping(value = "/{id}/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDTO> uploadCarImage(
            @PathVariable int id,
            @RequestParam("image") MultipartFile file) {

        try {
            Car car = carService.read(id);
            if (car == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Store image data
            car.setImageData(file.getBytes());
            car.setImageName(file.getOriginalFilename());
            car.setImageType(contentType);

            Car updatedCar = carService.update(car);
            CarDTO responseDto = CarMapper.toDTO(updatedCar);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get car image as binary data
     * @param id the car ID
     * @return the image file
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getCarImage(@PathVariable int id) {
        Car car = carService.read(id);
        if (car == null || car.getImageData() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        if (car.getImageType() != null) {
            headers.setContentType(MediaType.parseMediaType(car.getImageType()));
        } else {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }

        if (car.getImageName() != null) {
            headers.setContentDispositionFormData("inline", car.getImageName());
        }

        return new ResponseEntity<>(car.getImageData(), headers, HttpStatus.OK);
    }

    /**
     * Create car with image in a single request
     * @param carData the JSON car data
     * @param image the image file (optional)
     * @return the created car
     */
    @PostMapping(value = "/create-with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDTO> createWithImage(
            @RequestParam("carData") String carData,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            // Parse car data from JSON string
            // You may want to use ObjectMapper here for proper JSON parsing
            // For now, client should send CarDTO as JSON in carData param

            // Create basic car first
            CarDTO carDto = new CarDTO(); // Parse from carData string using Jackson ObjectMapper
            Car car = CarMapper.toEntity(carDto);

            // Add image if provided
            if (image != null && !image.isEmpty()) {
                String contentType = image.getContentType();
                if (contentType != null && contentType.startsWith("image/")) {
                    car.setImageData(image.getBytes());
                    car.setImageName(image.getOriginalFilename());
                    car.setImageType(contentType);
                }
            }

            Car createdCar = carService.create(car);
            CarDTO responseDto = CarMapper.toDTO(createdCar);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}