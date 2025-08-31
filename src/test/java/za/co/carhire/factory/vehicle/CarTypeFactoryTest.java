package za.co.carhire.factory.vehicle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;

import static org.junit.jupiter.api.Assertions.*;

public class CarTypeFactoryTest {

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
    }

    @Test
    void testCreateCarType() {
        CarType carType = CarTypeFactory.createCarType(
                1, "SUV", "Petrol", 4, 7
        );

        assertNotNull(carType);
        assertEquals(1, carType.getCarTypeID());
        assertEquals("SUV", carType.getType());
        assertEquals("Petrol", carType.getFuelType());
        assertEquals(4, carType.getNumberOfWheels());
        assertEquals(7, carType.getNumberOfSeats());
        assertNull(carType.getCar()); 
    }

    @Test
    void testCreateCarTypeWithDifferentValues() {
        CarType carType = CarTypeFactory.createCarType(
                99, "Truck", "Diesel", 6, 3
        );

        assertNotNull(carType);
        assertEquals(99, carType.getCarTypeID());
        assertEquals("Truck", carType.getType());
        assertEquals("Diesel", carType.getFuelType());
        assertEquals(6, carType.getNumberOfWheels());
        assertEquals(3, carType.getNumberOfSeats());
    }

    @Test
    void testCreateSedan() {
        CarType sedan = CarTypeFactory.createSedan(10);

        assertNotNull(sedan);
        assertEquals(10, sedan.getCarTypeID());
        assertEquals("Sedan", sedan.getType());
        assertEquals("Petrol", sedan.getFuelType());
        assertEquals(4, sedan.getNumberOfWheels());
        assertEquals(5, sedan.getNumberOfSeats());
    }

    @Test
    void testCreateSUV() {
        CarType suv = CarTypeFactory.createSUV(20);

        assertNotNull(suv);
        assertEquals(20, suv.getCarTypeID());
        assertEquals("SUV", suv.getType());
        assertEquals("Petrol", suv.getFuelType());
        assertEquals(4, suv.getNumberOfWheels());
        assertEquals(7, suv.getNumberOfSeats());
    }

    @Test
    void testCreateLuxury() {
        CarType luxury = CarTypeFactory.createLuxury(30);

        assertNotNull(luxury);
        assertEquals(30, luxury.getCarTypeID());
        assertEquals("Luxury", luxury.getType());
        assertEquals("Petrol", luxury.getFuelType());
        assertEquals(4, luxury.getNumberOfWheels());
        assertEquals(5, luxury.getNumberOfSeats());
    }

    @Test
    void testCreateEconomy() {
        CarType economy = CarTypeFactory.createEconomy(40);

        assertNotNull(economy);
        assertEquals(40, economy.getCarTypeID());
        assertEquals("Economy", economy.getType());
        assertEquals("Petrol", economy.getFuelType());
        assertEquals(4, economy.getNumberOfWheels());
        assertEquals(5, economy.getNumberOfSeats());
    }

    @Test
    void testCreateSports() {
        CarType sports = CarTypeFactory.createSports(50);

        assertNotNull(sports);
        assertEquals(50, sports.getCarTypeID());
        assertEquals("Sports", sports.getType());
        assertEquals("Petrol", sports.getFuelType());
        assertEquals(4, sports.getNumberOfWheels());
        assertEquals(2, sports.getNumberOfSeats()); 
    }

    @Test
    void testCreateConvertible() {
        CarType convertible = CarTypeFactory.createConvertible(60);

        assertNotNull(convertible);
        assertEquals(60, convertible.getCarTypeID());
        assertEquals("Convertible", convertible.getType());
        assertEquals("Petrol", convertible.getFuelType());
        assertEquals(4, convertible.getNumberOfWheels());
        assertEquals(2, convertible.getNumberOfSeats()); 
    }

    @Test
    void testCreateMinivan() {
        CarType minivan = CarTypeFactory.createMinivan(70);

        assertNotNull(minivan);
        assertEquals(70, minivan.getCarTypeID());
        assertEquals("Minivan", minivan.getType());
        assertEquals("Petrol", minivan.getFuelType());
        assertEquals(4, minivan.getNumberOfWheels());
        assertEquals(8, minivan.getNumberOfSeats()); 
    }

    @Test
    void testCreateElectric() {
        CarType electric = CarTypeFactory.createElectric(80);

        assertNotNull(electric);
        assertEquals(80, electric.getCarTypeID());
        assertEquals("Electric", electric.getType());
        assertEquals("Electric", electric.getFuelType()); 
        assertEquals(4, electric.getNumberOfWheels());
        assertEquals(5, electric.getNumberOfSeats());
    }

    @Test
    void testCreateHybrid() {
        CarType hybrid = CarTypeFactory.createHybrid(90);

        assertNotNull(hybrid);
        assertEquals(90, hybrid.getCarTypeID());
        assertEquals("Hybrid", hybrid.getType());
        assertEquals("Hybrid", hybrid.getFuelType()); 
        assertEquals(4, hybrid.getNumberOfWheels());
        assertEquals(5, hybrid.getNumberOfSeats());
    }

    @Test
    void testCreateMotorcycle() {
        CarType motorcycle = CarTypeFactory.createMotorcycle(100);

        assertNotNull(motorcycle);
        assertEquals(100, motorcycle.getCarTypeID());
        assertEquals("Motorcycle", motorcycle.getType());
        assertEquals("Petrol", motorcycle.getFuelType());
        assertEquals(2, motorcycle.getNumberOfWheels()); 
        assertEquals(2, motorcycle.getNumberOfSeats());
    }

    @Test
    void testCopyCarType() {
        CarType original = new CarType.Builder()
                .setCarTypeID(1)
                .setCar(testCar)
                .setType("Original")
                .setFuelType("Diesel")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        CarType copied = CarTypeFactory.copy(original, 200);

        assertNotNull(copied);
        assertEquals(200, copied.getCarTypeID()); 

        assertEquals(original.getCar(), copied.getCar());
        assertEquals(original.getType(), copied.getType());
        assertEquals(original.getFuelType(), copied.getFuelType());
        assertEquals(original.getNumberOfWheels(), copied.getNumberOfWheels());
        assertEquals(original.getNumberOfSeats(), copied.getNumberOfSeats());
    }

    @Test
    void testCopyCarTypeWithoutCar() {
        CarType original = CarTypeFactory.createSUV(10);
        CarType copied = CarTypeFactory.copy(original, 300);

        assertNotNull(copied);
        assertEquals(300, copied.getCarTypeID());
        assertEquals("SUV", copied.getType());
        assertEquals("Petrol", copied.getFuelType());
        assertEquals(4, copied.getNumberOfWheels());
        assertEquals(7, copied.getNumberOfSeats());
        assertNull(copied.getCar());
    }

    @Test
    void testCopyCarTypeNullOriginal() {
        assertThrows(IllegalArgumentException.class, () -> {
            CarTypeFactory.copy(null, 400);
        });
    }

    @Test
    void testCreateCarTypeWithNullValues() {
        CarType carType = CarTypeFactory.createCarType(
                500, null, null, 4, 5
        );

        assertNotNull(carType);
        assertNull(carType.getType());
        assertNull(carType.getFuelType());
        assertEquals(4, carType.getNumberOfWheels());
        assertEquals(5, carType.getNumberOfSeats());
    }

    @Test
    void testCreateCarTypeWithEmptyStrings() {
        CarType carType = CarTypeFactory.createCarType(
                600, "", "", 4, 5
        );

        assertNotNull(carType);
        assertEquals("", carType.getType());
        assertEquals("", carType.getFuelType());
    }

    @Test
    void testCreateCarTypeWithZeroWheels() {
        CarType carType = CarTypeFactory.createCarType(
                700, "Special", "Electric", 0, 1
        );

        assertNotNull(carType);
        assertEquals(0, carType.getNumberOfWheels());
        assertEquals(1, carType.getNumberOfSeats());
    }

    @Test
    void testCreateCarTypeWithNegativeValues() {
        CarType carType = CarTypeFactory.createCarType(
                800, "Test", "Test", -1, -1
        );

        assertNotNull(carType);
        assertEquals(-1, carType.getNumberOfWheels());
        assertEquals(-1, carType.getNumberOfSeats());
    }

    @Test
    void testAllPresetTypesHaveDifferentCharacteristics() {
        CarType sedan = CarTypeFactory.createSedan(1);
        CarType suv = CarTypeFactory.createSUV(2);
        CarType sports = CarTypeFactory.createSports(3);
        CarType motorcycle = CarTypeFactory.createMotorcycle(4);

        // Check different characteristics
        assertNotEquals(sedan.getNumberOfSeats(), suv.getNumberOfSeats());
        assertNotEquals(sports.getNumberOfSeats(), sedan.getNumberOfSeats());
        assertNotEquals(motorcycle.getNumberOfWheels(), sedan.getNumberOfWheels());
    }

    @Test
    void testFactoryMethodsProduceDifferentInstances() {
        CarType type1 = CarTypeFactory.createSedan(1);
        CarType type2 = CarTypeFactory.createSedan(1);

        assertNotSame(type1, type2); 
        assertEquals(type1.getType(), type2.getType()); 
    }

    @Test
    void testAllFuelTypes() {
        CarType petrol = CarTypeFactory.createSedan(1);
        CarType electric = CarTypeFactory.createElectric(2);
        CarType hybrid = CarTypeFactory.createHybrid(3);

        assertEquals("Petrol", petrol.getFuelType());
        assertEquals("Electric", electric.getFuelType());
        assertEquals("Hybrid", hybrid.getFuelType());
    }
}