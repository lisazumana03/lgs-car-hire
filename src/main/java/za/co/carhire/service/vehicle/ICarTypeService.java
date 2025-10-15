package za.co.carhire.service.vehicle;

import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.domain.vehicle.FuelType;
import za.co.carhire.service.IService;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

import java.util.List;
import java.util.Set;

public interface ICarTypeService extends IService <CarType, Integer> {

    Set<CarType> getCarTypes();

    List<CarType> getCarTypesByFuelType(FuelType fuelType);

    CarType create(CarType carType);
    CarType read(int carTypeID);
    CarType update(CarType carType);
    void delete(int carTypeID);
}