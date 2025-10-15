package za.co.carhire.repository.vehicle;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.domain.vehicle.VehicleCategory;
import za.co.carhire.domain.vehicle.FuelType;

import java.util.Optional;
import java.util.List;

@Repository
public interface ICarTypeRepository extends JpaRepository<CarType, Integer> {
    Optional<CarType> findByCategory(VehicleCategory category);
    List<CarType> findByFuelType(FuelType fuelType);
    Optional<CarType> findByNumberOfSeats(int numberOfSeats);
    Optional<CarType> findById(int carTypeID);
}