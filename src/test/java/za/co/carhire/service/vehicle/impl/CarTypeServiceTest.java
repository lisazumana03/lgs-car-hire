package za.co.carhire.service.vehicle.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.repository.vehicle.ICarTypeRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarTypeServiceTest {

    @Mock
    private ICarTypeRepository carTypeRepository;

    @InjectMocks
    private CarTypeService carTypeService;

    private CarType testCarType;
    private Car testCar;

    @BeforeEach
    void setUp() {
        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2022)
                .setAvailability(true)
                .setRentalPrice(500.0)
                .build();

        testCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setType("Sedan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .setCar(testCar)
                .build();
    }

    @Test
    void testCreate_Success() {
        when(carTypeRepository.save(any(CarType.class))).thenReturn(testCarType);

        CarType result = carTypeService.create(testCarType);

        assertNotNull(result);
        assertEquals(1, result.getCarTypeID());
        assertEquals("Sedan", result.getType());
        assertEquals("Petrol", result.getFuelType());
        assertEquals(4, result.getNumberOfWheels());
        assertEquals(5, result.getNumberOfSeats());
        verify(carTypeRepository, times(1)).save(testCarType);
    }

    @Test
    @SuppressWarnings("null")
    void testCreate_NullCarType() {
        when(carTypeRepository.save(null)).thenReturn(null);

        CarType result = carTypeService.create(null);

        assertNull(result);
        verify(carTypeRepository, times(1)).save(null);
    }

    @Test
    void testReadByInteger_Found() {
        when(carTypeRepository.findById(1)).thenReturn(Optional.of(testCarType));

        CarType result = carTypeService.read(Integer.valueOf(1));

        assertNotNull(result);
        assertEquals(1, result.getCarTypeID());
        assertEquals("Sedan", result.getType());
        verify(carTypeRepository, times(1)).findById(1);
    }

    @Test
    void testReadByInteger_NotFound() {
        when(carTypeRepository.findById(999)).thenReturn(Optional.empty());

        CarType result = carTypeService.read(Integer.valueOf(999));

        assertNull(result);
        verify(carTypeRepository, times(1)).findById(999);
    }

    @Test
    void testReadByInt_Found() {
        when(carTypeRepository.findById(1)).thenReturn(Optional.of(testCarType));

        CarType result = carTypeService.read(1);

        assertNotNull(result);
        assertEquals(1, result.getCarTypeID());
        assertEquals("Sedan", result.getType());
        verify(carTypeRepository, times(1)).findById(1);
    }

    @Test
    void testReadByInt_NotFound() {
        when(carTypeRepository.findById(999)).thenReturn(Optional.empty());

        CarType result = carTypeService.read(999);

        assertNull(result);
        verify(carTypeRepository, times(1)).findById(999);
    }

    @Test
    void testUpdate_Success() {
        CarType updatedCarType = new CarType.Builder()
                .copy(testCarType)
                .setType("SUV")
                .build();

        when(carTypeRepository.existsById(1)).thenReturn(true);
        when(carTypeRepository.save(any(CarType.class))).thenReturn(updatedCarType);

        CarType result = carTypeService.update(updatedCarType);

        assertNotNull(result);
        assertEquals("SUV", result.getType());
        verify(carTypeRepository, times(1)).existsById(1);
        verify(carTypeRepository, times(1)).save(updatedCarType);
    }

    @Test
    void testUpdate_NotFound() {
        when(carTypeRepository.existsById(999)).thenReturn(false);

        CarType nonExistentCarType = new CarType.Builder()
                .setCarTypeID(999)
                .setType("Sedan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        CarType result = carTypeService.update(nonExistentCarType);

        assertNull(result);
        verify(carTypeRepository, times(1)).existsById(999);
        verify(carTypeRepository, never()).save(any(CarType.class));
    }

    @Test
    void testDelete() {
        doNothing().when(carTypeRepository).deleteById(1);

        carTypeService.delete(1);

        verify(carTypeRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetCarTypes_MultipleResults() {
        CarType carType2 = new CarType.Builder()
                .setCarTypeID(2)
                .setType("SUV")
                .setFuelType("Diesel")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        CarType carType3 = new CarType.Builder()
                .setCarTypeID(3)
                .setType("Hatchback")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        List<CarType> carTypeList = Arrays.asList(testCarType, carType2, carType3);
        when(carTypeRepository.findAll()).thenReturn(carTypeList);

        Set<CarType> result = carTypeService.getCarTypes();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains(testCarType));
        assertTrue(result.contains(carType2));
        assertTrue(result.contains(carType3));
        verify(carTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetCarTypes_EmptyResult() {
        when(carTypeRepository.findAll()).thenReturn(new ArrayList<>());

        Set<CarType> result = carTypeService.getCarTypes();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(carTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetCarTypesByFuelType_Found() {
        CarType petrolCarType2 = new CarType.Builder()
                .setCarTypeID(2)
                .setType("Hatchback")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        List<CarType> allCarTypes = Arrays.asList(testCarType, petrolCarType2);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> result = carTypeService.getCarTypesByFuelType("Petrol");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(ct -> "Petrol".equals(ct.getFuelType())));
        verify(carTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetCarTypesByFuelType_CaseInsensitive() {
        List<CarType> allCarTypes = Arrays.asList(testCarType);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> result = carTypeService.getCarTypesByFuelType("petrol");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Petrol", result.get(0).getFuelType());
        verify(carTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetCarTypesByFuelType_NotFound() {
        List<CarType> allCarTypes = Arrays.asList(testCarType);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> result = carTypeService.getCarTypesByFuelType("Electric");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(carTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetCarTypesByFuelType_NullFuelTypeInData() {
        CarType carTypeWithNullFuel = new CarType.Builder()
                .setCarTypeID(2)
                .setType("Unknown")
                .setFuelType(null)
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        List<CarType> allCarTypes = Arrays.asList(testCarType, carTypeWithNullFuel);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> result = carTypeService.getCarTypesByFuelType("Petrol");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Petrol", result.get(0).getFuelType());
        verify(carTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetCarTypesByFuelType_MixedFuelTypes() {
        CarType dieselCarType = new CarType.Builder()
                .setCarTypeID(2)
                .setType("SUV")
                .setFuelType("Diesel")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        CarType electricCarType = new CarType.Builder()
                .setCarTypeID(3)
                .setType("Electric")
                .setFuelType("Electric")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        List<CarType> allCarTypes = Arrays.asList(testCarType, dieselCarType, electricCarType);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> result = carTypeService.getCarTypesByFuelType("Diesel");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Diesel", result.get(0).getFuelType());
        assertEquals("SUV", result.get(0).getType());
        verify(carTypeRepository, times(1)).findAll();
    }
}
