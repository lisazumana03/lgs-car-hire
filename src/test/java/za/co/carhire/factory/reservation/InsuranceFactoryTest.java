package za.co.carhire.factory.reservation;

/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 18/05/2025
 * Updated: 30/08/2025 - Complete test coverage with exception handling
 * */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceFactoryTest {

    private SimpleDateFormat dateFormat;
    private Date testDate;
    private Car testCar;

    @BeforeEach
    void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        testDate = dateFormat.parse("2025-05-18");

        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Fortuner")
                .setBrand("Toyota")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(1500.00)
                .build();
    }

    @Test
    void testCreateInsurance_ValidInput() {
        Insurance insurance = InsuranceFactory.createInsurance(
                24, testDate, 350.00, "CTU", "ACTIVE", 374L, "Alvin Lewis"
        );

        assertNotNull(insurance);
        assertEquals(24, insurance.getInsuranceID());
        assertEquals(testDate, insurance.getInsuranceStartDate());
        assertEquals(350.00, insurance.getInsuranceCost());
        assertEquals("CTU", insurance.getInsuranceProvider());
        assertEquals("ACTIVE", insurance.getStatus());
        assertEquals(374L, insurance.getPolicyNumber());
        assertEquals("Alvin Lewis", insurance.getMechanic());
        assertNull(insurance.getCar());
    }

    @Test
    void testCreateInsurance_InvalidID_Negative() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    -1, testDate, 350.00, "CTU", "ACTIVE", 374L, "Alvin Lewis"
            );
        });
    }

    @Test
    void testCreateInsurance_InvalidID_TooLarge() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    100001, testDate, 350.00, "CTU", "ACTIVE", 374L, "Alvin Lewis"
            );
        });
    }

    @Test
    void testCreateInsurance_NullStartDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    24, null, 350.00, "CTU", "ACTIVE", 374L, "Alvin Lewis"
            );
        });
    }

    @Test
    void testCreateInsurance_NegativeCost() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    24, testDate, -100.00, "CTU", "ACTIVE", 374L, "Alvin Lewis"
            );
        });
    }

    @Test
    void testCreateInsurance_NullProvider() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    24, testDate, 350.00, null, "ACTIVE", 374L, "Alvin Lewis"
            );
        });
    }

    @Test
    void testCreateInsurance_EmptyProvider() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    24, testDate, 350.00, "", "ACTIVE", 374L, "Alvin Lewis"
            );
        });
    }

    @Test
    void testCreateInsurance_WhitespaceProvider() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    24, testDate, 350.00, "   ", "ACTIVE", 374L, "Alvin Lewis"
            );
        });
    }

    @Test
    void testCreateInsurance_NullStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    24, testDate, 350.00, "CTU", null, 374L, "Alvin Lewis"
            );
        });
    }

    @Test
    void testCreateInsurance_EmptyStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    24, testDate, 350.00, "CTU", "", 374L, "Alvin Lewis"
            );
        });
    }

    @Test
    void testCreateInsurance_NegativePolicyNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    24, testDate, 350.00, "CTU", "ACTIVE", -1L, "Alvin Lewis"
            );
        });
    }

    @Test
    void testCreateInsurance_NullMechanic() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    24, testDate, 350.00, "CTU", "ACTIVE", 374L, null
            );
        });
    }

    @Test
    void testCreateInsurance_EmptyMechanic() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createInsurance(
                    24, testDate, 350.00, "CTU", "ACTIVE", 374L, ""
            );
        });
    }

    @Test
    void testCreateInsuranceWithCar() {
        Insurance insurance = InsuranceFactory.createInsuranceWithCar(
                25, testDate, 400.00, "SafeDrive", "ACTIVE", 999L, "John Doe", testCar
        );

        assertNotNull(insurance);
        assertEquals(25, insurance.getInsuranceID());
        assertEquals("SafeDrive", insurance.getInsuranceProvider());
        assertNotNull(insurance.getCar());
        assertEquals(testCar, insurance.getCar());
        assertEquals(1, insurance.getCar().getCarID());
    }

    @Test
    void testCreateInsuranceWithCar_NullCar() {
        Insurance insurance = InsuranceFactory.createInsuranceWithCar(
                26, testDate, 450.00, "QuickInsure", "ACTIVE", 888L, "Jane Smith", null
        );

        assertNotNull(insurance);
        assertNull(insurance.getCar());
    }

    @Test
    void testCreateActiveInsurance() {
        Insurance insurance = InsuranceFactory.createActiveInsurance(
                30, testDate, 500.00, "PremiumCover", 777L, "Bob Builder"
        );

        assertNotNull(insurance);
        assertEquals(30, insurance.getInsuranceID());
        assertEquals("ACTIVE", insurance.getStatus());
        assertEquals("PremiumCover", insurance.getInsuranceProvider());
    }

    @Test
    void testCreateCancelledInsurance() {
        Insurance insurance = InsuranceFactory.createCancelledInsurance(
                31, testDate, 550.00, "BasicCover", 666L, "Alice Wonder"
        );

        assertNotNull(insurance);
        assertEquals(31, insurance.getInsuranceID());
        assertEquals("CANCELLED", insurance.getStatus());
        assertEquals("BasicCover", insurance.getInsuranceProvider());
    }

    @Test
    void testCopyInsurance() {
        Insurance original = InsuranceFactory.createInsurance(
                40, testDate, 600.00, "TestProvider", "ACTIVE", 555L, "Test Mechanic"
        );

        Insurance copy = InsuranceFactory.copy(original, 50);

        assertNotNull(copy);
        assertEquals(50, copy.getInsuranceID()); // New ID
        // All other fields should be the same
        assertEquals(original.getInsuranceStartDate(), copy.getInsuranceStartDate());
        assertEquals(original.getInsuranceCost(), copy.getInsuranceCost());
        assertEquals(original.getInsuranceProvider(), copy.getInsuranceProvider());
        assertEquals(original.getStatus(), copy.getStatus());
        assertEquals(original.getPolicyNumber(), copy.getPolicyNumber());
        assertEquals(original.getMechanic(), copy.getMechanic());
    }

    @Test
    void testCopyInsurance_WithCar() {
        Insurance original = InsuranceFactory.createInsuranceWithCar(
                41, testDate, 650.00, "CopyProvider", "ACTIVE", 444L, "Copy Mechanic", testCar
        );

        Insurance copy = InsuranceFactory.copy(original, 51);

        assertNotNull(copy);
        assertEquals(51, copy.getInsuranceID());
        assertNotNull(copy.getCar());
        assertEquals(original.getCar(), copy.getCar());
    }

    @Test
    void testCopyInsurance_NullOriginal() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.copy(null, 60);
        });
    }

    @Test
    void testCreateMinimalInsurance() {
        Insurance insurance = InsuranceFactory.createMinimalInsurance(
                70, 700.00, "MinimalProvider"
        );

        assertNotNull(insurance);
        assertEquals(70, insurance.getInsuranceID());
        assertEquals(700.00, insurance.getInsuranceCost());
        assertEquals("MinimalProvider", insurance.getInsuranceProvider());
        assertEquals("NEW", insurance.getStatus());
        assertEquals(1000L, insurance.getPolicyNumber());
        assertEquals("Default Mechanic", insurance.getMechanic());
        assertNotNull(insurance.getInsuranceStartDate());
    }

    @Test
    void testCreateMinimalInsurance_InvalidCost() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createMinimalInsurance(71, -50.00, "Provider");
        });
    }

    @Test
    void testCreateMinimalInsurance_InvalidProvider() {
        assertThrows(IllegalArgumentException.class, () -> {
            InsuranceFactory.createMinimalInsurance(72, 100.00, "");
        });
    }

    @Test
    void testBoundaryValues_MinID() {
        Insurance insurance = InsuranceFactory.createInsurance(
                0, testDate, 350.00, "CTU", "ACTIVE", 374L, "Alvin Lewis"
        );
        assertEquals(0, insurance.getInsuranceID());
    }

    @Test
    void testBoundaryValues_MaxID() {
        Insurance insurance = InsuranceFactory.createInsurance(
                100000, testDate, 350.00, "CTU", "ACTIVE", 374L, "Alvin Lewis"
        );
        assertEquals(100000, insurance.getInsuranceID());
    }

    @Test
    void testBoundaryValues_ZeroCost() {
        Insurance insurance = InsuranceFactory.createInsurance(
                80, testDate, 0.00, "FreeCover", "ACTIVE", 333L, "Free Mechanic"
        );
        assertEquals(0.00, insurance.getInsuranceCost());
    }

    @Test
    void testBoundaryValues_ZeroPolicyNumber() {
        Insurance insurance = InsuranceFactory.createInsurance(
                81, testDate, 100.00, "TestCover", "ACTIVE", 0L, "Test Mechanic"
        );
        assertEquals(0L, insurance.getPolicyNumber());
    }

    @Test
    void testMultipleInsurances_DifferentInstances() {
        Insurance insurance1 = InsuranceFactory.createInsurance(
                90, testDate, 900.00, "Provider1", "ACTIVE", 111L, "Mechanic1"
        );

        Insurance insurance2 = InsuranceFactory.createInsurance(
                91, testDate, 950.00, "Provider2", "ACTIVE", 222L, "Mechanic2"
        );

        assertNotSame(insurance1, insurance2);
        assertNotEquals(insurance1.getInsuranceID(), insurance2.getInsuranceID());
        assertNotEquals(insurance1.getInsuranceProvider(), insurance2.getInsuranceProvider());
    }

    @Test
    void testStatusValidation_DifferentStatuses() {
        // Test different valid status values
        String[] validStatuses = {"ACTIVE", "CANCELLED", "EXPIRED", "PENDING", "SUSPENDED"};

        for (int i = 0; i < validStatuses.length; i++) {
            Insurance insurance = InsuranceFactory.createInsurance(
                    100 + i, testDate, 1000.00, "TestProvider",
                    validStatuses[i], 1000L + i, "TestMechanic"
            );
            assertEquals(validStatuses[i], insurance.getStatus());
        }
    }
}