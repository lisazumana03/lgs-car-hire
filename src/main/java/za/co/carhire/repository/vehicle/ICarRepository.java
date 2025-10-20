package za.co.carhire.repository.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICarRepository extends JpaRepository<Car, Integer> {
    Optional<Car> findByBrand(String brand);
    Optional<Car> findByModel(String model);
    Optional<Car> findByBrandAndModel(String brand, String model);
    Optional<Car> findByYear(int year);
    Optional<Car> findByAvailability(boolean availability);
    Optional<Car> findByRentalPrice(double rentalPrice);
    Optional<Car> findByCarType(CarType carType);

    Optional<Car> findById(int carID);

    // Return lists instead of single Optional for multiple results
    List<Car> findAllByBrand(String brand);
    List<Car> findAllByModel(String model);
    List<Car> findAllByYear(int year);
    List<Car> findAllByAvailability(boolean availability);
    List<Car> findAllByRentalPrice(double rentalPrice);
    List<Car> findAllByCarType(CarType carType);

    // Find by price range
    List<Car> findByRentalPriceBetween(double minPrice, double maxPrice);

    // Find by year range
    List<Car> findByYearBetween(int startYear, int endYear);
}