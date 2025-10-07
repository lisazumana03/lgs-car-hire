package za.co.carhire.service.reservation.impl;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.repository.reservation.IInsuranceRepository;
import za.co.carhire.service.reservation.IInsuranceService;

import java.util.List;

@Service
@Transactional
public class InsuranceService implements IInsuranceService {
    @Autowired
    private IInsuranceRepository insuranceRepository;

    @Override
    public Insurance assignInsurance(Insurance insurance) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insurance> getAll() {
        return insuranceRepository.findAll();
    }

    @Override
    public void deleteInsurance(int insuranceId) {
        insuranceRepository.deleteById(insuranceId);
    }

    @Override
    public void cancelInsurance(int insuranceId) {
        Insurance insurance = read(insuranceId);
        if (insurance != null) {
            insurance.setStatus("CANCELLED");
            insuranceRepository.save(insurance);
        }
    }

    @Override
    public Insurance create(Insurance insurance) {
        return insuranceRepository.save(insurance);
    }

    @Override
    @Transactional(readOnly = true)
    public Insurance read(Integer insuranceId) {
        return insuranceRepository.findById(insuranceId).orElse(null);
    }

    @Override
    public Insurance update(Insurance insurance) {
        if(insurance != null){
            return insuranceRepository.save(insurance);
        }
        return null;
    }
}
