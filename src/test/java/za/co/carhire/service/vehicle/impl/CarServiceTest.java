package za.co.carhire.service.vehicle.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarCondition;
import za.co.carhire.domain.vehicle.CarStatus;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.domain.vehicle.VehicleCategory;
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
                .setCategory(za.co.carhire.domain.vehicle.VehicleCategory.SEDAN)
                .setFuelType(za.co.carhire.domain.vehicle.FuelType.PETROL)
                .setTransmissionType(za.co.carhire.domain.vehicle.TransmissionType.AUTOMATIC)
                .setNumberOfSeats(5)
                .setNumberOfDoors(4)
                .setAirConditioned(true)
                .setLuggageCapacity(3)
                .setDescription("Test sedan for unit tests")
                .build();

        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2022)
                .setLicensePlate("ABC 123 GP")
                .setVin("TOY22123456789012")
                .setColor("White")
                .setMileage(45000)
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(CarCondition.GOOD)
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
                .setLicensePlate("DEF 456 GP")
                .setVin("HON23234567890123")
                .setColor("Silver")
                .setMileage(30000)
                .setStatus(CarStatus.RENTED)
                .setCondition(CarCondition.GOOD)
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
        assertEquals(VehicleCategory.SEDAN, foundCar.getCarType().getCategory());
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
        assertEquals(CarStatus.AVAILABLE, availableCars.get(0).getStatus());
        assertEquals("Corolla", availableCars.get(0).getModel());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetAvailableCarsNoneAvailable() {
        testCar.setStatus(CarStatus.RENTED);
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> availableCars = carService.getAvailableCars();

        assertNotNull(availableCars);
        assertTrue(availableCars.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testUpdateStatusSuccess() {
        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Car updatedCar = carService.updateStatus(1, CarStatus.MAINTENANCE);

        assertNotNull(updatedCar);
        assertEquals(CarStatus.MAINTENANCE, updatedCar.getStatus());
        verify(carRepository, times(1)).findById(1);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testUpdateStatusCarNotFound() {
        when(carRepository.findById(999)).thenReturn(Optional.empty());

        Car updatedCar = carService.updateStatus(999, CarStatus.MAINTENANCE);

        assertNull(updatedCar);
        verify(carRepository, times(1)).findById(999);
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    void testGetCarsByStatus() {
        List<Car> allCars = Arrays.asList(testCar, testCar2);
        when(carRepository.findAll()).thenReturn(allCars);

        List<Car> availableCars = carService.getCarsByStatus(CarStatus.AVAILABLE);

        assertNotNull(availableCars);
        assertEquals(1, availableCars.size());
        assertEquals(CarStatus.AVAILABLE, availableCars.get(0).getStatus());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testUpdateMileageSuccess() {
        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Car updatedCar = carService.updateMileage(1, 50000);

        assertNotNull(updatedCar);
        assertEquals(50000, updatedCar.getMileage());
        verify(carRepository, times(1)).findById(1);
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testUpdateMileageCarNotFound() {
        when(carRepository.findById(999)).thenReturn(Optional.empty());

        Car updatedCar = carService.updateMileage(999, 50000);

        assertNull(updatedCar);
        verify(carRepository, times(1)).findById(999);
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    void testGetCarByLicensePlate() {
        when(carRepository.findByLicensePlate("ABC 123 GP")).thenReturn(Optional.of(testCar));

        Car foundCar = carService.getCarByLicensePlate("ABC 123 GP");

        assertNotNull(foundCar);
        assertEquals("ABC 123 GP", foundCar.getLicensePlate());
        verify(carRepository, times(1)).findByLicensePlate("ABC 123 GP");
    }

    @Test
    void testGetCarByLicensePlateNotFound() {
        when(carRepository.findByLicensePlate("XYZ 999 GP")).thenReturn(Optional.empty());

        Car foundCar = carService.getCarByLicensePlate("XYZ 999 GP");

        assertNull(foundCar);
        verify(carRepository, times(1)).findByLicensePlate("XYZ 999 GP");
    }

    @Test
    void testGetCarByVin() {
        when(carRepository.findByVin("TOY22123456789012")).thenReturn(Optional.of(testCar));

        Car foundCar = carService.getCarByVin("TOY22123456789012");

        assertNotNull(foundCar);
        assertEquals("TOY22123456789012", foundCar.getVin());
        verify(carRepository, times(1)).findByVin("TOY22123456789012");
    }

    @Test
    void testGetCarByVinNotFound() {
        when(carRepository.findByVin("INVALID123456789")).thenReturn(Optional.empty());

        Car foundCar = carService.getCarByVin("INVALID123456789");

        assertNull(foundCar);
        verify(carRepository, times(1)).findByVin("INVALID123456789");
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
                .setLicensePlate("GHI 789 GP")
                .setVin("NIS24345678901234")
                .setColor("Blue")
                .setMileage(15000)
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(CarCondition.EXCELLENT)
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
        assertEquals("GHI 789 GP", createdCar.getLicensePlate());
        assertEquals(15000, createdCar.getMileage());
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
                .setMileage(55000)
                .setStatus(CarStatus.MAINTENANCE)
                .build();

        when(carRepository.existsById(1)).thenReturn(true);
        when(carRepository.save(updatedData)).thenReturn(updatedData);

        Car updatedCar = carService.update(updatedData);

        assertNotNull(updatedCar);
        assertEquals("Updated Model", updatedCar.getModel());
        assertEquals(55000, updatedCar.getMileage());
        assertEquals(CarStatus.MAINTENANCE, updatedCar.getStatus());
        verify(carRepository, times(1)).save(updatedData);
    }
}