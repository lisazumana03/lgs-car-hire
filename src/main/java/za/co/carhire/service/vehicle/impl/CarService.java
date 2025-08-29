package za.co.carhire.service.vehicle.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.vehicle.ICarService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarService implements ICarService {

    @Autowired
    private ICarRepository carRepository;

    @Override
    public Car create(Car car) {
        return carRepository.save(car);
    }

    @Override
    @Transactional(readOnly = true)
    public Car read(Integer id) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            Car foundCar = car.get();
            if (foundCar.getCarType() != null) {
                foundCar.getCarType().getType(); 
            }
            return foundCar;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Car read(int carID) {
        Optional<Car> car = carRepository.findById(carID);
        if (car.isPresent()) {
            Car foundCar = car.get();
            if (foundCar.getCarType() != null) {
                foundCar.getCarType().getType(); 
            }
            return foundCar;
        }
        return null;
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
    @Transactional(readOnly = true)
    public Set<Car> getCars() {
        List<Car> cars = carRepository.findAll();
        cars.forEach(car -> {
            if (car.getCarType() != null) {
                car.getCarType().getType(); 
            }
        });
        return cars.stream().collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Car> getCarsByBrand(String brand) {
        List<Car> cars = carRepository.findAll().stream()
                .filter(car -> car.getBrand() != null && car.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
        
        cars.forEach(car -> {
            if (car.getCarType() != null) {
                car.getCarType().getType();
            }
        });
        
        return cars;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Car> getAvailableCars() {
        List<Car> cars = carRepository.findAll().stream()
                .filter(Car::isAvailability)
                .collect(Collectors.toList());
        
        cars.forEach(car -> {
            if (car.getCarType() != null) {
                car.getCarType().getType();
            }
        });
        
        return cars;
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
    @Transactional(readOnly = true)
    public List<Car> getCarsByPriceRange(double minPrice, double maxPrice) {
        List<Car> cars = carRepository.findAll().stream()
                .filter(car -> car.getRentalPrice() >= minPrice && car.getRentalPrice() <= maxPrice)
                .collect(Collectors.toList());
        cars.forEach(car -> {
            if (car.getCarType() != null) {
                car.getCarType().getType();
            }
        });
        
        return cars;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Car> getCarsByYear(int year) {
        List<Car> cars = carRepository.findAll().stream()
                .filter(car -> car.getYear() == year)
                .collect(Collectors.toList());
        cars.forEach(car -> {
            if (car.getCarType() != null) {
                car.getCarType().getType();
            }
        });
        
        return cars;
    }
}