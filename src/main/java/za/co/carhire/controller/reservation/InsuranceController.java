package za.co.carhire.controller.reservation;
/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 12/08/2025
 * Updated: 2025-10-07 - Added DTO support
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
@CrossOrigin(origins = "*")
public class InsuranceController {

    @Autowired
    private IInsuranceService insuranceService;

    @PostMapping("/create")
    public ResponseEntity<InsuranceDTO> create(@RequestBody InsuranceDTO insuranceDTO) {
        Insurance insurance = InsuranceMapper.toEntity(insuranceDTO);
        Insurance createdInsurance = insuranceService.create(insurance);
        InsuranceDTO responseDTO = InsuranceMapper.toDTO(createdInsurance);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InsuranceDTO>> getAll() {
        List<Insurance> insurances = insuranceService.getAll();
        List<InsuranceDTO> dtos = insurances.stream()
                .map(InsuranceMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
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
    public ResponseEntity<InsuranceDTO> update(@RequestBody InsuranceDTO insuranceDTO) {
        Insurance existingInsurance = insuranceService.read(insuranceDTO.getInsuranceID());
        if (existingInsurance != null) {
            Insurance updatedInsurance = InsuranceMapper.updateEntityFromDTO(existingInsurance, insuranceDTO);
            Insurance savedInsurance = insuranceService.update(updatedInsurance);
            InsuranceDTO responseDTO = InsuranceMapper.toDTO(savedInsurance);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Insurance insurance = insuranceService.read(id);
        if (insurance != null) {
            insuranceService.deleteInsurance(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<InsuranceDTO> cancel(@PathVariable int id) {
        Insurance insurance = insuranceService.read(id);
        if (insurance != null) {
            insuranceService.cancelInsurance(id);
            Insurance cancelledInsurance = insuranceService.read(id);
            InsuranceDTO responseDTO = InsuranceMapper.toDTO(cancelledInsurance);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
