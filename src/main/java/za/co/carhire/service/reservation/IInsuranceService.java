package za.co.carhire.service.reservation;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import za.co.carhire.domain.reservation.Insurance;

import java.util.List;

public interface IInsuranceService {
    Insurance assignInsurance(Insurance insurance);
    List<Insurance> getAllInsurances();
    void deleteInsurance(Insurance insurance);
}
