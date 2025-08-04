package za.co.carhire.controller.vehicle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.service.vehicle.ICarService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

class CarControllerTest {

    @Mock
    private ICarService carService;

    @InjectMocks
    private CarController carController;

    private Car testCar;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a test car object
        testCar = new Car();
        // Set test car properties - adjust these based on your Car class structure
        // testCar.setId(1);
        // testCar.setBrand("Toyota");
        // testCar.setModel("Camry");
        // testCar.setYear(2023);
    }

    @Test
    void create() {
        // Arrange
        when(carService.create(testCar)).thenReturn(testCar);

        // Act
        Car result = carController.create(testCar);

        // Assert
        assertNotNull(result);
        assertEquals(testCar, result);
        verify(carService, times(1)).create(testCar);
    }

    @Test
    void read() {
        // Arrange
        int carId = 1;
        when(carService.read(carId)).thenReturn(testCar);

        // Act
        Car result = carController.read(carId);

        // Assert
        assertNotNull(result);
        assertEquals(testCar, result);
        verify(carService, times(1)).read(carId);
    }

    @Test
    void update() {
        // Arrange
        when(carService.update(testCar)).thenReturn(testCar);

        // Act
        Car result = carController.update(testCar);

        // Assert
        assertNotNull(result);
        assertEquals(testCar, result);
        verify(carService, times(1)).update(testCar);
    }

    @Test
    void delete() {
        // Arrange
        int carId = 1;
        doNothing().when(carService).delete(carId);

        // Act
        carController.delete(carId);

        // Assert
        verify(carService, times(1)).delete(carId);
    }

    @Test
    void createWithNullCar() {
        // Arrange
        when(carService.create(null)).thenReturn(null);

        // Act
        Car result = carController.create(null);

        // Assert
        assertNull(result);
        verify(carService, times(1)).create(null);
    }

    @Test
    void readWithInvalidId() {
        // Arrange
        int invalidId = -1;
        when(carService.read(invalidId)).thenReturn(null);

        // Act
        Car result = carController.read(invalidId);

        // Assert
        assertNull(result);
        verify(carService, times(1)).read(invalidId);
    }

    @Test
    void updateWithNullCar() {
        // Arrange
        when(carService.update(null)).thenReturn(null);

        // Act
        Car result = carController.update(null);

        // Assert
        assertNull(result);
        verify(carService, times(1)).update(null);
    }

    @Test
    void deleteWithInvalidId() {
        // Arrange
        int invalidId = -1;
        doNothing().when(carService).delete(invalidId);

        // Act & Assert
        assertDoesNotThrow(() -> carController.delete(invalidId));
        verify(carService, times(1)).delete(invalidId);
    }
}