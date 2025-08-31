package za.co.carhire.factory.vehicle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;

import static org.junit.jupiter.api.Assertions.*;

class CarFactoryTest {
public class CarFactoryTest {

    private CarType testCarType;
    private Insurance testInsurance;
    private Booking testBooking;

    @BeforeEach
    void setUp() {
        testCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setType("SUV")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        testInsurance = new Insurance();

        testBooking = new Booking();
    }

    @Test
    void testCreateBasicCar() {
        Car car = CarFactory.createBasicCar(1, "Corolla", "Toyota", 2024, 850.00);

        assertNotNull(car);
        assertEquals(1, car.getCarID());
        assertEquals("Corolla", car.getModel());
        assertEquals("Toyota", car.getBrand());
        assertEquals(2024, car.getYear());
        assertEquals(850.00, car.getRentalPrice());
        assertTrue(car.isAvailability());
        assertNull(car.getCarType());
        assertNull(car.getInsurance());
        assertNull(car.getBooking());
    }

    @Test
    void testCreateBasicCarWithDifferentValues() {
        Car car = CarFactory.createBasicCar(99, "Model S", "Tesla", 2025, 2500.00);

        assertNotNull(car);
        assertEquals(99, car.getCarID());
        assertEquals("Model S", car.getModel());
        assertEquals("Tesla", car.getBrand());
        assertEquals(2025, car.getYear());
        assertEquals(2500.00, car.getRentalPrice());
        assertTrue(car.isAvailability());
    }
  
