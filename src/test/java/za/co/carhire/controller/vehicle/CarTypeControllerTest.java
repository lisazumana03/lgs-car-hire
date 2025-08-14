package za.co.carhire.controller.vehicle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.service.vehicle.ICarTypeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
 */

class CarTypeControllerTest {

    @Mock
    private ICarTypeService carTypeService;

    @InjectMocks
    private CarTypeController carTypeController;

    private CarType testCarType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testCarType = new CarType(1, null, "SUV", "Petrol", 5, 4);

    }

    @Test
    void create() {
        // Arrange
        when(carTypeService.create(testCarType)).thenReturn(testCarType);

        // Act
        CarType result = carTypeController.create(testCarType);

        // Assert
        assertNotNull(result);
        assertEquals(testCarType, result);
        verify(carTypeService, times(1)).create(testCarType);
    }

    @Test
    void read() {
        // Arrange
        int carTypeId = 1;
        when(carTypeService.read(carTypeId)).thenReturn(testCarType);

        // Act
        CarType result = carTypeController.read(carTypeId);

        // Assert
        assertNotNull(result);
        assertEquals(testCarType, result);
        verify(carTypeService, times(1)).read(carTypeId);
    }

    @Test
    void update() {
        // Arrange
        when(carTypeService.update(testCarType)).thenReturn(testCarType);

        // Act
        CarType result = carTypeController.update(testCarType);

        // Assert
        assertNotNull(result);
        assertEquals(testCarType, result);
        verify(carTypeService, times(1)).update(testCarType);
    }

    @Test
    void delete() {
        // Arrange
        int carTypeId = 1;
        doNothing().when(carTypeService).delete(carTypeId);

        // Act
        carTypeController.delete(carTypeId);


        verify(carTypeService, times(1)).delete(carTypeId);
    }

    @Test
    void createWithNullCarType() {
        // Arrange
        when(carTypeService.create(null)).thenReturn(null);


        CarType result = carTypeController.create(null);


        assertNull(result);
        verify(carTypeService, times(1)).create(null);
    }

    @Test
    void readWithInvalidId() {
        // Arrange
        int invalidId = -1;
        when(carTypeService.read(invalidId)).thenReturn(null);

        // Act
        CarType result = carTypeController.read(invalidId);

        // Assert
        assertNull(result);
        verify(carTypeService, times(1)).read(invalidId);
    }

    @Test
    void updateWithNullCarType() {
        // Arrange
        when(carTypeService.update(null)).thenReturn(null);


        CarType result = carTypeController.update(null);


        assertNull(result);
        verify(carTypeService, times(1)).update(null);
    }

    @Test
    void deleteWithInvalidId() {

        int invalidId = -1;
        doNothing().when(carTypeService).delete(invalidId);

        assertDoesNotThrow(() -> carTypeController.delete(invalidId));
        verify(carTypeService, times(1)).delete(invalidId);
    }
}