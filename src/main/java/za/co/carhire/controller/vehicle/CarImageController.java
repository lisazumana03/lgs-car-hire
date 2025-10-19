package za.co.carhire.controller.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.service.vehicle.ICarService;

import java.io.IOException;


@RestController
@RequestMapping("/api/car/image")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class CarImageController {

    @Autowired
    private ICarService carService;

 //Upload car image

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCarImage(
            @PathVariable int id,
            @RequestParam("image") MultipartFile file) {

        try {
            Car car = carService.read(id);
            if (car == null) {
                return ResponseEntity.notFound().build();
            }

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file provided");
            }

            // Store image
            car.setImageData(file.getBytes());
            car.setImageName(file.getOriginalFilename());
            car.setImageType(file.getContentType());

            carService.update(car);

            return ResponseEntity.ok("Image uploaded successfully");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed");
        }
    }

    //Get car image
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getCarImage(@PathVariable int id) {
        Car car = carService.read(id);

        if (car == null || car.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(car.getImageType() != null
                ? MediaType.parseMediaType(car.getImageType())
                : MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(car.getImageData(), headers, HttpStatus.OK);
    }
}