    @Test
    void testCreateCompleteCar() {
        Car car = CarFactory.createCompleteCar(
                2, "Fortuner", "Toyota", 2023,
                false, 1500.00,
                testCarType, testInsurance, testBooking
        );

        assertNotNull(car);
        assertEquals(2, car.getCarID());
        assertEquals("Fortuner", car.getModel());
        assertEquals("Toyota", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals(600.0, car.getRentalPrice());
        assertTrue(car.isAvailability());

        assertEquals(carType, car.getCarType());
        assertEquals(insurance, car.getInsurance());
        assertEquals(booking, car.getBooking());
        assertFalse(car.isAvailability());
        assertEquals(1500.00, car.getRentalPrice());
        assertNotNull(car.getCarType());
        assertEquals(testCarType, car.getCarType());
        assertNotNull(car.getInsurance());
        assertEquals(testInsurance, car.getInsurance());
        assertNotNull(car.getBooking());
        assertEquals(testBooking, car.getBooking());
    }

    @Test
    void testCreateCompleteCarWithNullRelationships() {
        Car car = CarFactory.createCompleteCar(
                3, "Civic", "Honda", 2024,
                true, 900.00,
                null, null, null
        );

        assertNotNull(car);
        assertEquals(3, car.getCarID());
        assertEquals("Civic", car.getModel());
        assertEquals("Honda", car.getBrand());
        assertTrue(car.isAvailability());
        assertNull(car.getCarType());
        assertNull(car.getInsurance());
        assertNull(car.getBooking());
    }

    @Test
    void createCarWithInsurance() {
        // Create dependencies
        CarType carType = CarTypeFactory.createSedan(4);
        Insurance insurance = new Insurance();

    @Test
    void testCreateCarWithType() {
        Car car = CarFactory.createCarWithType(
                4, "X5", "BMW", 2024,
                2000.00, testCarType
        );

        assertNotNull(car);
        assertEquals(4, car.getCarID());
        assertEquals("X5", car.getModel());
        assertEquals("BMW", car.getBrand());
        assertEquals(2024, car.getYear());
        assertEquals(2000.00, car.getRentalPrice());
        assertTrue(car.isAvailability()); 
        assertNotNull(car.getCarType());
        assertEquals(testCarType, car.getCarType());
        assertNull(car.getInsurance());
        assertNull(car.getBooking());
    }

    @Test
    void testCreateCarWithTypeNull() {
        Car car = CarFactory.createCarWithType(
                5, "A3", "Audi", 2023,
                1100.00, null
        );

        assertNotNull(car);
        assertEquals("A3", car.getModel());
        assertNull(car.getCarType());
        assertTrue(car.isAvailability());
    }

    @Test
    void testCreateCarWithInsurance() {
        Car car = CarFactory.createCarWithInsurance(
                6, "C-Class", "Mercedes", 2024,
                1800.00, testCarType, testInsurance
        );

        assertNotNull(car);
        assertEquals(6, car.getCarID());
        assertEquals("C-Class", car.getModel());
        assertEquals("Mercedes", car.getBrand());
        assertEquals(2024, car.getYear());
        assertEquals(1800.00, car.getRentalPrice());
        assertTrue(car.isAvailability()); 
        assertNotNull(car.getCarType());
        assertEquals(testCarType, car.getCarType());
        assertNotNull(car.getInsurance());
        assertEquals(testInsurance, car.getInsurance());
        assertNull(car.getBooking());
    }


    @Test
    void testCreateCarWithInsuranceNullInsurance() {
        Car car = CarFactory.createCarWithInsurance(
                7, "Golf", "Volkswagen", 2023,
                950.00, testCarType, null
        );

        assertNotNull(car);
        assertEquals("Golf", car.getModel());
        assertNotNull(car.getCarType());
        assertNull(car.getInsurance());
        assertTrue(car.isAvailability());
    }


    @Test
    void testCreateCarCopy() {
        Car originalCar = CarFactory.createCompleteCar(
                10, "Model 3", "Tesla", 2024,
                true, 1600.00,
                testCarType, testInsurance, testBooking
        );

        // Create copy with new ID
        Car copiedCar = CarFactory.createCarCopy(originalCar, 6);
        Car copiedCar = CarFactory.createCarCopy(originalCar, 20);

        assertNotNull(copiedCar);
        assertEquals(20, copiedCar.getCarID()); 
        assertEquals(originalCar.getModel(), copiedCar.getModel());
        assertEquals(originalCar.getBrand(), copiedCar.getBrand());
        assertEquals(originalCar.getYear(), copiedCar.getYear());
        assertEquals(originalCar.isAvailability(), copiedCar.isAvailability());
        assertEquals(originalCar.getRentalPrice(), copiedCar.getRentalPrice());
        assertEquals(originalCar.getCarType(), copiedCar.getCarType());
        assertEquals(originalCar.getInsurance(), copiedCar.getInsurance());
        assertEquals(originalCar.getBooking(), copiedCar.getBooking());
    }

    @Test
    void testCreateCarCopyWithBasicCar() {
        Car originalCar = CarFactory.createBasicCar(30, "Polo", "Volkswagen", 2022, 700.00);
        Car copiedCar = CarFactory.createCarCopy(originalCar, 40);

        assertNotNull(copiedCar);
        assertEquals(40, copiedCar.getCarID());
        assertEquals("Polo", copiedCar.getModel());
        assertEquals("Volkswagen", copiedCar.getBrand());
        assertEquals(2022, copiedCar.getYear());
        assertEquals(700.00, copiedCar.getRentalPrice());
        assertTrue(copiedCar.isAvailability());
        assertNull(copiedCar.getCarType());
        assertNull(copiedCar.getInsurance());
    }

    @Test
    void testCreateUnavailableCar() {
        Car car = CarFactory.createUnavailableCar(
                50, "Ranger", "Ford", 2024,
                1300.00, testCarType
        );

        assertNotNull(car);
        assertEquals(50, car.getCarID());
        assertEquals("Ranger", car.getModel());
        assertEquals("Ford", car.getBrand());
        assertEquals(2024, car.getYear());
        assertEquals(1300.00, car.getRentalPrice());
        assertFalse(car.isAvailability());
        assertNotNull(car.getCarType());
        assertEquals(testCarType, car.getCarType());
        assertNull(car.getInsurance());
        assertNull(car.getBooking());
    }

    @Test
    void testCreateUnavailableCarWithNullCarType() {
        Car car = CarFactory.createUnavailableCar(
                60, "Hilux", "Toyota", 2023,
                1400.00, null
        );

        assertNotNull(car);
        assertEquals("Hilux", car.getModel());
        assertFalse(car.isAvailability());
        assertNull(car.getCarType());
    }

    @Test
    void testCreateCarWithZeroPrice() {
        Car car = CarFactory.createBasicCar(70, "Test", "Brand", 2024, 0.00);

        assertNotNull(car);
        assertEquals(0.00, car.getRentalPrice());
    }

    @Test
    void testCreateCarWithNegativePrice() {
        Car car = CarFactory.createBasicCar(80, "Test", "Brand", 2024, -100.00);

        assertNotNull(car);
        assertEquals(-100.00, car.getRentalPrice());
    }

    @Test
    void testCreateCarWithEmptyStrings() {
        Car car = CarFactory.createBasicCar(90, "", "", 2024, 500.00);

        assertNotNull(car);
        assertEquals("", car.getModel());
        assertEquals("", car.getBrand());
    }

    @Test
    void testCreateCarWithNullStrings() {
        Car car = CarFactory.createBasicCar(100, null, null, 2024, 600.00);

        assertNotNull(car);
        assertNull(car.getModel());
        assertNull(car.getBrand());
    }

    @Test
    void testAllFactoryMethodsProduceDifferentInstances() {
        Car car1 = CarFactory.createBasicCar(1, "Model1", "Brand1", 2024, 100.00);
        Car car2 = CarFactory.createBasicCar(1, "Model1", "Brand1", 2024, 100.00);

        assertNotSame(car1, car2); 
        assertEquals(car1.getModel(), car2.getModel()); 
    }
}