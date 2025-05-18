package za.ac.cput.factory;

/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 17/05/2025
 * */

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Maintenance;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class MaintenanceFactoryTest {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void createMaintenance() throws ParseException {

        Date serviceDate = dateFormat.parse("2025-3-12");
        Maintenance maintenance = new Maintenance.Builder()
                .setMaintenanceID(24)
                .setServiceDate(serviceDate)
                .setDescription("Minor service")
                .setCost(350.00)
                .setStatus("In the workshop")
                .setMechanic("Alvin Lewis")
                .build();

        assertEquals(24, maintenance.getMaintenanceId());
        assertEquals(serviceDate, maintenance.getServiceDate());
        assertEquals("Minor service", maintenance.getDescription());
        assertEquals(350.00, maintenance.getCost());
        assertEquals("In the workshop", maintenance.getStatus());
        assertEquals("Alvin Lewis", maintenance.getMechanic());
        assertTrue(true);
        assertNotNull(maintenance);

    }
}
