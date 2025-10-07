package za.co.carhire.service.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.repository.reservation.IInsuranceRepository;
import za.co.carhire.service.reservation.impl.InsuranceService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * InsuranceServiceTest
 * Unit tests for InsuranceService
 * Tests all CRUD operations and business logic
 * Author: System Generated
 * Date: 2025-10-07
 */
@ExtendWith(MockitoExtension.class)
class InsuranceServiceTest {

    @Mock
    private IInsuranceRepository insuranceRepository;

    @InjectMocks
    private InsuranceService insuranceService;

    private Insurance testInsurance1;
    private Insurance testInsurance2;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        testInsurance1 = new Insurance.Builder()
                .setInsuranceID(1)
                .setInsuranceStartDate(dateFormat.parse("2025-01-15"))
                .setInsuranceCost(350.00)
                .setInsuranceProvider("Discovery Insure")
                .setPolicyNumber(1001234567L)
                .setStatus("ACTIVE")
                .setMechanic("John Mechanic")
                .build();

        testInsurance2 = new Insurance.Builder()
                .setInsuranceID(2)
                .setInsuranceStartDate(dateFormat.parse("2025-02-20"))
                .setInsuranceCost(450.00)
                .setInsuranceProvider("OUTsurance")
                .setPolicyNumber(1002345678L)
                .setStatus("ACTIVE")
                .setMechanic("Jane Mechanic")
                .build();
    }

    @Test
    void testCreate_Success() {
        // Given
        when(insuranceRepository.save(any(Insurance.class))).thenReturn(testInsurance1);

        // When
        Insurance created = insuranceService.create(testInsurance1);

        // Then
        assertNotNull(created);
        assertEquals(1, created.getInsuranceID());
        assertEquals(350.00, created.getInsuranceCost());
        assertEquals("Discovery Insure", created.getInsuranceProvider());
        verify(insuranceRepository, times(1)).save(testInsurance1);
    }

    @Test
    void testCreate_WithNullInsurance() {
        // When
        Insurance created = insuranceService.create(null);

        // Then
        assertNull(created);
        verify(insuranceRepository, times(1)).save(null);
    }

    @Test
    void testRead_Success() {
        // Given
        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance1));

        // When
        Insurance found = insuranceService.read(1);

        // Then
        assertNotNull(found);
        assertEquals(1, found.getInsuranceID());
        assertEquals("Discovery Insure", found.getInsuranceProvider());
        verify(insuranceRepository, times(1)).findById(1);
    }

    @Test
    void testRead_NotFound() {
        // Given
        when(insuranceRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Insurance found = insuranceService.read(999);

        // Then
        assertNull(found);
        verify(insuranceRepository, times(1)).findById(999);
    }

    @Test
    void testUpdate_Success() {
        // Given
        Insurance updatedInsurance = new Insurance.Builder()
                .setInsuranceID(1)
                .setInsuranceStartDate(testInsurance1.getInsuranceStartDate())
                .setInsuranceCost(400.00) // Updated cost
                .setInsuranceProvider("Discovery Insure")
                .setPolicyNumber(1001234567L)
                .setStatus("ACTIVE")
                .setMechanic("John Mechanic")
                .build();

        when(insuranceRepository.save(any(Insurance.class))).thenReturn(updatedInsurance);

        // When
        Insurance result = insuranceService.update(updatedInsurance);

        // Then
        assertNotNull(result);
        assertEquals(400.00, result.getInsuranceCost());
        verify(insuranceRepository, times(1)).save(updatedInsurance);
    }

    @Test
    void testUpdate_WithNullInsurance() {
        // When
        Insurance result = insuranceService.update(null);

        // Then
        assertNull(result);
        verify(insuranceRepository, never()).save(any());
    }

    @Test
    void testDelete_Success() {
        // Given
        doNothing().when(insuranceRepository).deleteById(1);

        // When
        insuranceService.deleteInsurance(1);

        // Then
        verify(insuranceRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetAll_Success() {
        // Given
        List<Insurance> insuranceList = Arrays.asList(testInsurance1, testInsurance2);
        when(insuranceRepository.findAll()).thenReturn(insuranceList);

        // When
        List<Insurance> result = insuranceService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Discovery Insure", result.get(0).getInsuranceProvider());
        assertEquals("OUTsurance", result.get(1).getInsuranceProvider());
        verify(insuranceRepository, times(1)).findAll();
    }

    @Test
    void testGetAll_EmptyList() {
        // Given
        when(insuranceRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Insurance> result = insuranceService.getAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(insuranceRepository, times(1)).findAll();
    }

    @Test
    void testCancelInsurance_Success() {
        // Given
        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance1));
        when(insuranceRepository.save(any(Insurance.class))).thenAnswer(invocation -> {
            Insurance ins = invocation.getArgument(0);
            assertEquals("CANCELLED", ins.getStatus());
            return ins;
        });

        // When
        insuranceService.cancelInsurance(1);

        // Then
        verify(insuranceRepository, times(1)).findById(1);
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
    }

    @Test
    void testCancelInsurance_NotFound() {
        // Given
        when(insuranceRepository.findById(999)).thenReturn(Optional.empty());

        // When
        insuranceService.cancelInsurance(999);

        // Then
        verify(insuranceRepository, times(1)).findById(999);
        verify(insuranceRepository, never()).save(any());
    }

    @Test
    void testCreate_VerifyAllFields() throws ParseException {
        // Given
        Date startDate = dateFormat.parse("2025-03-10");
        Insurance fullInsurance = new Insurance.Builder()
                .setInsuranceID(3)
                .setInsuranceStartDate(startDate)
                .setInsuranceCost(500.00)
                .setInsuranceProvider("Santam")
                .setPolicyNumber(1003456789L)
                .setStatus("PENDING")
                .setMechanic("Bob Mechanic")
                .build();

        when(insuranceRepository.save(any(Insurance.class))).thenReturn(fullInsurance);

        // When
        Insurance created = insuranceService.create(fullInsurance);

        // Then
        assertNotNull(created);
        assertEquals(3, created.getInsuranceID());
        assertEquals(startDate, created.getInsuranceStartDate());
        assertEquals(500.00, created.getInsuranceCost());
        assertEquals("Santam", created.getInsuranceProvider());
        assertEquals(1003456789L, created.getPolicyNumber());
        assertEquals("PENDING", created.getStatus());
        assertEquals("Bob Mechanic", created.getMechanic());
    }

    @Test
    void testUpdate_ChangeStatus() {
        // Given
        Insurance insuranceWithNewStatus = new Insurance.Builder()
                .copy(testInsurance1)
                .build();
        insuranceWithNewStatus.setStatus("EXPIRED");

        when(insuranceRepository.save(any(Insurance.class))).thenReturn(insuranceWithNewStatus);

        // When
        Insurance updated = insuranceService.update(insuranceWithNewStatus);

        // Then
        assertNotNull(updated);
        assertEquals("EXPIRED", updated.getStatus());
        verify(insuranceRepository, times(1)).save(insuranceWithNewStatus);
    }

    @Test
    void testUpdate_ChangeMechanic() {
        // Given
        Insurance insuranceWithNewMechanic = new Insurance.Builder()
                .copy(testInsurance1)
                .build();
        insuranceWithNewMechanic.setMechanic("New Mechanic");

        when(insuranceRepository.save(any(Insurance.class))).thenReturn(insuranceWithNewMechanic);

        // When
        Insurance updated = insuranceService.update(insuranceWithNewMechanic);

        // Then
        assertNotNull(updated);
        assertEquals("New Mechanic", updated.getMechanic());
        verify(insuranceRepository, times(1)).save(insuranceWithNewMechanic);
    }

    @Test
    void testGetAll_VerifyMultipleProviders() {
        // Given
        List<Insurance> insuranceList = Arrays.asList(testInsurance1, testInsurance2);
        when(insuranceRepository.findAll()).thenReturn(insuranceList);

        // When
        List<Insurance> result = insuranceService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        Set<String> providers = new HashSet<>();
        for (Insurance insurance : result) {
            providers.add(insurance.getInsuranceProvider());
        }

        assertTrue(providers.contains("Discovery Insure"));
        assertTrue(providers.contains("OUTsurance"));
    }

    @Test
    void testRead_VerifyPolicyNumber() {
        // Given
        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance1));

        // When
        Insurance found = insuranceService.read(1);

        // Then
        assertNotNull(found);
        assertEquals(1001234567L, found.getPolicyNumber());
    }

    @Test
    void testCancelInsurance_VerifyStatusChange() {
        // Given
        Insurance activeInsurance = new Insurance.Builder()
                .copy(testInsurance1)
                .build();
        activeInsurance.setStatus("ACTIVE");

        when(insuranceRepository.findById(1)).thenReturn(Optional.of(activeInsurance));
        when(insuranceRepository.save(any(Insurance.class))).thenAnswer(invocation -> {
            Insurance saved = invocation.getArgument(0);
            assertEquals("CANCELLED", saved.getStatus());
            assertEquals(1, saved.getInsuranceID());
            return saved;
        });

        // When
        insuranceService.cancelInsurance(1);

        // Then
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
    }
}
