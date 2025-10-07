package za.co.carhire.controller.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.carhire.dto.MaintenanceDTO;
import za.co.carhire.service.reservation.impl.MaintenanceService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Test class for MaintenanceController
 * Sibulele Gift Nohamba (220374686)
 */
@ExtendWith(MockitoExtension.class)
class MaintenanceControllerTest {

    @Mock
    private MaintenanceService maintenanceService;

    @InjectMocks
    private MaintenanceController maintenanceController;

    private MaintenanceDTO testMaintenanceDTO;

    @BeforeEach
    void setUp() {
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
    void testCreate() {
        // Arrange
        when(maintenanceService.create(any(MaintenanceDTO.class))).thenReturn(testMaintenanceDTO);

        // Act
        ResponseEntity<MaintenanceDTO> response = maintenanceController.create(testMaintenanceDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().maintenanceID());
        assertEquals("Oil change and filter replacement", response.getBody().description());
        verify(maintenanceService, times(1)).create(any(MaintenanceDTO.class));
    }

    @Test
    void testRead_Found() {
        // Arrange
        when(maintenanceService.read(1)).thenReturn(Optional.of(testMaintenanceDTO));

        // Act
        ResponseEntity<MaintenanceDTO> response = maintenanceController.read(1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().maintenanceID());
        verify(maintenanceService, times(1)).read(1);
    }

    @Test
    void testRead_NotFound() {
        // Arrange
        when(maintenanceService.read(999)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<MaintenanceDTO> response = maintenanceController.read(999);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAll() {
        // Arrange
        MaintenanceDTO maintenance2 = new MaintenanceDTO(
                2,
                new Date(),
                "Brake pad replacement",
                850.00,
                "Quick Fix Auto",
                16
        );

        when(maintenanceService.getAll()).thenReturn(List.of(testMaintenanceDTO, maintenance2));

        // Act
        ResponseEntity<List<MaintenanceDTO>> response = maintenanceController.getAll();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Oil change and filter replacement", response.getBody().get(0).description());
        assertEquals("Brake pad replacement", response.getBody().get(1).description());
        verify(maintenanceService, times(1)).getAll();
    }

    @Test
    void testUpdate() {
        // Arrange
        MaintenanceDTO updatedDTO = new MaintenanceDTO(
                1,
                new Date(),
                "Updated oil change",
                400.00,
                "New Mechanic",
                15
        );

        when(maintenanceService.update(eq(1), any(MaintenanceDTO.class))).thenReturn(updatedDTO);

        // Act
        ResponseEntity<MaintenanceDTO> response = maintenanceController.update(1, updatedDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated oil change", response.getBody().description());
        assertEquals(400.00, response.getBody().cost());
        verify(maintenanceService, times(1)).update(eq(1), any(MaintenanceDTO.class));
    }

    @Test
    void testDelete() {
        // Arrange
        doNothing().when(maintenanceService).delete(1);

        // Act
        ResponseEntity<Void> response = maintenanceController.delete(1);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(maintenanceService, times(1)).delete(1);
    }

    @Test
    void testCreate_WithHighCost() {
        // Arrange
        MaintenanceDTO expensiveMaintenanceDTO = new MaintenanceDTO(
                2,
                new Date(),
                "Engine overhaul",
                5000.00,
                "Premium Mechanic",
                17
        );

        when(maintenanceService.create(any(MaintenanceDTO.class))).thenReturn(expensiveMaintenanceDTO);

        // Act
        ResponseEntity<MaintenanceDTO> response = maintenanceController.create(expensiveMaintenanceDTO);

        // Assert
        assertNotNull(response);
        assertEquals(5000.00, response.getBody().cost());
    }
}

