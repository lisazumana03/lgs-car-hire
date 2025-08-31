package za.co.carhire.service.vehicle.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.repository.vehicle.ICarTypeRepository;
import za.co.carhire.service.vehicle.ICarTypeService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarTypeService implements ICarTypeService {

    @Autowired
    private ICarTypeRepository carTypeRepository;

    @Override
    public CarType create(CarType carType) {
        return carTypeRepository.save(carType);
    }

    @Override
    public CarType read(Integer id) {
        return carTypeRepository.findById(id).orElse(null);
    }

    @Override
    public CarType read(int carTypeID) {
        return carTypeRepository.findById(carTypeID).orElse(null);
    }

    @Override
    public CarType update(CarType carType) {
        if (carTypeRepository.existsById(carType.getCarTypeID())) {
            return carTypeRepository.save(carType);
        }
        return null;
    }

    @Override
    public void delete(int carTypeID) {
        carTypeRepository.deleteById(carTypeID);
    }

    @Override
    public Set<CarType> getCarTypes() {
        return carTypeRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public List<CarType> getCarTypesByFuelType(String fuelType) {
        return carTypeRepository.findAll().stream()
                .filter(carType -> carType.getFuelType() != null && 
                       carType.getFuelType().equalsIgnoreCase(fuelType))
                .collect(Collectors.toList());
    }
}