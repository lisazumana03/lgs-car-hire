package za.ac.cput.factory;

/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 18/05/2025
 * */

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.Insurance;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class InsuranceFactoryTest {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void createInsurance() throws ParseException {

        Date insuranceStartDate = dateFormat.parse("2025-05-18");
        Insurance insurance = new Insurance.Builder()
                .setInsuranceID(24)
                .setInsuranceStartDate(insuranceStartDate)
                .setInsuranceCost(350.00)
                .setInsuranceProvider("CTU")
                .setPolicyNumber(37484737263748)
                .setStatus("New")
                .setMechanic("Alvin Lewis")
                .build();

        assertEquals(24, insurance.getInsuranceID());
        assertEquals(insuranceStartDate, insurance.getInsuranceStartDate());
        assertEquals(350.00, insurance.getCost());
        assertEquals("CTU", insurance.getInsuranceProvider());
        assertEquals(37484737263748, insurance.getPolicyNumber());
        assertEquals("New", insurance.getStatus());
        assertEquals("Alvin Lewis", insurance.getMechanic());
        assertTrue(true);
        assertNotNull(insurance);

    }
}
