package za.co.carhire.service.impl.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.carhire.domain.reservation.Maintenance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.MaintenanceDTO;
import za.co.carhire.repository.reservation.IMaintenanceRepository;
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.reservation.impl.MaintenanceService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for MaintenanceService
 * Sibulele Gift Nohamba (220374686)
 */
@ExtendWith(MockitoExtension.class)
class MaintenanceServiceTest {

    @Mock
    private IMaintenanceRepository maintenanceRepository;

    @Mock
    private ICarRepository carRepository;

    @InjectMocks
    private MaintenanceService maintenanceService;

    private MaintenanceDTO testMaintenanceDTO;
    private Maintenance testMaintenance;
    private Car testCar;

    @BeforeEach
    void setUp() {
        testCar = new Car.Builder()
                .setCarID(15)
                .setBrand("Toyota")
                .setModel("Corolla")
                .build();

        testMaintenance = new Maintenance.Builder()
                .setMaintenanceID(1)
                .setMaintenanceDate(new Date())
                .setDescription("Oil change and filter replacement")
                .setCost(350.00)
                .setMechanicName("John Motors")
                .setCar(testCar)
                .build();

        testMaintenanceDTO = new MaintenanceDTO(
                1,
                new Date(),
                "Oil change and filter replacement",
                350.00,
                "John Motors",
                15
        );
    }

    @Test
    void testCreate_Success() {
        // Arrange
        when(carRepository.findById(15)).thenReturn(Optional.of(testCar));
        when(maintenanceRepository.save(any(Maintenance.class))).thenReturn(testMaintenance);

        // Act
        MaintenanceDTO result = maintenanceService.create(testMaintenanceDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testMaintenanceDTO.description(), result.description());
        assertEquals(testMaintenanceDTO.cost(), result.cost());
        verify(carRepository, times(1)).findById(15);
        verify(maintenanceRepository, times(1)).save(any(Maintenance.class));
    }

    @Test
    void testCreate_CarNotFound() {
        // Arrange
        when(carRepository.findById(999)).thenReturn(Optional.empty());

        MaintenanceDTO dtoWithInvalidCar = new MaintenanceDTO(
                1,
                new Date(),
                "Oil change",
                350.00,
                "Mechanic",
                999
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            maintenanceService.create(dtoWithInvalidCar);
        });
    }

    @Test
    void testRead_Success() {
        // Arrange
        when(maintenanceRepository.findById(1)).thenReturn(Optional.of(testMaintenance));

        // Act
        Optional<MaintenanceDTO> result = maintenanceService.read(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1, result.get().maintenanceID());
        assertEquals("Oil change and filter replacement", result.get().description());
        verify(maintenanceRepository, times(1)).findById(1);
    }

    @Test
    void testRead_NotFound() {
        // Arrange
        when(maintenanceRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<MaintenanceDTO> result = maintenanceService.read(999);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAll() {
        // Arrange
        Maintenance maintenance2 = new Maintenance.Builder()
                .setMaintenanceID(2)
                .setMaintenanceDate(new Date())
                .setDescription("Brake pad replacement")
                .setCost(850.00)
                .setMechanicName("Quick Fix Auto")
                .setCar(testCar)
                .build();

        when(maintenanceRepository.findAll()).thenReturn(List.of(testMaintenance, maintenance2));

        // Act
        List<MaintenanceDTO> result = maintenanceService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(maintenanceRepository, times(1)).findAll();
    }

    @Test
    void testUpdate_Success() {
        // Arrange
        MaintenanceDTO updatedDTO = new MaintenanceDTO(
                1,
                new Date(),
                "Updated oil change",
                400.00,
                "New Mechanic",
                15
        );

        Maintenance updatedMaintenance = new Maintenance.Builder()
                .setMaintenanceID(1)
                .setMaintenanceDate(updatedDTO.maintenanceDate())
                .setDescription("Updated oil change")
                .setCost(400.00)
                .setMechanicName("New Mechanic")
                .setCar(testCar)
                .build();

        when(maintenanceRepository.findById(1)).thenReturn(Optional.of(testMaintenance));
        when(carRepository.findById(15)).thenReturn(Optional.of(testCar));
        when(maintenanceRepository.save(any(Maintenance.class))).thenReturn(updatedMaintenance);

        // Act
        MaintenanceDTO result = maintenanceService.update(1, updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated oil change", result.description());
        assertEquals(400.00, result.cost());
        verify(maintenanceRepository, times(1)).save(any(Maintenance.class));
    }

    @Test
    void testUpdate_MaintenanceNotFound() {
        // Arrange
        when(maintenanceRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            maintenanceService.update(999, testMaintenanceDTO);
        });
    }

    @Test
    void testUpdate_CarNotFound() {
        // Arrange
        MaintenanceDTO dtoWithInvalidCar = new MaintenanceDTO(
                1,
                new Date(),
                "Oil change",
                350.00,
                "Mechanic",
                999
        );

        when(maintenanceRepository.findById(1)).thenReturn(Optional.of(testMaintenance));
        when(carRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            maintenanceService.update(1, dtoWithInvalidCar);
        });
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(maintenanceRepository).deleteById(1);

        // Act
        maintenanceService.delete(1);

        // Assert
        verify(maintenanceRepository, times(1)).deleteById(1);
    }
}

