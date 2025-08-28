package za.co.carhire.service.vehicle.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.vehicle.ICarService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarService implements ICarService {

    @Autowired
    private ICarRepository carRepository;

    @Override
    public Car create(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car read(Integer id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public Car read(int carID) {
        return carRepository.findById(carID).orElse(null);
    }

    @Override
    public Car update(Car car) {
        if (carRepository.existsById(car.getCarID())) {
            return carRepository.save(car);
        }
        return null;
    }

    @Override
    public void delete(int carID) {
        carRepository.deleteById(carID);
    }

    @Override
    public Set<Car> getCars() {
        return carRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public List<Car> getCarsByBrand(String brand) {
        return carRepository.findAll().stream()
                .filter(car -> car.getBrand() != null && car.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> getAvailableCars() {
        return carRepository.findAll().stream()
                .filter(Car::isAvailability)
                .collect(Collectors.toList());
    }

    @Override
    public Car updateAvailability(int carID, boolean available) {
        Optional<Car> carOptional = carRepository.findById(carID);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            car.setAvailability(available);
            return carRepository.save(car);
        }
        return null;
    }

    @Override
    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) {
        return carRepository.findAll().stream()
                .filter(car -> car.getRentalPrice() >= minPrice && car.getRentalPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> getCarsByYear(int year) {
        return carRepository.findAll().stream()
                .filter(car -> car.getYear() == year)
                .collect(Collectors.toList());
    }
}