package za.co.carhire.controller.reservation;

/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 12/08/2025
 *
 * Imtiyaaz Waggie 219374759
 *  - Added /api prefix and DTO support
 * */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.dto.reservation.InsuranceDTO;
import za.co.carhire.mapper.reservation.InsuranceMapper;
import za.co.carhire.service.reservation.IInsuranceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/insurance")
// @CrossOrigin(origins = "http://localhost:3046")
public class InsuranceController {

    @Autowired
    private IInsuranceService insuranceService;

    @PostMapping("/create")
    public ResponseEntity<InsuranceDTO> create(@RequestBody InsuranceDTO insuranceDto) {
        Insurance insurance = InsuranceMapper.toEntity(insuranceDto);
        Insurance createdInsurance = insuranceService.create(insurance);
        InsuranceDTO responseDto = InsuranceMapper.toDTO(createdInsurance);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<InsuranceDTO> read(@PathVariable int id) {
        Insurance insurance = insuranceService.read(id);
        if (insurance != null) {
            InsuranceDTO dto = InsuranceMapper.toDTO(insurance);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<InsuranceDTO> update(@RequestBody InsuranceDTO insuranceDto) {
        Insurance existingInsurance = insuranceService.read(insuranceDto.getInsuranceID());
        if (existingInsurance != null) {
            Insurance updatedInsurance = InsuranceMapper.updateEntityFromDTO(existingInsurance, insuranceDto);
            Insurance savedInsurance = insuranceService.update(updatedInsurance);
            InsuranceDTO responseDto = InsuranceMapper.toDTO(savedInsurance);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        insuranceService.deleteInsurance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InsuranceDTO>> getAll() {
        List<Insurance> insurances = insuranceService.getAll();
        List<InsuranceDTO> insuranceDtos = insurances.stream()
                .map(InsuranceMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(insuranceDtos, HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<InsuranceDTO> cancel(@PathVariable int id) {
        Insurance cancelledInsurance = insuranceService.cancelInsurance(id);
        if (cancelledInsurance != null) {
            InsuranceDTO dto = InsuranceMapper.toDTO(cancelledInsurance);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/assign/{insuranceId}/to-car/{carId}")
    public ResponseEntity<InsuranceDTO> assignToCar(@PathVariable int insuranceId, @PathVariable int carId) {
        Insurance assignedInsurance = insuranceService.assignInsuranceToCar(insuranceId, carId);
        if (assignedInsurance != null) {
            InsuranceDTO dto = InsuranceMapper.toDTO(assignedInsurance);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<InsuranceDTO>> getByStatus(@PathVariable String status) {
        List<Insurance> insurances = insuranceService.getInsurancesByStatus(status);
        List<InsuranceDTO> insuranceDtos = insurances.stream()
                .map(InsuranceMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(insuranceDtos, HttpStatus.OK);
    }

    @GetMapping("/by-provider/{provider}")
    public ResponseEntity<List<InsuranceDTO>> getByProvider(@PathVariable String provider) {
        List<Insurance> insurances = insuranceService.getInsurancesByProvider(provider);
        List<InsuranceDTO> insuranceDtos = insurances.stream()
                .map(InsuranceMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(insuranceDtos, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<InsuranceDTO>> getActiveInsurances() {
        List<Insurance> insurances = insuranceService.getActiveInsurances();
        List<InsuranceDTO> insuranceDtos = insurances.stream()
                .map(InsuranceMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(insuranceDtos, HttpStatus.OK);
    }
}