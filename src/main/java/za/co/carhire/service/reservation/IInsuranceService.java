package za.co.carhire.service.reservation;

/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025

Imtiyaaz Waggie 219374759
- Added more comprehensive service methods
 */

import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.service.IService;

import java.util.List;

public interface IInsuranceService extends IService<Insurance, Integer> {

    Insurance create(Insurance insurance);
    Insurance read(int insuranceID);
    Insurance update(Insurance insurance);
    void deleteInsurance(int insuranceId);

    List<Insurance> getAll();

    Insurance cancelInsurance(int insuranceId);
    Insurance assignInsuranceToCar(int insuranceId, int carId);

    List<Insurance> getInsurancesByStatus(String status);
    List<Insurance> getInsurancesByProvider(String provider);
    List<Insurance> getActiveInsurances();
    List<Insurance> getExpiredInsurances();
    List<Insurance> getInsurancesByCostRange(double minCost, double maxCost);

    boolean isInsuranceValid(int insuranceId);
    boolean canAssignToCar(int insuranceId, int carId);
}