//package za.co.carhire.service.impl.vehicle;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import za.co.carhire.domain.vehicle.Car;
//import za.co.carhire.repository.vehicle.ICarRepository;
//import za.co.carhire.service.vehicle.ICarService;
//
///*
//Imtiyaaz Waggie 219374759
//Date: 25/05/2025
// */
//
//import java.util.List;
//import java.util.Set;
//
//@Service
//public abstract class CarService implements ICarService {
//
//    @Autowired
//    private ICarRepository carRepository;
//
//    @Override
//    public Set<Car> getCars() {
//        return Set.copyOf(this.carRepository.findAll());
//    }
//
//    @Override
//    public List<Car> getCarsByBrand(String brand) {
//        return this.carRepository.findByBrand(brand).map(List::of).orElse(List.of());
//    }
//
//    @Override
//    public Car create(Car car) {
//        return this.carRepository.save(car);
//    }
//
//    @Override
//    public Car read(int carId) {
//        return this.carRepository.findById(carId).orElse(null);
//    }
//
//    @Override
//    public Car update(Car car) {
//        if (this.carRepository.existsById(car.getCarID())) {
//            return this.carRepository.save(car);
//        }
//        return null;
//    }
//
//    @Override
//    public void delete(Integer carId) {
//        this.carRepository.deleteById(carId);
//    }
//}