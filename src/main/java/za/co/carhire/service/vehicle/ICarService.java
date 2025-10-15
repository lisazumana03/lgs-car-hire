package za.co.carhire.service.vehicle;

import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarStatus;
import za.co.carhire.service.IService;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
Updated: 15/10/2025 - Refactored for new Car model
*/

import java.util.List;
import java.util.Set;

public interface ICarService extends IService<Car, Integer> {

    Set<Car> getCars();

    List<Car> getCarsByBrand(String brand);

    Car create(Car car);
    Car read(int carID);
    Car update(Car car);
    void delete(int carID);

    List<Car> getAvailableCars();
    List<Car> getCarsByStatus(CarStatus status);
    Car updateStatus(int carID, CarStatus status);
    Car updateMileage(int carID, int mileage);
    List<Car> getCarsByYear(int year);
    Car getCarByLicensePlate(String licensePlate);
    Car getCarByVin(String vin);
}