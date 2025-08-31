package za.co.carhire.repository.vehicle;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.domain.vehicle.Car;

import java.util.Optional;

@Repository
public interface ICarTypeRepository extends JpaRepository<CarType, Integer> {
    Optional<CarType> findByCar(Car car);
    Optional<CarType> findByType(String type);
    Optional<CarType> findByFuelType(String fuelType);
    Optional<CarType> findByNumberOfWheels(int numberOfWheels);
    Optional<CarType> findByNumberOfSeats(int numberOfSeats);
    Optional<CarType> findById(int carTypeID);
}