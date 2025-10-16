package za.co.carhire.service.reservation.impl;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
Updated: 2025-10-16 - Completely rewritten to match new Insurance entity structure
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.dto.InsuranceDTO;
import za.co.carhire.mapper.InsuranceMapper;
import za.co.carhire.repository.reservation.IInsuranceRepository;
import za.co.carhire.service.reservation.IInsuranceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsuranceService implements IInsuranceService {

    private final IInsuranceRepository insuranceRepository;

    @Autowired
    public InsuranceService(IInsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    @Override
    public InsuranceDTO createInsurance(InsuranceDTO insuranceDTO) {
        if (insuranceDTO == null) {
            throw new IllegalArgumentException("Insurance DTO cannot be null");
        }

        // Validate required fields
        if (insuranceDTO.insuranceProvider() == null || insuranceDTO.insuranceProvider().isEmpty()) {
            throw new IllegalArgumentException("Insurance provider is required");
        }
        if (insuranceDTO.coverageType() == null) {
            throw new IllegalArgumentException("Coverage type is required");
        }
        if (insuranceDTO.insuranceCost() < 0) {
            throw new IllegalArgumentException("Insurance cost cannot be negative");
        }

        // Convert DTO to entity
        Insurance insurance = InsuranceMapper.fromDTO(insuranceDTO);

        // Save and return
        Insurance saved = insuranceRepository.save(insurance);
        return InsuranceMapper.toDTO(saved);
    }

    @Override
    public InsuranceDTO getInsuranceById(int id) {
        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Insurance not found with ID: " + id));
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
        if (insuranceDTO == null) {
            throw new IllegalArgumentException("Insurance DTO cannot be null");
        }

        // Check if exists
        Insurance existing = insuranceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Insurance not found with ID: " + id));

        // Update with new data using Builder
        Insurance updated = new Insurance.Builder()
                .setInsuranceID(id)  // Keep same ID
                .setInsuranceStartDate(insuranceDTO.insuranceStartDate())
                .setInsuranceEndDate(insuranceDTO.insuranceEndDate())
                .setInsuranceCost(insuranceDTO.insuranceCost())
                .setInsuranceProvider(insuranceDTO.insuranceProvider())
                .setCoverageType(insuranceDTO.coverageType())
                .setDeductible(insuranceDTO.deductible())
                .setPolicyNumber(insuranceDTO.policyNumber())
                .setActive(insuranceDTO.isActive())
                .build();

        Insurance saved = insuranceRepository.save(updated);
        return InsuranceMapper.toDTO(saved);
    }

    @Override
    public void deleteInsurance(int id) {
        if (!insuranceRepository.existsById(id)) {
            throw new IllegalArgumentException("Insurance not found with ID: " + id);
        }
        insuranceRepository.deleteById(id);
    }
}
