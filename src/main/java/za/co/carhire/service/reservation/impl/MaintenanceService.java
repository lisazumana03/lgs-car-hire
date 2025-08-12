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
import za.co.carhire.repository.reservation.IMaintenanceRepository;
import za.co.carhire.service.reservation.IMaintenanceService;

import java.util.Optional;

@Service
public class MaintenanceService implements IMaintenanceService {
    @Autowired
    private IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance read(Integer integer) {
        return null;
    }

    @Override
    public Maintenance update(Maintenance maintenance) {
        return null;
    }

    @Override
    public void delete(int maintenanceId) {
        maintenanceRepository.deleteById(maintenanceId);
    }

    @Override
    public void cancelMaintenance(int maintenanceId) {
        Optional<Maintenance> optionalMaintenance = maintenanceRepository.findById(maintenanceId);

        if (optionalMaintenance.isPresent()) {
            Maintenance maintenance = optionalMaintenance.get();
            maintenance.setStatus("CANCELLED");
            maintenanceRepository.save(maintenance);
        } else {
            throw new RuntimeException("Maintenance record not found for ID: " + maintenanceId);
        }
    }
}
