package za.co.carhire.controller.vehicle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.carhire.domain.vehicle.PricingRule;
import za.co.carhire.dto.vehicle.PricingRuleDTO;
import za.co.carhire.mapper.vehicle.PricingRuleMapper;
import za.co.carhire.service.vehicle.IPricingRuleService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pricing-rule")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class PricingRuleController {

    @Autowired
    private IPricingRuleService pricingRuleService;

    @PostMapping("/create")
    public ResponseEntity<PricingRuleDTO> create(@RequestBody PricingRuleDTO pricingRuleDto) {
        PricingRule pricingRule = PricingRuleMapper.toEntity(pricingRuleDto);
        PricingRule createdRule = pricingRuleService.create(pricingRule);
        PricingRuleDTO responseDto = PricingRuleMapper.toDTO(createdRule);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<PricingRuleDTO> read(@PathVariable int id) {
        PricingRule pricingRule = pricingRuleService.read(id);
        if (pricingRule != null) {
            PricingRuleDTO dto = PricingRuleMapper.toDTO(pricingRule);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<PricingRuleDTO> update(@RequestBody PricingRuleDTO pricingRuleDto) {
        PricingRule existingRule = pricingRuleService.read(pricingRuleDto.getPricingRuleID());
        if (existingRule != null) {
            PricingRule updatedRule = PricingRuleMapper.updateEntityFromDTO(existingRule, pricingRuleDto);
            PricingRule savedRule = pricingRuleService.update(updatedRule);
            PricingRuleDTO responseDto = PricingRuleMapper.toDTO(savedRule);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        pricingRuleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PricingRuleDTO>> getAll() {
        List<PricingRule> pricingRules = pricingRuleService.getAllPricingRules();
        List<PricingRuleDTO> dtos = pricingRules.stream()
                .map(PricingRuleMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<PricingRuleDTO>> getActive() {
        List<PricingRule> pricingRules = pricingRuleService.getActivePricingRules();
        List<PricingRuleDTO> dtos = pricingRules.stream()
                .map(PricingRuleMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/car-type/{carTypeId}")
    public ResponseEntity<List<PricingRuleDTO>> getByCarType(@PathVariable int carTypeId) {
        List<PricingRule> pricingRules = pricingRuleService.getPricingRulesByCarType(carTypeId);
        List<PricingRuleDTO> dtos = pricingRules.stream()
                .map(PricingRuleMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/car-type/{carTypeId}/active")
    public ResponseEntity<PricingRuleDTO> getActiveForCarType(@PathVariable int carTypeId) {
        PricingRule pricingRule = pricingRuleService.getActivePricingRuleForCarType(carTypeId);
        if (pricingRule != null) {
            PricingRuleDTO dto = PricingRuleMapper.toDTO(pricingRule);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/calculate-price")
    public ResponseEntity<Double> calculatePrice(
            @RequestParam int carTypeId,
            @RequestParam int days,
            @RequestParam(required = false) String rentalDate) {

        LocalDate date = (rentalDate != null && !rentalDate.isEmpty())
                ? LocalDate.parse(rentalDate)
                : LocalDate.now();

        double price = pricingRuleService.calculatePrice(carTypeId, days, date);

        if (price > 0) {
            return new ResponseEntity<>(price, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/valid-on/{date}")
    public ResponseEntity<List<PricingRuleDTO>> getValidOnDate(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<PricingRule> pricingRules = pricingRuleService.getPricingRulesValidOnDate(localDate);
            List<PricingRuleDTO> dtos = pricingRules.stream()
                    .map(PricingRuleMapper::toDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
