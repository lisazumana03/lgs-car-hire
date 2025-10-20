/*
 * Sibulele Gift Nohamba (220374686)
 * Date: 12/08/2025
 * */
package za.co.carhire.controller.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.carhire.dto.InsuranceDTO;
import za.co.carhire.service.reservation.impl.InsuranceService;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Test class for InsuranceController
 * Sibulele Gift Nohamba (220374686)
 * Updated with Spring Security authorization tests
 */
@ExtendWith(MockitoExtension.class)
class InsuranceControllerTest {

    @Mock
    private InsuranceService insuranceService;

    @InjectMocks
    private InsuranceController insuranceController;

    private InsuranceDTO testInsuranceDTO;

    @BeforeEach
    void setUp() {
        testInsuranceDTO = new InsuranceDTO(
                1,
                new Date(),
                1500.00,
                "Santam Insurance",
                "Active",
                "SAN2025001",
                "John Motors",
                15);
    }

    @Test
    void testCreateInsurance() {
        // Arrange
        when(insuranceService.createInsurance(any(InsuranceDTO.class))).thenReturn(testInsuranceDTO);

        // Act
        InsuranceDTO result = insuranceController.createInsurance(testInsuranceDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.insuranceID());
        assertEquals("Santam Insurance", result.insuranceProvider());
        assertEquals("SAN2025001", result.policyNumber());
        verify(insuranceService, times(1)).createInsurance(any(InsuranceDTO.class));
    }

    @Test
    void testGetInsuranceById() {
        // Arrange
        when(insuranceService.getInsuranceById(1)).thenReturn(testInsuranceDTO);

        // Act
        InsuranceDTO result = insuranceController.getInsuranceById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.insuranceID());
        assertEquals("Santam Insurance", result.insuranceProvider());
        assertEquals("SAN2025001", result.policyNumber());
        verify(insuranceService, times(1)).getInsuranceById(1);
    }

    @Test
    void testGetAllInsurances() {
        // Arrange
        InsuranceDTO insurance2 = new InsuranceDTO(
                2,
                new Date(),
                1200.00,
                "OUTsurance",
                "Active",
                "OUT2025002",
                "Quick Fix Auto",
                16);

        when(insuranceService.getAllInsurances()).thenReturn(List.of(testInsuranceDTO, insurance2));

        // Act
        List<InsuranceDTO> result = insuranceController.getAllInsurances();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Santam Insurance", result.get(0).insuranceProvider());
        assertEquals("OUTsurance", result.get(1).insuranceProvider());
        assertEquals("SAN2025001", result.get(0).policyNumber());
        assertEquals("OUT2025002", result.get(1).policyNumber());
        verify(insuranceService, times(1)).getAllInsurances();
    }

    @Test
    void testUpdateInsurance() {
        // Arrange
        InsuranceDTO updatedDTO = new InsuranceDTO(
                1,
                new Date(),
                1800.00,
                "Santam Insurance Updated",
                "Active",
                "SAN2025001",
                "New Mechanic",
                15);

        when(insuranceService.updateInsurance(eq(1), any(InsuranceDTO.class))).thenReturn(updatedDTO);

        // Act
        InsuranceDTO result = insuranceController.updateInsurance(1, updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1800.00, result.insuranceCost());
        assertEquals("Santam Insurance Updated", result.insuranceProvider());
        assertEquals("SAN2025001", result.policyNumber());
        verify(insuranceService, times(1)).updateInsurance(eq(1), any(InsuranceDTO.class));
    }

    @Test
    void testDeleteInsurance() {
        // Arrange
        doNothing().when(insuranceService).deleteInsurance(1);

        // Act
        insuranceController.deleteInsurance(1);

        // Assert
        verify(insuranceService, times(1)).deleteInsurance(1);
    }

    @Test
    void testCreateInsurance_WithCar() {
        // Arrange
        InsuranceDTO dtoWithCar = new InsuranceDTO(
                2,
                new Date(),
                1500.00,
                "Discovery Insure",
                "Active",
                "DIS2025003",
                "Premium Auto Care",
                17);

        when(insuranceService.createInsurance(any(InsuranceDTO.class))).thenReturn(dtoWithCar);

        // Act
        InsuranceDTO result = insuranceController.createInsurance(dtoWithCar);

        // Assert
        assertNotNull(result);
        assertEquals(17, result.car());
        assertEquals("DIS2025003", result.policyNumber());
        verify(insuranceService, times(1)).createInsurance(any(InsuranceDTO.class));
    }

    @Test
    void testCreateInsurance_NullCar() {
        // Arrange
        InsuranceDTO dtoWithoutCar = new InsuranceDTO(
                3,
                new Date(),
                1350.00,
                "Mutual & Federal",
                "Active",
                "MF2025004",
                "City Auto Repairs",
                null);

        when(insuranceService.createInsurance(any(InsuranceDTO.class))).thenReturn(dtoWithoutCar);

        // Act
        InsuranceDTO result = insuranceController.createInsurance(dtoWithoutCar);

        // Assert
        assertNotNull(result);
        assertNull(result.car());
        assertEquals("Mutual & Federal", result.insuranceProvider());
        verify(insuranceService, times(1)).createInsurance(any(InsuranceDTO.class));
    }
}
