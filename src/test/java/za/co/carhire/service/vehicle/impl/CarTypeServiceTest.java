package za.co.carhire.service.impl.vehicle.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.repository.vehicle.ICarTypeRepository;
import za.co.carhire.service.vehicle.impl.CarTypeService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CarTypeServiceTest {

    @Mock
    private ICarTypeRepository carTypeRepository;

    @InjectMocks
    private CarTypeService carTypeService;

    private CarType testCarType1;
    private CarType testCarType2;
    private CarType testCarType3;
    private Car testCar;

    @BeforeEach
    void setUp() {
        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("TestModel")
                .setBrand("TestBrand")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(1000.00)
                .build();

        testCarType1 = new CarType.Builder()
                .setCarTypeID(1)
                .setCar(testCar)
                .setType("SUV")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        testCarType2 = new CarType.Builder()
                .setCarTypeID(2)
                .setType("Sedan")
                .setFuelType("Diesel")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        testCarType3 = new CarType.Builder()
                .setCarTypeID(3)
                .setType("Electric")
                .setFuelType("Electric")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();
    }

    @Test
    void testCreateCarType() {
        when(carTypeRepository.save(any(CarType.class))).thenReturn(testCarType1);

        CarType createdCarType = carTypeService.create(testCarType1);

        assertNotNull(createdCarType);
        assertEquals(testCarType1.getCarTypeID(), createdCarType.getCarTypeID());
        assertEquals(testCarType1.getType(), createdCarType.getType());
        assertEquals(testCarType1.getFuelType(), createdCarType.getFuelType());
        verify(carTypeRepository, times(1)).save(testCarType1);
    }

    @Test
    void testCreateCarTypeWithNullInput() {
        when(carTypeRepository.save(null)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            carTypeService.create(null);
        });
    }

    @Test
    void testReadCarTypeByInteger() {
        when(carTypeRepository.findById(anyInt())).thenReturn(Optional.of(testCarType1));

        CarType foundCarType = carTypeService.read(Integer.valueOf(1));

        assertNotNull(foundCarType);
        assertEquals(1, foundCarType.getCarTypeID());
        assertEquals("SUV", foundCarType.getType());
        verify(carTypeRepository, times(1)).findById(1);
    }

    @Test
    void testReadCarTypeByInt() {
        when(carTypeRepository.findById(2)).thenReturn(Optional.of(testCarType2));

        CarType foundCarType = carTypeService.read(2);

        assertNotNull(foundCarType);
        assertEquals(2, foundCarType.getCarTypeID());
        assertEquals("Sedan", foundCarType.getType());
        verify(carTypeRepository, times(1)).findById(2);
    }

    @Test
    void testReadCarTypeNotFound() {
        when(carTypeRepository.findById(999)).thenReturn(Optional.empty());

        CarType foundCarType = carTypeService.read(999);

        assertNull(foundCarType);
        verify(carTypeRepository, times(1)).findById(999);
    }

    @Test
    void testUpdateCarTypeExists() {
        when(carTypeRepository.existsById(1)).thenReturn(true);
        when(carTypeRepository.save(any(CarType.class))).thenReturn(testCarType1);

        CarType updatedCarType = carTypeService.update(testCarType1);

        assertNotNull(updatedCarType);
        assertEquals(testCarType1.getCarTypeID(), updatedCarType.getCarTypeID());
        verify(carTypeRepository, times(1)).existsById(1);
        verify(carTypeRepository, times(1)).save(testCarType1);
    }

    @Test
    void testUpdateCarTypeDoesNotExist() {
        CarType nonExistentCarType = new CarType.Builder()
                .setCarTypeID(999)
                .setType("Test")
                .build();

        when(carTypeRepository.existsById(999)).thenReturn(false);

        CarType updatedCarType = carTypeService.update(nonExistentCarType);

        assertNull(updatedCarType);
        verify(carTypeRepository, times(1)).existsById(999);
        verify(carTypeRepository, never()).save(any());
    }

    @Test
    void testDeleteCarType() {
        doNothing().when(carTypeRepository).deleteById(1);

        carTypeService.delete(1);

        verify(carTypeRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteNonExistentCarType() {
        doThrow(new IllegalArgumentException()).when(carTypeRepository).deleteById(999);

        assertThrows(IllegalArgumentException.class, () -> {
            carTypeService.delete(999);
        });

        verify(carTypeRepository, times(1)).deleteById(999);
    }

    @Test
    void testGetCarTypes() {
        List<CarType> carTypeList = Arrays.asList(testCarType1, testCarType2, testCarType3);
        when(carTypeRepository.findAll()).thenReturn(carTypeList);

        Set<CarType> carTypes = carTypeService.getCarTypes();

        assertNotNull(carTypes);
        assertEquals(3, carTypes.size());
        assertTrue(carTypes.contains(testCarType1));
        assertTrue(carTypes.contains(testCarType2));
        assertTrue(carTypes.contains(testCarType3));
        verify(carTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetCarTypesEmpty() {
        when(carTypeRepository.findAll()).thenReturn(new ArrayList<>());

        Set<CarType> carTypes = carTypeService.getCarTypes();

        assertNotNull(carTypes);
        assertTrue(carTypes.isEmpty());
        verify(carTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetCarTypesSingleElement() {
        List<CarType> carTypeList = Collections.singletonList(testCarType1);
        when(carTypeRepository.findAll()).thenReturn(carTypeList);

        Set<CarType> carTypes = carTypeService.getCarTypes();

        assertNotNull(carTypes);
        assertEquals(1, carTypes.size());
        assertTrue(carTypes.contains(testCarType1));
    }

    @Test
    void testGetCarTypesByFuelType() {
        List<CarType> allCarTypes = Arrays.asList(testCarType1, testCarType2, testCarType3);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> petrolCarTypes = carTypeService.getCarTypesByFuelType("Petrol");

        assertNotNull(petrolCarTypes);
        assertEquals(1, petrolCarTypes.size());
        assertTrue(petrolCarTypes.contains(testCarType1));
        assertFalse(petrolCarTypes.contains(testCarType2)); 
        assertFalse(petrolCarTypes.contains(testCarType3)); 
        verify(carTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetCarTypesByFuelTypeCaseInsensitive() {
        List<CarType> allCarTypes = Arrays.asList(testCarType1, testCarType2, testCarType3);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> electricCarTypes = carTypeService.getCarTypesByFuelType("ELECTRIC");

        assertNotNull(electricCarTypes);
        assertEquals(1, electricCarTypes.size());
        assertTrue(electricCarTypes.contains(testCarType3));
    }

    @Test
    void testGetCarTypesByFuelTypeMultipleMatches() {
       
        CarType anotherPetrolType = new CarType.Builder()
                .setCarTypeID(4)
                .setType("Sports")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(2)
                .build();

        List<CarType> allCarTypes = Arrays.asList(testCarType1, testCarType2, testCarType3, anotherPetrolType);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> petrolCarTypes = carTypeService.getCarTypesByFuelType("Petrol");

        assertNotNull(petrolCarTypes);
        assertEquals(2, petrolCarTypes.size());
        assertTrue(petrolCarTypes.contains(testCarType1));
        assertTrue(petrolCarTypes.contains(anotherPetrolType));
    }

    @Test
    void testGetCarTypesByFuelTypeNoMatches() {
        List<CarType> allCarTypes = Arrays.asList(testCarType1, testCarType2, testCarType3);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> hydrogenCarTypes = carTypeService.getCarTypesByFuelType("Hydrogen");

        assertNotNull(hydrogenCarTypes);
        assertTrue(hydrogenCarTypes.isEmpty());
    }

    @Test
    void testGetCarTypesByFuelTypeNullFuelType() {
        CarType carTypeWithNullFuel = new CarType.Builder()
                .setCarTypeID(5)
                .setType("Test")
                .setFuelType(null)
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        List<CarType> allCarTypes = Arrays.asList(testCarType1, carTypeWithNullFuel);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> petrolCarTypes = carTypeService.getCarTypesByFuelType("Petrol");

        assertEquals(1, petrolCarTypes.size());
        assertTrue(petrolCarTypes.contains(testCarType1));
        assertFalse(petrolCarTypes.contains(carTypeWithNullFuel));
    }

    @Test
    void testGetCarTypesByFuelTypeEmptyString() {
        CarType carTypeWithEmptyFuel = new CarType.Builder()
                .setCarTypeID(6)
                .setType("Test")
                .setFuelType("")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        List<CarType> allCarTypes = Arrays.asList(testCarType1, carTypeWithEmptyFuel);
        when(carTypeRepository.findAll()).thenReturn(allCarTypes);

        List<CarType> emptyFuelTypes = carTypeService.getCarTypesByFuelType("");

        assertEquals(1, emptyFuelTypes.size());
        assertTrue(emptyFuelTypes.contains(carTypeWithEmptyFuel));
    }

    @Test
    void testUpdateCarTypePartial() {

        CarType modifiedCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setCar(testCar)
                .setType("SUV")
                .setFuelType("Hybrid") 
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        when(carTypeRepository.existsById(1)).thenReturn(true);
        when(carTypeRepository.save(any(CarType.class))).thenReturn(modifiedCarType);

        CarType updatedCarType = carTypeService.update(modifiedCarType);

        assertNotNull(updatedCarType);
        assertEquals("Hybrid", updatedCarType.getFuelType());
        assertEquals("SUV", updatedCarType.getType());
        verify(carTypeRepository, times(1)).save(modifiedCarType);
    }

    @Test
    void testServiceMethodsWithDifferentCarTypes() {
        CarType motorcycle = new CarType.Builder()
                .setCarTypeID(10)
                .setType("Motorcycle")
                .setFuelType("Petrol")
                .setNumberOfWheels(2)
                .setNumberOfSeats(2)
                .build();

        when(carTypeRepository.save(any(CarType.class))).thenReturn(motorcycle);
        when(carTypeRepository.findById(10)).thenReturn(Optional.of(motorcycle));


        CarType createdMotorcycle = carTypeService.create(motorcycle);
        assertNotNull(createdMotorcycle);
        assertEquals(2, createdMotorcycle.getNumberOfWheels());

        CarType foundMotorcycle = carTypeService.read(10);
        assertNotNull(foundMotorcycle);
        assertEquals("Motorcycle", foundMotorcycle.getType());
    }
}