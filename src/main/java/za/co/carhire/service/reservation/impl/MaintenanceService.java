package za.co.carhire.service.reservation.impl;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.repository.reservation.IMaintenanceRepository;
import za.co.carhire.service.reservation.IMaintenanceService;

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
    public void delete(Integer integer) {

    }
}
