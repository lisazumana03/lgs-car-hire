package za.co.carhire.factory.vehicle;

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;

import static org.junit.jupiter.api.Assertions.*;

public class CarTypeFactoryTest {

    @Test
    void testCreateCarType_Success() {
        CarType carType = CarTypeFactory.createCarType(1, "Sedan", "Petrol", 4, 5);

        assertNotNull(carType);
        assertEquals(1, carType.getCarTypeID());
        assertEquals("Sedan", carType.getType());
        assertEquals("Petrol", carType.getFuelType());
        assertEquals(4, carType.getNumberOfWheels());
        assertEquals(5, carType.getNumberOfSeats());
    }

    @Test
    void testCreateSedan() {
        CarType sedan = CarTypeFactory.createSedan(1);

        assertNotNull(sedan);
        assertEquals(1, sedan.getCarTypeID());
        assertEquals("Sedan", sedan.getType());
        assertEquals("Petrol", sedan.getFuelType());
        assertEquals(4, sedan.getNumberOfWheels());
        assertEquals(5, sedan.getNumberOfSeats());
    }

    @Test
    void testCreateSUV() {
        CarType suv = CarTypeFactory.createSUV(2);

        assertNotNull(suv);
        assertEquals(2, suv.getCarTypeID());
        assertEquals("SUV", suv.getType());
        assertEquals("Petrol", suv.getFuelType());
        assertEquals(4, suv.getNumberOfWheels());
        assertEquals(7, suv.getNumberOfSeats());
    }

    @Test
    void testCreateLuxury() {
        CarType luxury = CarTypeFactory.createLuxury(3);

        assertNotNull(luxury);
        assertEquals(3, luxury.getCarTypeID());
        assertEquals("Luxury", luxury.getType());
        assertEquals("Petrol", luxury.getFuelType());
        assertEquals(4, luxury.getNumberOfWheels());
        assertEquals(5, luxury.getNumberOfSeats());
    }

    @Test
    void testCreateEconomy() {
        CarType economy = CarTypeFactory.createEconomy(4);

        assertNotNull(economy);
        assertEquals(4, economy.getCarTypeID());
        assertEquals("Economy", economy.getType());
        assertEquals("Petrol", economy.getFuelType());
        assertEquals(4, economy.getNumberOfWheels());
        assertEquals(5, economy.getNumberOfSeats());
    }

    @Test
    void testCreateSports() {
        CarType sports = CarTypeFactory.createSports(5);

        assertNotNull(sports);
        assertEquals(5, sports.getCarTypeID());
        assertEquals("Sports", sports.getType());
        assertEquals("Petrol", sports.getFuelType());
        assertEquals(4, sports.getNumberOfWheels());
        assertEquals(2, sports.getNumberOfSeats());
    }

    @Test
    void testCreateConvertible() {
        CarType convertible = CarTypeFactory.createConvertible(6);

        assertNotNull(convertible);
        assertEquals(6, convertible.getCarTypeID());
        assertEquals("Convertible", convertible.getType());
        assertEquals("Petrol", convertible.getFuelType());
        assertEquals(4, convertible.getNumberOfWheels());
        assertEquals(2, convertible.getNumberOfSeats());
    }

    @Test
    void testCreateMinivan() {
        CarType minivan = CarTypeFactory.createMinivan(7);

        assertNotNull(minivan);
        assertEquals(7, minivan.getCarTypeID());
        assertEquals("Minivan", minivan.getType());
        assertEquals("Petrol", minivan.getFuelType());
        assertEquals(4, minivan.getNumberOfWheels());
        assertEquals(8, minivan.getNumberOfSeats());
    }

    @Test
    void testCreateElectric() {
        CarType electric = CarTypeFactory.createElectric(8);

        assertNotNull(electric);
        assertEquals(8, electric.getCarTypeID());
        assertEquals("Electric", electric.getType());
        assertEquals("Electric", electric.getFuelType());
        assertEquals(4, electric.getNumberOfWheels());
        assertEquals(5, electric.getNumberOfSeats());
    }

    @Test
    void testCreateHybrid() {
        CarType hybrid = CarTypeFactory.createHybrid(9);

        assertNotNull(hybrid);
        assertEquals(9, hybrid.getCarTypeID());
        assertEquals("Hybrid", hybrid.getType());
        assertEquals("Hybrid", hybrid.getFuelType());
        assertEquals(4, hybrid.getNumberOfWheels());
        assertEquals(5, hybrid.getNumberOfSeats());
    }

