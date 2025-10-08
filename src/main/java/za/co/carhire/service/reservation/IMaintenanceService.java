package za.co.carhire.service.reservation;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.dto.MaintenanceDTO;
import za.co.carhire.service.IService;

import java.util.List;
import java.util.Optional;

public interface IMaintenanceService {
    MaintenanceDTO create(MaintenanceDTO dto);
    Optional<MaintenanceDTO> read(int id);
    List<MaintenanceDTO> getAll();
    MaintenanceDTO update(int id, MaintenanceDTO dto);
    void delete(int id);
}
