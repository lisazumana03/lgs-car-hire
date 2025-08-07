package za.co.carhire.service.reservation.impl;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.repository.reservation.IInsuranceRepository;
import za.co.carhire.service.reservation.IInsuranceService;

import java.util.List;

@Service
public class InsuranceService implements IInsuranceService {
    @Autowired
    private IInsuranceRepository insuranceRepository;


    @Override
    public Insurance assignInsurance(Insurance insurance) {
        return null;
    }

    @Override
    public List<Insurance> getAllInsurances() {
        return List.of();
    }

    @Override
    public void deleteInsurance(Insurance insurance) {

    }
}
