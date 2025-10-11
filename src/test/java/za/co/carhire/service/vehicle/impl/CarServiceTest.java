package za.co.carhire.service.vehicle.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.repository.vehicle.ICarRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private ICarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car testCar;
    private Car testCar2;
    private CarType testCarType;

    @BeforeEach
    void setUp() {
        testCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setType("Sedan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2022)
                .setAvailability(true)
                .setRentalPrice(500.0)
                .setImageData("test-image-data".getBytes())
                .setImageName("image.jpg")
                .setImageType("image/jpeg")
                .setCarType(testCarType)
                .build();

        testCar2 = new Car.Builder()
                .setCarID(2)
                .setModel("Civic")
                .setBrand("Honda")
                .setYear(2023)
                .setAvailability(false)
                .setRentalPrice(550.0)
                .build();
    }

    @Test
    void testCreate() {
        when(carRepository.save(any(Car.class))).thenReturn(testCar);

        Car createdCar = carService.create(testCar);

        assertNotNull(createdCar);
        assertEquals(testCar.getCarID(), createdCar.getCarID());
        assertEquals(testCar.getModel(), createdCar.getModel());
        verify(carRepository, times(1)).save(testCar);
    }

    @Test
    void testReadByIntegerFound() {
        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));

        Car foundCar = carService.read(Integer.valueOf(1));

        assertNotNull(foundCar);
        assertEquals(1, foundCar.getCarID());
        assertEquals("Corolla", foundCar.getModel());
        verify(carRepository, times(1)).findById(1);
    }

    @Test
    void testReadByIntegerNotFound() {
        when(carRepository.findById(999)).thenReturn(Optional.empty());

        Car foundCar = carService.read(Integer.valueOf(999));

        assertNull(foundCar);
        verify(carRepository, times(1)).findById(999);
    }

    @Test
    void testReadByIntFound() {
        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));

        Car foundCar = carService.read(1);

        assertNotNull(foundCar);
        assertEquals(1, foundCar.getCarID());
        assertEquals("Corolla", foundCar.getModel());
        verify(carRepository, times(1)).findById(1);
    }

    @Test
    void testReadByIntNotFound() {
        when(carRepository.findById(999)).thenReturn(Optional.empty());

        Car foundCar = carService.read(999);

        assertNull(foundCar);
        verify(carRepository, times(1)).findById(999);
    }

    @Test
    void testReadWithCarType() {
        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));

        Car foundCar = carService.read(1);

        assertNotNull(foundCar);
        assertNotNull(foundCar.getCarType());
        assertEquals("Sedan", foundCar.getCarType().getType());
        verify(carRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateExistingCar() {
        when(carRepository.existsById(1)).thenReturn(true);
        when(carRepository.save(any(Car.class))).thenReturn(testCar);

        Car updatedCar = carService.update(testCar);

        assertNotNull(updatedCar);
        assertEquals(testCar.getCarID(), updatedCar.getCarID());
        verify(carRepository, times(1)).existsById(1);
        verify(carRepository, times(1)).save(testCar);
    }

    @Test
    void testUpdateNonExistingCar() {
        when(carRepository.existsById(999)).thenReturn(false);

        testCar.setCarID(999);
        Car updatedCar = carService.update(testCar);

        assertNull(updatedCar);
        verify(carRepository, times(1)).existsById(999);
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    void testDelete() {
        doNothing().when(carRepository).deleteById(1);

        carService.delete(1);

        verify(carRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetCars() {
        List<Car> carList = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(carList);

        Set<Car> cars = carService.getCars();

        assertNotNull(cars);
        assertEquals(2, cars.size());
        assertTrue(cars.contains(testCar));
        assertTrue(cars.contains(testCar2));
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsWithCarTypes() {
        List<Car> carList = Arrays.asList(testCar);
        when(carRepository.findAll()).thenReturn(carList);

        Set<Car> cars = carService.getCars();

        assertNotNull(cars);
        assertEquals(1, cars.size());
        Car car = cars.iterator().next();
        assertNotNull(car.getCarType());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByBrand() {
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> toyotaCars = carService.getCarsByBrand("Toyota");

        assertNotNull(toyotaCars);
        assertEquals(1, toyotaCars.size());
        assertEquals("Toyota", toyotaCars.get(0).getBrand());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByBrandCaseInsensitive() {
        List<Car> allCars = Arrays.asList(testCar);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> toyotaCars = carService.getCarsByBrand("TOYOTA");

        assertNotNull(toyotaCars);
        assertEquals(1, toyotaCars.size());
        assertEquals("Toyota", toyotaCars.get(0).getBrand());
    }

    @Test
    void testGetCarsByBrandNotFound() {
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> fordCars = carService.getCarsByBrand("Ford");

        assertNotNull(fordCars);
        assertTrue(fordCars.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetAvailableCars() {
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> availableCars = carService.getAvailableCars();

        assertNotNull(availableCars);
        assertEquals(1, availableCars.size());
        assertTrue(availableCars.get(0).isAvailability());
        assertEquals("Corolla", availableCars.get(0).getModel());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetAvailableCarsNoneAvailable() {
        testCar.setAvailability(false);
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> availableCars = carService.getAvailableCars();

        assertNotNull(availableCars);
        assertTrue(availableCars.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testUpdateAvailabilitySuccess() {
        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Car updatedCar = carService.updateAvailability(1, false);

        assertNotNull(updatedCar);
        assertFalse(updatedCar.isAvailability());
        verify(carRepository, times(1)).findById(1);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testUpdateAvailabilityCarNotFound() {
        when(carRepository.findById(999)).thenReturn(Optional.empty());

        Car updatedCar = carService.updateAvailability(999, false);

        assertNull(updatedCar);
        verify(carRepository, times(1)).findById(999);
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    void testGetCarsByPriceRange() {
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> carsInRange = carService.getCarsByPriceRange(450.0, 520.0);

        assertNotNull(carsInRange);
        assertEquals(1, carsInRange.size());
        assertEquals(500.0, carsInRange.get(0).getRentalPrice());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByPriceRangeInclusive() {
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> carsInRange = carService.getCarsByPriceRange(500.0, 550.0);

        assertNotNull(carsInRange);
        assertEquals(2, carsInRange.size());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByPriceRangeNoneFound() {
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> carsInRange = carService.getCarsByPriceRange(600.0, 700.0);

        assertNotNull(carsInRange);
        assertTrue(carsInRange.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByYear() {
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> cars2022 = carService.getCarsByYear(2022);

        assertNotNull(cars2022);
        assertEquals(1, cars2022.size());
        assertEquals(2022, cars2022.get(0).getYear());
        assertEquals("Corolla", cars2022.get(0).getModel());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByYearMultipleResults() {
        testCar2.setYear(2022);
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> cars2022 = carService.getCarsByYear(2022);

        assertNotNull(cars2022);
        assertEquals(2, cars2022.size());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsByYearNoneFound() {
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> cars2020 = carService.getCarsByYear(2020);

        assertNotNull(cars2020);
        assertTrue(cars2020.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarsEmptyDatabase() {
        when(carRepository.findAll()).thenReturn(new ArrayList<>());

        Set<Car> cars = carService.getCars();

        assertNotNull(cars);
        assertTrue(cars.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testReadWithNullCarType() {
        testCar.setCarType(null);
        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));

        Car foundCar = carService.read(1);

        assertNotNull(foundCar);
        assertNull(foundCar.getCarType());
        verify(carRepository, times(1)).findById(1);
    }

    @Test
    void testCreateCarWithAllFields() {
        Car fullCar = new Car.Builder()
                .setCarID(3)
                .setModel("Altima")
                .setBrand("Nissan")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(600.0)
                .setImageData("altima-image-data".getBytes())
                .setImageName("altima.jpg")
                .setImageType("image/jpeg")
                .setCarType(testCarType)
                .build();

        when(carRepository.save(fullCar)).thenReturn(fullCar);

        Car createdCar = carService.create(fullCar);

        assertNotNull(createdCar);
        assertEquals("Altima", createdCar.getModel());
        assertEquals("altima.jpg", createdCar.getImageName());
        assertEquals("image/jpeg", createdCar.getImageType());
        assertNotNull(createdCar.getImageData());
        assertEquals(testCarType, createdCar.getCarType());
        verify(carRepository, times(1)).save(fullCar);
    }

    @Test
    void testUpdateCarFields() {
        Car originalCar = new Car.Builder()
                .copy(testCar)
                .build();

        Car updatedData = new Car.Builder()
                .copy(originalCar)
                .setModel("Updated Model")
                .setRentalPrice(750.0)
                .setAvailability(false)
                .build();

        when(carRepository.existsById(1)).thenReturn(true);
        when(carRepository.save(updatedData)).thenReturn(updatedData);

        Car updatedCar = carService.update(updatedData);

        assertNotNull(updatedCar);
        assertEquals("Updated Model", updatedCar.getModel());
        assertEquals(750.0, updatedCar.getRentalPrice());
        assertFalse(updatedCar.isAvailability());
        verify(carRepository, times(1)).save(updatedData);
    }
}