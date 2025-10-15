package za.co.carhire.repository.vehicle;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarStatus;
import za.co.carhire.domain.vehicle.CarType;

import java.util.Optional;

@Repository
public interface ICarRepository extends JpaRepository<Car, Integer> {
    Optional<Car> findByBrand(String brand);
    Optional<Car> findByModel(String model);
    Optional<Car> findByBrandAndModel(String brand, String model);
    Optional<Car> findByYear(int year);
    Optional<Car> findByStatus(CarStatus status);
    Optional<Car> findByCarType(CarType carType);
    Optional<Car> findById(int carID);
    Optional<Car> findByLicensePlate(String licensePlate);
    Optional<Car> findByVin(String vin);
}