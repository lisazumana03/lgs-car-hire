package za.co.carhire.service.reservation.impl;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.BookingStatus;
import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.MaintenanceDTO;
import za.co.carhire.mapper.MaintenanceMapper;
import za.co.carhire.repository.reservation.IMaintenanceRepository;
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.reservation.IMaintenanceService;


import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    private final   ICarRepository carRepository;

    @Autowired
    public MaintenanceService(IMaintenanceRepository maintenanceRepository, ICarRepository carRepository) {
        this.carRepository = carRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    @Override
    public MaintenanceDTO create(MaintenanceDTO dto) {
        Car car = carRepository.findById(dto.carId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found with ID: " + dto.carId()));

        //Info from frontend to entity or domain class
        Maintenance maintenance = MaintenanceMapper.toEntity(dto, car);
        //Save to DB
        Maintenance saved = maintenanceRepository.save(maintenance);
        return MaintenanceMapper.toDTO(saved);
    }

    @Override
    public Optional<MaintenanceDTO> read(int id) {
        return maintenanceRepository.findById(id)
                .map(MaintenanceMapper::toDTO);
    }

    @Override
    public List<MaintenanceDTO> getAll() {
        return maintenanceRepository.findAll()
                .stream()
                .map(MaintenanceMapper::toDTO)
                .toList();
    }

    @Override
    public MaintenanceDTO update(int id, MaintenanceDTO dto) {
        Optional<Maintenance> existing = maintenanceRepository.findById(id);
        if (existing.isEmpty()) throw new RuntimeException("Maintenance not found");

        Car car = carRepository.findById(dto.carId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        Maintenance updated = new Maintenance.Builder()
                .copy(existing.get())
                .setMaintenanceDate(dto.maintenanceDate())
                .setServiceType(dto.serviceType())
                .setDescription(dto.description())
                .setCost(dto.cost())
                .setMileageAtService(dto.mileageAtService())
                .setNextServiceDate(dto.nextServiceDate())
                .setNextServiceMileage(dto.nextServiceMileage())
                .setMechanicName(dto.mechanicName())
                .setStatus(dto.status())
                .setNotes(dto.notes())
                .setCar(car)
                .build();

        Maintenance saved = maintenanceRepository.save(updated);
        return MaintenanceMapper.toDTO(saved);
    }

    @Override
    public void delete(int id) {
        maintenanceRepository.deleteById(id);

    }
}
