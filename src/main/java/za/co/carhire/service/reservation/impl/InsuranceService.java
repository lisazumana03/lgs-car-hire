package za.co.carhire.service.reservation.impl;

/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
Updated: 30/08/2025 - Complete implementation with business logic
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.repository.reservation.IInsuranceRepository;
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.reservation.IInsuranceService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class InsuranceService implements IInsuranceService {

    @Autowired
    private IInsuranceRepository insuranceRepository;

    @Autowired
    private ICarRepository carRepository;

    @Override
    public Insurance create(Insurance insurance) {
        // Validate insurance before creation
        if (insurance == null) {
            throw new IllegalArgumentException("Insurance cannot be null");
        }
        if (insurance.getInsuranceCost() < 0) {
            throw new IllegalArgumentException("Insurance cost cannot be negative");
        }
        if (insurance.getInsuranceProvider() == null || insurance.getInsuranceProvider().isEmpty()) {
            throw new IllegalArgumentException("Insurance provider is required");
        }
        if (insurance.getStatus() == null) {
            insurance = new Insurance.Builder()
                    .copy(insurance)
                    .setStatus("ACTIVE")
                    .build();
        }
        return insuranceRepository.save(insurance);
    }

    @Override
    @Transactional(readOnly = true)
    public Insurance read(Integer insuranceId) {
        if (insuranceId == null) {
            return null;
        }
        Optional<Insurance> insurance = insuranceRepository.findById(insuranceId);
        if (insurance.isPresent()) {
            Insurance foundInsurance = insurance.get();
            // Initialize lazy-loaded car if present
            if (foundInsurance.getCar() != null) {
                foundInsurance.getCar().getModel(); // Force load
            }
            return foundInsurance;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Insurance read(int insuranceId) {
        return read(Integer.valueOf(insuranceId));
    }

    @Override
    public Insurance update(Insurance insurance) {
        if (insurance == null) {
            throw new IllegalArgumentException("Insurance cannot be null");
        }
        if (!insuranceRepository.existsById(insurance.getInsuranceID())) {
            return null;
        }
        return insuranceRepository.save(insurance);
    }

    @Override
    public void deleteInsurance(int insuranceId) {
        // Check if insurance exists before deletion
        if (insuranceRepository.existsById(insuranceId)) {
            // Remove association with car if exists
            Optional<Insurance> insurance = insuranceRepository.findById(insuranceId);
            if (insurance.isPresent() && insurance.get().getCar() != null) {
                Car car = insurance.get().getCar();
                car.setInsurance(null);
                carRepository.save(car);
            }
            insuranceRepository.deleteById(insuranceId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insurance> getAll() {
        List<Insurance> insurances = insuranceRepository.findAll();
        // Initialize lazy-loaded relationships
        insurances.forEach(insurance -> {
            if (insurance.getCar() != null) {
                insurance.getCar().getModel(); // Force load
            }
        });
        return insurances;
    }

    @Override
    public Insurance cancelInsurance(int insuranceId) {
        Optional<Insurance> insuranceOpt = insuranceRepository.findById(insuranceId);
        if (insuranceOpt.isPresent()) {
            Insurance insurance = insuranceOpt.get();
            Insurance cancelledInsurance = new Insurance.Builder()
                    .copy(insurance)
                    .setStatus("CANCELLED")
                    .build();
            return insuranceRepository.save(cancelledInsurance);
        }
        return null;
    }

    @Override
    public Insurance assignInsuranceToCar(int insuranceId, int carId) {
        Optional<Insurance> insuranceOpt = insuranceRepository.findById(insuranceId);
        Optional<Car> carOpt = carRepository.findById(carId);

        if (insuranceOpt.isPresent() && carOpt.isPresent()) {
            Insurance insurance = insuranceOpt.get();
            Car car = carOpt.get();

            // Check if car already has insurance
            if (car.getInsurance() != null && car.getInsurance().getInsuranceID() != insuranceId) {
                throw new IllegalStateException("Car already has insurance assigned");
            }

            // Remove insurance from previous car if exists
            if (insurance.getCar() != null && insurance.getCar().getCarID() != carId) {
                Car previousCar = insurance.getCar();
                previousCar.setInsurance(null);
                carRepository.save(previousCar);
            }

            // Assign insurance to new car
            Insurance updatedInsurance = new Insurance.Builder()
                    .copy(insurance)
                    .setCar(car)
                    .build();

            car.setInsurance(updatedInsurance);
            carRepository.save(car);

            return insuranceRepository.save(updatedInsurance);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insurance> getInsurancesByStatus(String status) {
        return insuranceRepository.findAll().stream()
                .filter(insurance -> insurance.getStatus() != null &&
                        insurance.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insurance> getInsurancesByProvider(String provider) {
        return insuranceRepository.findAll().stream()
                .filter(insurance -> insurance.getInsuranceProvider() != null &&
                        insurance.getInsuranceProvider().equalsIgnoreCase(provider))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insurance> getActiveInsurances() {
        return getInsurancesByStatus("ACTIVE");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insurance> getExpiredInsurances() {
        return getInsurancesByStatus("EXPIRED");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insurance> getInsurancesByCostRange(double minCost, double maxCost) {
        return insuranceRepository.findAll().stream()
                .filter(insurance -> insurance.getInsuranceCost() >= minCost &&
                        insurance.getInsuranceCost() <= maxCost)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInsuranceValid(int insuranceId) {
        Optional<Insurance> insuranceOpt = insuranceRepository.findById(insuranceId);
        if (insuranceOpt.isPresent()) {
            Insurance insurance = insuranceOpt.get();
            return insurance.getStatus() != null &&
                    insurance.getStatus().equalsIgnoreCase("ACTIVE") &&
                    insurance.getInsuranceStartDate() != null &&
                    insurance.getInsuranceStartDate().before(new Date());
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canAssignToCar(int insuranceId, int carId) {
        Optional<Insurance> insuranceOpt = insuranceRepository.findById(insuranceId);
        Optional<Car> carOpt = carRepository.findById(carId);

        if (insuranceOpt.isPresent() && carOpt.isPresent()) {
            Insurance insurance = insuranceOpt.get();
            Car car = carOpt.get();

            // Check if insurance is active
            if (!isInsuranceValid(insuranceId)) {
                return false;
            }

            // Check if car doesn't already have different insurance
            if (car.getInsurance() != null && car.getInsurance().getInsuranceID() != insuranceId) {
                return false;
            }

            return true;
        }
        return false;
    }
}