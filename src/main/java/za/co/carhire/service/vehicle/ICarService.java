package za.co.carhire.service.vehicle;

import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.service.IService;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

import java.util.List;
import java.util.Set;

public interface ICarService extends IService <Car, Integer> {

    Set<Car> getCars();

    List<Car> getCarsByBrand(String brand);

    Car create(Car car);
    Car read(int carID);
    Car update(Car car);
    void delete(int carID);
}