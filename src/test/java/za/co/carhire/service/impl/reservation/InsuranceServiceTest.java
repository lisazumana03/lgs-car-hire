package za.co.carhire.service.impl.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.InsuranceDTO;
import za.co.carhire.repository.reservation.IInsuranceRepository;
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.reservation.impl.InsuranceService;
import za.co.carhire.service.vehicle.ICarService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for InsuranceService
 * Sibulele Gift Nohamba (220374686)
 */
@ExtendWith(MockitoExtension.class)
class InsuranceServiceTest {

    @Mock
    private IInsuranceRepository insuranceRepository;

    @Mock
    private ICarService carService;

    @InjectMocks
    private InsuranceService insuranceService;

    private InsuranceDTO testInsuranceDTO;
    private Insurance testInsurance;
    private Car testCar;

    @BeforeEach
    void setUp() {
        testCar = new Car.Builder()
                .setCarID(15)
                .setBrand("Toyota")
                .setModel("Corolla")
                .build();

        testInsurance = new Insurance.Builder()
                .setInsuranceID(1)
                .setInsuranceProvider("Santam Insurance")
                .setPolicyNumber(2025001L)
                .setInsuranceStartDate(new Date())
                .setInsuranceCost(1500.00)
                .setStatus("Active")
                .setMechanic("John Motors")
                .build();

        testInsuranceDTO = new InsuranceDTO(
                1,
                new Date(),
                1500.00,
                "Santam Insurance",
                "Active",
                2025001L,
                "John Motors",
                null
        );
    }

    @Test
    void testCreateInsurance_Success() {
        // Arrange
        when(insuranceRepository.save(any(Insurance.class))).thenReturn(testInsurance);

        // Act
        InsuranceDTO result = insuranceService.createInsurance(testInsuranceDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testInsuranceDTO.insuranceProvider(), result.insuranceProvider());
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
    }

    @Test
    void testGetInsuranceById_Success() {
        // Arrange
        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance));

        // Act
        InsuranceDTO result = insuranceService.getInsuranceById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.insuranceID());
        assertEquals("Santam Insurance", result.insuranceProvider());
        verify(insuranceRepository, times(1)).findById(1);
    }

    @Test
    void testGetInsuranceById_NotFound() {
        // Arrange
        when(insuranceRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            insuranceService.getInsuranceById(999);
        });
    }

    @Test
    void testGetAllInsurances() {
        // Arrange
        Insurance insurance2 = new Insurance.Builder()
                .setInsuranceID(2)
                .setInsuranceProvider("OUTsurance")
                .setPolicyNumber(2025002L)
                .setInsuranceStartDate(new Date())
                .setInsuranceCost(1200.00)
                .setStatus("Active")
                .setMechanic("Quick Fix Auto")
                .build();

        when(insuranceRepository.findAll()).thenReturn(List.of(testInsurance, insurance2));

        // Act
        List<InsuranceDTO> result = insuranceService.getAllInsurances();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(insuranceRepository, times(1)).findAll();
    }

    @Test
    void testUpdateInsurance_Success() {
        // Arrange
        InsuranceDTO updatedDTO = new InsuranceDTO(
                1,
                new Date(),
                1800.00,
                "Santam Insurance Updated",
                "Active",
                2025001L,
                "New Mechanic",
                null
        );

        Insurance updatedInsurance = new Insurance.Builder()
                .setInsuranceID(1)
                .setInsuranceProvider("Santam Insurance Updated")
                .setPolicyNumber(2025001L)
                .setInsuranceStartDate(updatedDTO.insuranceStartDate())
                .setInsuranceCost(1800.00)
                .setStatus("Active")
                .setMechanic("New Mechanic")
                .build();

        when(insuranceRepository.findById(1)).thenReturn(Optional.of(testInsurance));
        when(insuranceRepository.save(any(Insurance.class))).thenReturn(updatedInsurance);

        // Act
        InsuranceDTO result = insuranceService.updateInsurance(1, updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1800.00, result.insuranceCost());
        assertEquals("Santam Insurance Updated", result.insuranceProvider());
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
    }

    @Test
    void testUpdateInsurance_NotFound() {
        // Arrange
        when(insuranceRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            insuranceService.updateInsurance(999, testInsuranceDTO);
        });
    }

    @Test
    void testDeleteInsurance_Success() {
        // Arrange
        when(insuranceRepository.existsById(1)).thenReturn(true);
        doNothing().when(insuranceRepository).deleteById(1);

        // Act
        insuranceService.deleteInsurance(1);

        // Assert
        verify(insuranceRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteInsurance_NotFound() {
        // Arrange
        when(insuranceRepository.existsById(999)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            insuranceService.deleteInsurance(999);
        });
    }
}

