package za.co.carhire.service.reservation;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.dto.InsuranceDTO;
import za.co.carhire.service.IService;

import java.util.List;

public interface IInsuranceService{
    InsuranceDTO createInsurance(InsuranceDTO insuranceDTO);

    InsuranceDTO getInsuranceById(int id);

    List<InsuranceDTO> getAllInsurances();

    InsuranceDTO updateInsurance(int id, InsuranceDTO insuranceDTO);

    void deleteInsurance(int id);
}
