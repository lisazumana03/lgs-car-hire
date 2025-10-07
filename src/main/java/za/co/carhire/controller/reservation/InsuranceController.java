package za.co.carhire.controller.reservation;
/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 12/08/2025
 * */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.dto.InsuranceDTO;
import za.co.carhire.service.reservation.impl.InsuranceService;

import java.util.List;

@RestController
@RequestMapping("/api/insurance")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class InsuranceController {


    private final InsuranceService insuranceService;

    @Autowired
    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    // Create Insurance
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InsuranceDTO createInsurance(@RequestBody InsuranceDTO insuranceDTO) {
        return insuranceService.createInsurance(insuranceDTO);
    }

    // Get Insurance by ID
    @GetMapping("/{id}")
    public InsuranceDTO getInsuranceById(@PathVariable int id) {
        return insuranceService.getInsuranceById(id);
    }

    // Get all Insurances
    @GetMapping
    public List<InsuranceDTO> getAllInsurances() {
        return insuranceService.getAllInsurances();
    }

    // Update Insurance
    @PutMapping("/{id}")
    public InsuranceDTO updateInsurance(@PathVariable int id, @RequestBody InsuranceDTO insuranceDTO) {
        return insuranceService.updateInsurance(id, insuranceDTO);
    }

    // Delete Insurance
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInsurance(@PathVariable int id) {
        insuranceService.deleteInsurance(id);
    }

    
    }
