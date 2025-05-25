package za.co.carhire.service.impl.vehicle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.factory.vehicle.CarTypeFactory;
import za.co.carhire.repository.vehicle.ICarTypeRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
Imtiyaaz Waggie 219374759
Date: 25/05/2025
*/

class CarTypeServiceTest {

    @Mock
    private ICarTypeRepository carTypeRepository;

    @InjectMocks
    private CarTypeService carTypeService;

    private CarType testCarType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testCarType = CarTypeFactory.createSedan(1);
    }

    @Test
    void getCarTypes() {
        when(carTypeRepository.findAll()).thenReturn(Arrays.asList(testCarType));
        assertEquals(1, carTypeService.getCarTypes().size());
    }

    @Test
    void getCarTypesByFuelType() {
        when(carTypeRepository.findByFuelType("Petrol")).thenReturn(Optional.of(testCarType));
        assertEquals(1, carTypeService.getCarTypesByFuelType("Petrol").size());
    }

    @Test
    void create() {
        when(carTypeRepository.save(testCarType)).thenReturn(testCarType);
        assertEquals(testCarType, carTypeService.create(testCarType));
    }

    @Test
    void read() {
        when(carTypeRepository.findById(1)).thenReturn(Optional.of(testCarType));
        assertEquals(testCarType, carTypeService.read(1));
    }

    @Test
    void update() {
        when(carTypeRepository.existsById(1)).thenReturn(true);
        when(carTypeRepository.save(testCarType)).thenReturn(testCarType);
        assertEquals(testCarType, carTypeService.update(testCarType));
    }

    @Test
    void delete() {
        carTypeService.delete(1);
        verify(carTypeRepository).deleteById(1);
    }
}