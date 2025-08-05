package za.co.carhire.service.reservation;
/*
Sibulele Gift Nohamba
220374686
Date: 24/05/2025
 */

import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.service.IService;

public interface IMaintenanceService extends IService<Maintenance, Integer> {
    void delete(int maintenanceId);
}
