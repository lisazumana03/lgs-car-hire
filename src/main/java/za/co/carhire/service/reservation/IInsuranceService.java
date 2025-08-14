package za.co.carhire.service.reservation;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.service.IService;

import java.util.List;

public interface IInsuranceService extends IService<Insurance, Integer> {
    Insurance assignInsurance(Insurance insurance);
    List<Insurance> getAll();
    void deleteInsurance(int insuranceId);
    void cancelInsurance(int insuranceId);
}