    @Test
    void testCreateMotorcycle() {
        CarType motorcycle = CarTypeFactory.createMotorcycle(10);

        assertNotNull(motorcycle);
        assertEquals(10, motorcycle.getCarTypeID());
        assertEquals("Motorcycle", motorcycle.getType());
        assertEquals("Petrol", motorcycle.getFuelType());
        assertEquals(2, motorcycle.getNumberOfWheels());
        assertEquals(2, motorcycle.getNumberOfSeats());
    }

    @Test
    void testCopy_Success() {
        Car testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2022)
                .setAvailability(true)
                .setRentalPrice(500.0)
                .build();

        CarType original = new CarType.Builder()
                .setCarTypeID(1)
                .setType("Sedan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .setCar(testCar)
                .build();

        CarType copy = CarTypeFactory.copy(original, 99);

        assertNotNull(copy);
        assertEquals(99, copy.getCarTypeID());
        assertEquals(original.getType(), copy.getType());
        assertEquals(original.getFuelType(), copy.getFuelType());
        assertEquals(original.getNumberOfWheels(), copy.getNumberOfWheels());
        assertEquals(original.getNumberOfSeats(), copy.getNumberOfSeats());
        assertEquals(original.getCar(), copy.getCar());

        // Verify they are different instances
        assertNotSame(original, copy);
    }

    @Test
    void testCopy_NullOriginal() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> CarTypeFactory.copy(null, 99)
        );

        assertEquals("Original CarType cannot be null", exception.getMessage());
    }

    @Test
    void testCopy_WithoutCar() {
        CarType original = new CarType.Builder()
                .setCarTypeID(1)
                .setType("SUV")
                .setFuelType("Diesel")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        CarType copy = CarTypeFactory.copy(original, 100);

        assertNotNull(copy);
        assertEquals(100, copy.getCarTypeID());
        assertEquals("SUV", copy.getType());
        assertEquals("Diesel", copy.getFuelType());
        assertEquals(4, copy.getNumberOfWheels());
        assertEquals(7, copy.getNumberOfSeats());
        assertNull(copy.getCar());
    }

    @Test
    void testCreateCarType_WithZeroID() {
        CarType carType = CarTypeFactory.createCarType(0, "Custom", "Diesel", 6, 10);

        assertNotNull(carType);
        assertEquals(0, carType.getCarTypeID());
        assertEquals("Custom", carType.getType());
        assertEquals("Diesel", carType.getFuelType());
        assertEquals(6, carType.getNumberOfWheels());
        assertEquals(10, carType.getNumberOfSeats());
    }

    @Test
    void testCreateCarType_WithNegativeID() {
        CarType carType = CarTypeFactory.createCarType(-1, "Test", "Petrol", 4, 5);

        assertNotNull(carType);
        assertEquals(-1, carType.getCarTypeID());
    }

    @Test
    void testMultipleInstances_AreDifferent() {
        CarType sedan1 = CarTypeFactory.createSedan(1);
        CarType sedan2 = CarTypeFactory.createSedan(2);

        assertNotSame(sedan1, sedan2);
        assertNotEquals(sedan1.getCarTypeID(), sedan2.getCarTypeID());
        assertEquals(sedan1.getType(), sedan2.getType());
        assertEquals(sedan1.getFuelType(), sedan2.getFuelType());
    }

    @Test
    void testBuilderCopy_ConsistentWithFactoryCopy() {
        CarType original = CarTypeFactory.createSedan(1);

        CarType factoryCopy = CarTypeFactory.copy(original, 2);

        CarType builderCopy = new CarType.Builder()
                .copy(original)
                .setCarTypeID(2)
                .build();

        assertEquals(factoryCopy.getCarTypeID(), builderCopy.getCarTypeID());
        assertEquals(factoryCopy.getType(), builderCopy.getType());
        assertEquals(factoryCopy.getFuelType(), builderCopy.getFuelType());
        assertEquals(factoryCopy.getNumberOfWheels(), builderCopy.getNumberOfWheels());
        assertEquals(factoryCopy.getNumberOfSeats(), builderCopy.getNumberOfSeats());
    }
}
