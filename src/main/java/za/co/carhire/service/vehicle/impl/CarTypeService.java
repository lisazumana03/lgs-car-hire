package za.co.carhire.service.vehicle.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.repository.vehicle.ICarTypeRepository;
import za.co.carhire.service.vehicle.ICarTypeService;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

import java.util.List;
import java.util.Set;

@Service
public class CarTypeService implements ICarTypeService {

    @Autowired
    private ICarTypeRepository carTypeRepository;

    @Override
    public Set<CarType> getCarTypes() {
        return Set.copyOf(this.carTypeRepository.findAll());
    }

    @Override
    public List<CarType> getCarTypesByFuelType(String fuelType) {
        return this.carTypeRepository.findByFuelType(fuelType).map(List::of).orElse(List.of());
    }

    @Override
    public CarType create(CarType carType) {
        return this.carTypeRepository.save(carType);
    }

    @Override
    public CarType read(Integer integer) {
        return null;
    }

    @Override
    public CarType read(int carTypeId) {
        return this.carTypeRepository.findById(carTypeId).orElse(null);
    }

    @Override
    public CarType update(CarType carType) {
        if (this.carTypeRepository.existsById(carType.getCarTypeID())) {
            return this.carTypeRepository.save(carType);
        }
        return null;
    }

    @Override
    public void delete(Integer carTypeID) {

            this.carTypeRepository.deleteById(carTypeID);
    }

}