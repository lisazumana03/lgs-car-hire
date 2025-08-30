package za.co.carhire.service.reservation.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.repository.reservation.IInsuranceRepository;
import za.co.carhire.repository.vehicle.ICarRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class InsuranceServiceTest {

    @Mock
    private IInsuranceRepository insuranceRepository;

    @Mock
    private ICarRepository carRepository;

    @InjectMocks
    private InsuranceService insuranceService;

    private Insurance testInsurance1;
    private Insurance testInsurance2;
    private Car testCar;
    private Date testDate;

    @BeforeEach
    void setUp() {
        testDate = new Date();

        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Fortuner")
                .setBrand("Toyota")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(1500.00)
                .build();

        testInsurance1 = new Insurance.Builder()
                .setInsuranceID(1)
                .setInsuranceStartDate(testDate)
                .setInsuranceCost(350.00)
                .setInsuranceProvider("CTU Insurance")
                .setStatus("ACTIVE")
                .setPolicyNumber(12345L)
                .setMechanic("John Doe")
                .build();

        testInsurance2 = new Insurance.Builder()
                .setInsuranceID(2)
                .setInsuranceStartDate(testDate)
                .setInsuranceCost(450.00)
                .setInsuranceProvider("SafeDrive")
                .setStatus("CANCELLED")
                .setPolicyNumber(67890L)
                .setMechanic("Jane Smith")
                .setCar(testCar)
                .build();
    }

    @Test
    void testCreateInsurance_Success() {
        when(insuranceRepository.save(any(Insurance.class))).thenReturn(testInsurance1);

        Insurance createdInsurance = insuranceService.create(testInsurance1);

        assertNotNull(createdInsurance);
        assertEquals(1, createdInsurance.getInsuranceID());
        assertEquals("CTU Insurance", createdInsurance.getInsuranceProvider());
        assertEquals("ACTIVE", createdInsurance.getStatus());
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
    }

    @Test
    void testCreateInsurance_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            insuranceService.create(null);
        });
        verify(insuranceRepository, never()).save(any());
    }

    @Test
    void testCreateInsurance_NegativeCost() {
        Insurance invalidInsurance = new Insurance.Builder()
                .setInsuranceID(3)
                .setInsuranceCost(-100.00)
                .setInsuranceProvider("Test")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            insuranceService.create(invalidInsurance);
        });
        verify(insuranceRepository, never()).save(any());
    }

    @Test
    void testCreateInsurance_EmptyProvider() {
        Insurance invalidInsurance = new Insurance.Builder()
                .setInsuranceID(3)
                .setInsuranceCost(100.00)
                .setInsuranceProvider("")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            insuranceService.create(invalidInsurance);
        });
        verify(insuranceRepository, never()).save(any());
    }

    @Test
    void testReadInsurance_Found() {
        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance1));

        Insurance foundInsurance = insuranceService.read(1);

        assertNotNull(foundInsurance);
        assertEquals(1, foundInsurance.getInsuranceID());
        assertEquals("CTU Insurance", foundInsurance.getInsuranceProvider());
        verify(insuranceRepository, times(1)).findById(1);
    }

    @Test
    void testReadInsurance_NotFound() {
        when(insuranceRepository.findById(999)).thenReturn(Optional.empty());

        Insurance foundInsurance = insuranceService.read(999);

        assertNull(foundInsurance);
        verify(insuranceRepository, times(1)).findById(999);
    }

    @Test
    void testUpdateInsurance_Exists() {
        when(insuranceRepository.existsById(1)).thenReturn(true);
        when(insuranceRepository.save(any(Insurance.class))).thenReturn(testInsurance1);

        Insurance updatedInsurance = insuranceService.update(testInsurance1);

        assertNotNull(updatedInsurance);
        assertEquals(testInsurance1.getInsuranceID(), updatedInsurance.getInsuranceID());
        verify(insuranceRepository, times(1)).existsById(1);
        verify(insuranceRepository, times(1)).save(testInsurance1);
    }

    @Test
    void testUpdateInsurance_NotExists() {
        Insurance nonExistent = new Insurance.Builder()
                .setInsuranceID(999)
                .build();

        when(insuranceRepository.existsById(999)).thenReturn(false);

        Insurance updatedInsurance = insuranceService.update(nonExistent);

        assertNull(updatedInsurance);
        verify(insuranceRepository, times(1)).existsById(999);
        verify(insuranceRepository, never()).save(any());
    }

    @Test
    void testDeleteInsurance_WithoutCar() {
        when(insuranceRepository.existsById(1)).thenReturn(true);
        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance1));
        doNothing().when(insuranceRepository).deleteById(1);

        insuranceService.deleteInsurance(1);

        verify(insuranceRepository, times(1)).deleteById(1);
        verify(carRepository, never()).save(any());
    }

    @Test
    void testDeleteInsurance_WithCar() {
        when(insuranceRepository.existsById(2)).thenReturn(true);
        when(insuranceRepository.findById(2)).thenReturn(Optional.of(testInsurance2));
        when(carRepository.save(any(Car.class))).thenReturn(testCar);
        doNothing().when(insuranceRepository).deleteById(2);

        insuranceService.deleteInsurance(2);

        verify(insuranceRepository, times(1)).deleteById(2);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testGetAll() {
        List<Insurance> insuranceList = Arrays.asList(testInsurance1, testInsurance2);
        when(insuranceRepository.findAll()).thenReturn(insuranceList);

        List<Insurance> allInsurances = insuranceService.getAll();

        assertNotNull(allInsurances);
        assertEquals(2, allInsurances.size());
        assertTrue(allInsurances.contains(testInsurance1));
        assertTrue(allInsurances.contains(testInsurance2));
        verify(insuranceRepository, times(1)).findAll();
    }

    @Test
    void testCancelInsurance_Success() {
        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance1));
        when(insuranceRepository.save(any(Insurance.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Insurance cancelledInsurance = insuranceService.cancelInsurance(1);

        assertNotNull(cancelledInsurance);
        assertEquals("CANCELLED", cancelledInsurance.getStatus());
        verify(insuranceRepository, times(1)).findById(1);
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
    }

    @Test
    void testCancelInsurance_NotFound() {
        when(insuranceRepository.findById(999)).thenReturn(Optional.empty());

        Insurance cancelledInsurance = insuranceService.cancelInsurance(999);

        assertNull(cancelledInsurance);
        verify(insuranceRepository, times(1)).findById(999);
        verify(insuranceRepository, never()).save(any());
    }

    @Test
    void testAssignInsuranceToCar_Success() {
        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance1));
        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));
        when(insuranceRepository.save(any(Insurance.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(carRepository.save(any(Car.class))).thenReturn(testCar);

        Insurance assignedInsurance = insuranceService.assignInsuranceToCar(1, 1);

        assertNotNull(assignedInsurance);
        assertNotNull(assignedInsurance.getCar());
        assertEquals(1, assignedInsurance.getCar().getCarID());
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testAssignInsuranceToCar_CarAlreadyHasInsurance() {
        Car carWithInsurance = new Car.Builder()
                .setCarID(2)
                .setModel("Test")
                .setBrand("Test")
                .setInsurance(testInsurance2)
                .build();

        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance1));
        when(carRepository.findById(2)).thenReturn(Optional.of(carWithInsurance));

        assertThrows(IllegalStateException.class, () -> {
            insuranceService.assignInsuranceToCar(1, 2);
        });
    }

    @Test
    void testGetInsurancesByStatus() {
        List<Insurance> allInsurances = Arrays.asList(testInsurance1, testInsurance2);
        when(insuranceRepository.findAll()).thenReturn(allInsurances);

        List<Insurance> activeInsurances = insuranceService.getInsurancesByStatus("ACTIVE");

        assertNotNull(activeInsurances);
        assertEquals(1, activeInsurances.size());
        assertTrue(activeInsurances.contains(testInsurance1));
        assertFalse(activeInsurances.contains(testInsurance2));
    }

    @Test
    void testGetInsurancesByProvider() {
        List<Insurance> allInsurances = Arrays.asList(testInsurance1, testInsurance2);
        when(insuranceRepository.findAll()).thenReturn(allInsurances);

        List<Insurance> ctuInsurances = insuranceService.getInsurancesByProvider("CTU Insurance");

        assertNotNull(ctuInsurances);
        assertEquals(1, ctuInsurances.size());
        assertTrue(ctuInsurances.contains(testInsurance1));
    }

    @Test
    void testGetActiveInsurances() {
        List<Insurance> allInsurances = Arrays.asList(testInsurance1, testInsurance2);
        when(insuranceRepository.findAll()).thenReturn(allInsurances);

        List<Insurance> activeInsurances = insuranceService.getActiveInsurances();

        assertNotNull(activeInsurances);
        assertEquals(1, activeInsurances.size());
        assertTrue(activeInsurances.contains(testInsurance1));
    }

    @Test
    void testGetInsurancesByCostRange() {
        List<Insurance> allInsurances = Arrays.asList(testInsurance1, testInsurance2);
        when(insuranceRepository.findAll()).thenReturn(allInsurances);

        List<Insurance> insurancesInRange = insuranceService.getInsurancesByCostRange(300.00, 400.00);

        assertNotNull(insurancesInRange);
        assertEquals(1, insurancesInRange.size());
        assertTrue(insurancesInRange.contains(testInsurance1));
        assertFalse(insurancesInRange.contains(testInsurance2));
    }

    @Test
    void testIsInsuranceValid_Valid() {
        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance1));

        boolean isValid = insuranceService.isInsuranceValid(1);

        assertTrue(isValid);
        verify(insuranceRepository, times(1)).findById(1);
    }

    @Test
    void testIsInsuranceValid_Cancelled() {
        when(insuranceRepository.findById(2)).thenReturn(Optional.of(testInsurance2));

        boolean isValid = insuranceService.isInsuranceValid(2);

        assertFalse(isValid);
    }

    @Test
    void testCanAssignToCar_Valid() {
        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance1));
        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));

        boolean canAssign = insuranceService.canAssignToCar(1, 1);

        assertTrue(canAssign);
    }

    @Test
    void testCanAssignToCar_InvalidInsurance() {
        when(insuranceRepository.findById(2)).thenReturn(Optional.of(testInsurance2));
        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));

        boolean canAssign = insuranceService.canAssignToCar(2, 1);

        assertFalse(canAssign);
    }
}