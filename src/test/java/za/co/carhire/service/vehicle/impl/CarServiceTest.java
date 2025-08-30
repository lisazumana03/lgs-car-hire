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
import za.co.carhire.repository.vehicle.ICarRepository;
import za.co.carhire.service.vehicle.impl.CarService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CarServiceTest {

    @Mock
    private ICarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car testCar1;
    private Car testCar2;
    private Car testCar3;
    private CarType testCarType;

    @BeforeEach
    void setUp() {
        // Setup test car type
        testCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setType("SUV")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        // Setup test cars
        testCar1 = new Car.Builder()
                .setCarID(1)
                .setModel("Fortuner")
                .setBrand("Toyota")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(1500.00)
                .setCarType(testCarType)
                .build();

        testCar2 = new Car.Builder()
                .setCarID(2)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2023)
                .setAvailability(false)
                .setRentalPrice(800.00)
                .build();

        testCar3 = new Car.Builder()
                .setCarID(3)
                .setModel("X5")
                .setBrand("BMW")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(2000.00)
                .build();
    }

    @Test
    void testCreateCar() {
        when(carRepository.save(any(Car.class))).thenReturn(testCar1);

        Car createdCar = carService.create(testCar1);

        assertNotNull(createdCar);
        assertEquals(testCar1.getCarID(), createdCar.getCarID());
        assertEquals(testCar1.getModel(), createdCar.getModel());
        assertEquals(testCar1.getBrand(), createdCar.getBrand());
        verify(carRepository, times(1)).save(testCar1);
    }

    @Test
    void testCreateCarWithNullInput() {
        when(carRepository.save(null)).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            carService.create(null);
        });
    }

    @Test
    void testReadCarByInteger() {
        when(carRepository.findById(anyInt())).thenReturn(Optional.of(testCar1));

        Car foundCar = carService.read(Integer.valueOf(1));

        assertNotNull(foundCar);
        assertEquals(1, foundCar.getCarID());
        assertEquals("Fortuner", foundCar.getModel());
        verify(carRepository, times(1)).findById(1);
    }

    @Test
    void testReadCarByInt() {
        when(carRepository.findById(2)).thenReturn(Optional.of(testCar2));

        Car foundCar = carService.read(2);

        assertNotNull(foundCar);
        assertEquals(2, foundCar.getCarID());
        assertEquals("Corolla", foundCar.getModel());
        verify(carRepository, times(1)).findById(2);
    }

    @Test
    void testReadCarNotFound() {
        when(carRepository.findById(999)).thenReturn(Optional.empty());

        Car foundCar = carService.read(999);

        assertNull(foundCar);
        verify(carRepository, times(1)).findById(999);
    }

    @Test
    void testUpdateCarExists() {
        when(carRepository.existsById(1)).thenReturn(true);
        when(carRepository.save(any(Car.class))).thenReturn(testCar1);

        Car updatedCar = carService.update(testCar1);

        assertNotNull(updatedCar);
        assertEquals(testCar1.getCarID(), updatedCar.getCarID());
        verify(carRepository, times(1)).existsById(1);
        verify(carRepository, times(1)).save(testCar1);
    }

    @Test
    void testUpdateCarDoesNotExist() {
        when(carRepository.existsById(999)).thenReturn(false);

        Car updatedCar = carService.update(new Car.Builder().setCarID(999).build());

        assertNull(updatedCar);
        verify(carRepository, times(1)).existsById(999);
        verify(carRepository, never()).save(any());
    }

    @Test
    void testDeleteCar() {
        doNothing().when(carRepository).deleteById(1);

        carService.delete(1);

        verify(carRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteNonExistentCar() {
        doThrow(new IllegalArgumentException()).when(carRepository).deleteById(999);

        assertThrows(IllegalArgumentException.class, () -> {
            carService.delete(999);
        });

        verify(carRepository, times(1)).deleteById(999);
    }

    @Test
    void testGetCars() {
        List<Car> carList = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(carList);

        Set<Car> cars = carService.getCars();

        assertNotNull(cars);
        assertEquals(3, cars.size());
        assertTrue(cars.contains(testCar1));
        assertTrue(cars.contains(testCar2));
        assertTrue(cars.contains(testCar3));
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsEmpty() {
        when(carRepository.findAll()).thenReturn(new ArrayList<>());

        Set<Car> cars = carService.getCars();

        assertNotNull(cars);
        assertTrue(cars.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByBrand() {
        List<Car> allCars = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> toyotaCars = carService.getCarsByBrand("Toyota");

        assertNotNull(toyotaCars);
        assertEquals(2, toyotaCars.size());
        assertTrue(toyotaCars.contains(testCar1));
        assertTrue(toyotaCars.contains(testCar2));
        assertFalse(toyotaCars.contains(testCar3));
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByBrandCaseInsensitive() {
        List<Car> allCars = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> toyotaCars = carService.getCarsByBrand("TOYOTA");

        assertEquals(2, toyotaCars.size());
        assertTrue(toyotaCars.contains(testCar1));
        assertTrue(toyotaCars.contains(testCar2));
    }

    @Test
    void testGetCarsByBrandNoMatches() {
        List<Car> allCars = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> mercedesCars = carService.getCarsByBrand("Mercedes");

        assertNotNull(mercedesCars);
        assertTrue(mercedesCars.isEmpty());
    }

    @Test
    void testGetCarsByBrandNullBrand() {
        Car carWithNullBrand = new Car.Builder()
                .setCarID(4)
                .setModel("Test")
                .setBrand(null)
                .build();
        
        List<Car> allCars = Arrays.asList(testCar1, carWithNullBrand);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> toyotaCars = carService.getCarsByBrand("Toyota");

        assertEquals(1, toyotaCars.size());
        assertTrue(toyotaCars.contains(testCar1));
        assertFalse(toyotaCars.contains(carWithNullBrand));
    }

    @Test
    void testGetAvailableCars() {
        List<Car> allCars = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> availableCars = carService.getAvailableCars();

        assertNotNull(availableCars);
        assertEquals(2, availableCars.size());
        assertTrue(availableCars.contains(testCar1));
        assertFalse(availableCars.contains(testCar2));
        assertTrue(availableCars.contains(testCar3));
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetAvailableCarsNoneAvailable() {
        List<Car> allCars = Arrays.asList(testCar2); 
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> availableCars = carService.getAvailableCars();

        assertNotNull(availableCars);
        assertTrue(availableCars.isEmpty());
    }

    @Test
    void testUpdateAvailabilitySuccess() {
        when(carRepository.findById(anyInt())).thenReturn(Optional.of(testCar1));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Car updatedCar = carService.updateAvailability(1, false);

        assertNotNull(updatedCar);
        assertFalse(updatedCar.isAvailability());
        verify(carRepository, times(1)).findById(1);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testUpdateAvailabilityCarNotFound() {
        when(carRepository.findById(anyInt())).thenReturn(Optional.empty());

        Car updatedCar = carService.updateAvailability(999, true);

        assertNull(updatedCar);
        verify(carRepository, times(1)).findById(999);
        verify(carRepository, never()).save(any());
    }

    @Test
    void testGetCarsByPriceRange() {
        List<Car> allCars = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> carsInRange = carService.getCarsByPriceRange(700.00, 1600.00);

        assertNotNull(carsInRange);
        assertEquals(2, carsInRange.size());
        assertTrue(carsInRange.contains(testCar1)); 
        assertTrue(carsInRange.contains(testCar2)); 
        assertFalse(carsInRange.contains(testCar3)); 
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByPriceRangeInclusive() {
        List<Car> allCars = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> carsInRange = carService.getCarsByPriceRange(800.00, 2000.00);

        assertEquals(3, carsInRange.size()); 
    }

    @Test
    void testGetCarsByPriceRangeNoMatches() {
        List<Car> allCars = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> carsInRange = carService.getCarsByPriceRange(100.00, 500.00);

        assertNotNull(carsInRange);
        assertTrue(carsInRange.isEmpty());
    }

    @Test
    void testGetCarsByYear() {
        List<Car> allCars = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> cars2024 = carService.getCarsByYear(2024);

        assertNotNull(cars2024);
        assertEquals(2, cars2024.size());
        assertTrue(cars2024.contains(testCar1));
        assertFalse(cars2024.contains(testCar2)); 
        assertTrue(cars2024.contains(testCar3));
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByYearNoMatches() {
        List<Car> allCars = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> cars2020 = carService.getCarsByYear(2020);

        assertNotNull(cars2020);
        assertTrue(cars2020.isEmpty());
    }

    @Test
    void testGetCarsByYearSingleMatch() {
        List<Car> allCars = Arrays.asList(testCar1, testCar2, testCar3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> cars2023 = carService.getCarsByYear(2023);

        assertNotNull(cars2023);
        assertEquals(1, cars2023.size());
        assertTrue(cars2023.contains(testCar2));
    }
}