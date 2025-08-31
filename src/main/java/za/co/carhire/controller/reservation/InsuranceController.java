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
import za.co.carhire.service.reservation.impl.InsuranceService;

@RestController
@RequestMapping("/insurance")
@CrossOrigin(origins = "*")
public class InsuranceController {
    @Autowired
    private InsuranceService insuranceService;

    @PostMapping("/create")
    public ResponseEntity<Insurance> create(@RequestBody Insurance insurance) {
        return new ResponseEntity<>(insuranceService.create(insurance), HttpStatus.CREATED);
    }
      @GetMapping("/all")
    public List<Insurance> getAll(){
        return insuranceService.getAll();
    }
    @Override
    public List<Insurance> getAll(){
        return insuranceRepository.findAll()
    }
    @GetMapping("/read/{id}")
    public Insurance read(@PathVariable int id){
        return insuranceService.read(id);
    }
    @PutMapping("/update")
    public Insurance update(@RequestBody Insurance insurance){
        return insuranceService.update(insurance);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        insuranceService.deleteInsurance(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Insurance> cancel(@PathVariable int id) {
        insuranceService.cancelInsurance(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    }
