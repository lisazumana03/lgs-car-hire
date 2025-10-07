package za.co.carhire.factory.reservation;

/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 18/05/2025
 * Enhanced: 2025-10-07 - Added comprehensive test coverage
 * */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.Insurance;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class InsuranceFactoryTest {

    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    void testCreateInsurance_AllFields() throws ParseException {
        // Given
        Date insuranceStartDate = dateFormat.parse("2025-05-18");

        // When
        Insurance insurance = new Insurance.Builder()
                .setInsuranceID(24)
                .setInsuranceStartDate(insuranceStartDate)
                .setInsuranceCost(350.00)
                .setInsuranceProvider("CTU")
                .setPolicyNumber(374)
                .setStatus("New")
                .setMechanic("Alvin Lewis")
                .build();

        // Then
        assertNotNull(insurance);
        assertEquals(24, insurance.getInsuranceID());
        assertEquals(insuranceStartDate, insurance.getInsuranceStartDate());
        assertEquals(350.00, insurance.getInsuranceCost());
        assertEquals("CTU", insurance.getInsuranceProvider());
        assertEquals(374, insurance.getPolicyNumber());
        assertEquals("New", insurance.getStatus());
        assertEquals("Alvin Lewis", insurance.getMechanic());
    }

    @Test
    void testCreateInsurance_DiscoveryInsure() throws ParseException {
        // Given
        Date startDate = dateFormat.parse("2025-01-15");

        // When
        Insurance insurance = new Insurance.Builder()
                .setInsuranceID(1)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(400.00)
                .setInsuranceProvider("Discovery Insure")
                .setPolicyNumber(1001234567L)
                .setStatus("ACTIVE")
                .setMechanic("John Smith")
                .build();

        // Then
        assertNotNull(insurance);
        assertEquals("Discovery Insure", insurance.getInsuranceProvider());
        assertEquals("ACTIVE", insurance.getStatus());
        assertEquals(400.00, insurance.getInsuranceCost());
    }

    @Test
    void testCreateInsurance_OUTsurance() throws ParseException {
        // Given
        Date startDate = dateFormat.parse("2025-02-20");

        // When
        Insurance insurance = new Insurance.Builder()
                .setInsuranceID(2)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(450.00)
                .setInsuranceProvider("OUTsurance")
                .setPolicyNumber(1002345678L)
                .setStatus("ACTIVE")
                .setMechanic("Jane Doe")
                .build();

        // Then
        assertNotNull(insurance);
        assertEquals("OUTsurance", insurance.getInsuranceProvider());
        assertEquals(450.00, insurance.getInsuranceCost());
    }

    @Test
    void testCreateInsurance_Santam() throws ParseException {
        // Given
        Date startDate = dateFormat.parse("2025-03-10");

        // When
        Insurance insurance = new Insurance.Builder()
                .setInsuranceID(3)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(500.00)
                .setInsuranceProvider("Santam")
                .setPolicyNumber(1003456789L)
                .setStatus("PENDING")
                .setMechanic("Bob Johnson")
                .build();

        // Then
        assertNotNull(insurance);
        assertEquals("Santam", insurance.getInsuranceProvider());
        assertEquals("PENDING", insurance.getStatus());
    }

    @Test
    void testCreateInsurance_MinimalFields() {
        // When
        Insurance insurance = new Insurance.Builder()
                .setInsuranceID(100)
                .setInsuranceCost(300.00)
                .setInsuranceProvider("Basic Provider")
                .build();

        // Then
        assertNotNull(insurance);
        assertEquals(100, insurance.getInsuranceID());
        assertEquals(300.00, insurance.getInsuranceCost());
        assertEquals("Basic Provider", insurance.getInsuranceProvider());
        assertNull(insurance.getInsuranceStartDate());
        assertNull(insurance.getStatus());
        assertNull(insurance.getMechanic());
    }

    @Test
    void testCopy_AllFields() throws ParseException {
        // Given
        Date startDate = dateFormat.parse("2025-04-01");
        Insurance original = new Insurance.Builder()
                .setInsuranceID(10)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(600.00)
                .setInsuranceProvider("MiWay")
                .setPolicyNumber(1004567890L)
                .setStatus("ACTIVE")
                .setMechanic("Alice Williams")
                .build();

        // When
        Insurance copied = new Insurance.Builder()
                .copy(original)
                .build();

        // Then
        assertNotNull(copied);
        assertEquals(original.getInsuranceID(), copied.getInsuranceID());
        assertEquals(original.getInsuranceStartDate(), copied.getInsuranceStartDate());
        assertEquals(original.getInsuranceCost(), copied.getInsuranceCost());
        assertEquals(original.getInsuranceProvider(), copied.getInsuranceProvider());
        assertEquals(original.getPolicyNumber(), copied.getPolicyNumber());
        assertEquals(original.getStatus(), copied.getStatus());
        assertEquals(original.getMechanic(), copied.getMechanic());
    }

    @Test
    void testCopy_ThenModify() throws ParseException {
        // Given
        Date startDate = dateFormat.parse("2025-05-01");
        Insurance original = new Insurance.Builder()
                .setInsuranceID(20)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(550.00)
                .setInsuranceProvider("King Price")
                .setPolicyNumber(1005678901L)
                .setStatus("ACTIVE")
                .setMechanic("Charlie Brown")
                .build();

        // When
        Insurance modified = new Insurance.Builder()
                .copy(original)
                .setInsuranceCost(650.00) // Modify cost
                .setStatus("RENEWED") // Modify status
                .build();

        // Then
        assertNotNull(modified);
        assertEquals(original.getInsuranceID(), modified.getInsuranceID());
        assertEquals(650.00, modified.getInsuranceCost()); // Changed
        assertEquals("RENEWED", modified.getStatus()); // Changed
        assertEquals(original.getInsuranceProvider(), modified.getInsuranceProvider()); // Same
        assertEquals(original.getMechanic(), modified.getMechanic()); // Same
    }

    @Test
    void testBuilder_ChainedCalls() throws ParseException {
        // Given/When
        Date startDate = dateFormat.parse("2025-06-15");
        Insurance insurance = new Insurance.Builder()
                .setInsuranceID(30)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(700.00)
                .setInsuranceProvider("Budget Insurance")
                .setPolicyNumber(1006789012L)
                .setStatus("EXPIRED")
                .setMechanic("David Lee")
                .build();

        // Then
        assertNotNull(insurance);
        assertEquals(30, insurance.getInsuranceID());
        assertEquals(startDate, insurance.getInsuranceStartDate());
        assertEquals(700.00, insurance.getInsuranceCost());
        assertEquals("Budget Insurance", insurance.getInsuranceProvider());
        assertEquals(1006789012L, insurance.getPolicyNumber());
        assertEquals("EXPIRED", insurance.getStatus());
        assertEquals("David Lee", insurance.getMechanic());
    }

    @Test
    void testInsurance_StatusTypes() throws ParseException {
        // Test different status types
        Date startDate = dateFormat.parse("2025-07-01");

        // Active
        Insurance activeInsurance = new Insurance.Builder()
                .setInsuranceID(40)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(350.00)
                .setInsuranceProvider("Provider A")
                .setStatus("ACTIVE")
                .build();
        assertEquals("ACTIVE", activeInsurance.getStatus());

        // Pending
        Insurance pendingInsurance = new Insurance.Builder()
                .setInsuranceID(41)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(350.00)
                .setInsuranceProvider("Provider B")
                .setStatus("PENDING")
                .build();
        assertEquals("PENDING", pendingInsurance.getStatus());

        // Expired
        Insurance expiredInsurance = new Insurance.Builder()
                .setInsuranceID(42)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(350.00)
                .setInsuranceProvider("Provider C")
                .setStatus("EXPIRED")
                .build();
        assertEquals("EXPIRED", expiredInsurance.getStatus());

        // Cancelled
        Insurance cancelledInsurance = new Insurance.Builder()
                .setInsuranceID(43)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(350.00)
                .setInsuranceProvider("Provider D")
                .setStatus("CANCELLED")
                .build();
        assertEquals("CANCELLED", cancelledInsurance.getStatus());
    }

    @Test
    void testInsurance_DifferentCosts() {
        // Test various cost values
        Insurance lowCost = new Insurance.Builder()
                .setInsuranceID(50)
                .setInsuranceCost(200.00)
                .setInsuranceProvider("Economy Insurance")
                .build();
        assertEquals(200.00, lowCost.getInsuranceCost());

        Insurance mediumCost = new Insurance.Builder()
                .setInsuranceID(51)
                .setInsuranceCost(500.00)
                .setInsuranceProvider("Standard Insurance")
                .build();
        assertEquals(500.00, mediumCost.getInsuranceCost());

        Insurance highCost = new Insurance.Builder()
                .setInsuranceID(52)
                .setInsuranceCost(1000.00)
                .setInsuranceProvider("Premium Insurance")
                .build();
        assertEquals(1000.00, highCost.getInsuranceCost());
    }

    @Test
    void testToString() throws ParseException {
        // Given
        Date startDate = dateFormat.parse("2025-08-01");
        Insurance insurance = new Insurance.Builder()
                .setInsuranceID(60)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(400.00)
                .setInsuranceProvider("Test Provider")
                .setPolicyNumber(1234567890L)
                .setStatus("ACTIVE")
                .setMechanic("Test Mechanic")
                .build();

        // When
        String toString = insurance.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("insuranceID=60"));
        assertTrue(toString.contains("insuranceCost=400.0"));
        assertTrue(toString.contains("insuranceProvider='Test Provider'"));
        assertTrue(toString.contains("status='ACTIVE'"));
        assertTrue(toString.contains("mechanic='Test Mechanic'"));
    }
}
