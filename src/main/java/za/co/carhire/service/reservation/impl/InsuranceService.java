package za.co.carhire.service.reservation.impl;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.InsuranceDTO;
import za.co.carhire.mapper.InsuranceMapper;
import za.co.carhire.repository.reservation.IInsuranceRepository;
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.reservation.IInsuranceService;
import za.co.carhire.service.vehicle.ICarService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InsuranceService implements IInsuranceService {


    private IInsuranceRepository insuranceRepository;

    private ICarService carService;

    @Autowired
    public InsuranceService(IInsuranceRepository insuranceRepository, ICarService carService) {
        this.carService = carService;
        this.insuranceRepository = insuranceRepository;

    }

    @Override
    public InsuranceDTO createInsurance(InsuranceDTO insuranceDTO) {
        // Convert DTO to domain entity
        Insurance createdInsurance = InsuranceMapper.fromDTO(insuranceDTO);

        // Set car reference if provided
        if (insuranceDTO.car() != null && insuranceDTO.car() > 0) {
            Car car = carService.read(insuranceDTO.car()); // this throws if not found
            createdInsurance = new Insurance.Builder()
                    .setInsuranceID(insuranceDTO.insuranceID())
                    .setInsuranceStartDate(insuranceDTO.insuranceStartDate())
                    .setInsuranceCost(insuranceDTO.insuranceCost())
                    .setInsuranceProvider(insuranceDTO.insuranceProvider())
                    .setStatus(insuranceDTO.status())
                    .setPolicyNumber(insuranceDTO.policyNumber())
                    .setMechanic(insuranceDTO.mechanic())
                    .setCar(car)
                    .build();
        }

        Insurance saved = insuranceRepository.save(createdInsurance);
        return InsuranceMapper.toDTO(saved);
    }

    @Override
    public InsuranceDTO getInsuranceById(int id) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Insurance not found: " + id));
        return InsuranceMapper.toDTO(insurance);
    }

    @Override
    public List<InsuranceDTO> getAllInsurances() {
        return insuranceRepository.findAll().stream()
                .map(InsuranceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InsuranceDTO updateInsurance(int id, InsuranceDTO insuranceDTO) {
        Insurance existing = insuranceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Insurance not found: " + id));

        // Update with new data
        Insurance updated = new Insurance.Builder()
                .setInsuranceID(id)
                .setInsuranceStartDate(insuranceDTO.insuranceStartDate())
                .setInsuranceCost(insuranceDTO.insuranceCost())
                .setInsuranceProvider(insuranceDTO.insuranceProvider())
                .setStatus(insuranceDTO.status())
                .setPolicyNumber(insuranceDTO.policyNumber())
                .setMechanic(insuranceDTO.mechanic())
                .build();

        // Set car reference if provided
        if (insuranceDTO.car() != null && insuranceDTO.car() > 0) {
            Optional<Car> car = Optional.ofNullable(carService.read(insuranceDTO.car()));
            if (car.isPresent()) {
                updated = new Insurance.Builder()
                        .setInsuranceID(id)
                        .setInsuranceStartDate(insuranceDTO.insuranceStartDate())
                        .setInsuranceCost(insuranceDTO.insuranceCost())
                        .setInsuranceProvider(insuranceDTO.insuranceProvider())
                        .setStatus(insuranceDTO.status())
                        .setPolicyNumber(insuranceDTO.policyNumber())
                        .setMechanic(insuranceDTO.mechanic())
                        .setCar(car.get())
                        .build();
            }
        }

        Insurance saved = insuranceRepository.save(updated);
        return InsuranceMapper.toDTO(saved);
    }

    @Override
    public void deleteInsurance(int id) {
        if (!insuranceRepository.existsById(id)) {
            throw new IllegalArgumentException("Insurance not found: " + id);
        }
        insuranceRepository.deleteById(id);
    }

}
