package za.co.carhire.factory.vehicle;

// Imtiyaaz Waggie 219374759//
//date:10/05/2025 //

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;

import static org.junit.jupiter.api.Assertions.*;

class CarTypeFactoryTest {

    @Test
    void createCarType() {
        // Test creating a car type
        CarType carType = CarTypeFactory.createCarType(1, "Sedan", "Petrol", 4, 5);

        // Verify the car type was created properly
        assertNotNull(carType);
        assertEquals(1, carType.getCarTypeID());
        assertEquals("Sedan", carType.getType());
        assertEquals("Petrol", carType.getFuelType());
        assertEquals(4, carType.getNumberOfWheels());
        assertEquals(5, carType.getNumberOfSeats());
    }

    @Test
    void createSedan() {
        // Test creating a sedan
        CarType sedan = CarTypeFactory.createSedan(2);

        // Verify the sedan was created properly
        assertNotNull(sedan);
        assertEquals(2, sedan.getCarTypeID());
        assertEquals("Sedan", sedan.getType());
        assertEquals("Petrol", sedan.getFuelType());
        assertEquals(4, sedan.getNumberOfWheels());
        assertEquals(5, sedan.getNumberOfSeats());
    }

    @Test
    void createSUV() {
        // Test creating an SUV
        CarType suv = CarTypeFactory.createSUV(3);

        // Verify the SUV was created properly
        assertNotNull(suv);
        assertEquals(3, suv.getCarTypeID());
        assertEquals("SUV", suv.getType());
        assertEquals("Petrol", suv.getFuelType());
        assertEquals(4, suv.getNumberOfWheels());
        assertEquals(7, suv.getNumberOfSeats()); // SUVs typically have more seats
    }

    @Test
    void createLuxury() {
        // Test creating a luxury car type
        CarType luxury = CarTypeFactory.createLuxury(4);

        // Verify the luxury type was created properly
        assertNotNull(luxury);
        assertEquals(4, luxury.getCarTypeID());
        assertEquals("Luxury", luxury.getType());
        assertEquals("Petrol", luxury.getFuelType());
        assertEquals(4, luxury.getNumberOfWheels());
        assertEquals(5, luxury.getNumberOfSeats());
    }

    @Test
    void createEconomy() {
        // Test creating an economy car type
        CarType economy = CarTypeFactory.createEconomy(5);

        // Verify the economy type was created properly
        assertNotNull(economy);
        assertEquals(5, economy.getCarTypeID());
        assertEquals("Economy", economy.getType());
        assertEquals("Petrol", economy.getFuelType());
        assertEquals(4, economy.getNumberOfWheels());
        assertEquals(5, economy.getNumberOfSeats());
    }

    @Test
    void createSports() {
        // Test creating a sports car type
        CarType sports = CarTypeFactory.createSports(6);

        // Verify the sports type was created properly
        assertNotNull(sports);
        assertEquals(6, sports.getCarTypeID());
        assertEquals("Sports", sports.getType());
        assertEquals("Petrol", sports.getFuelType());
        assertEquals(4, sports.getNumberOfWheels());
        assertEquals(2, sports.getNumberOfSeats()); // Sports cars typically have 2 seats
    }

    @Test
    void createConvertible() {
        // Test creating a convertible car type
        CarType convertible = CarTypeFactory.createConvertible(7);

        // Verify the convertible type was created properly
        assertNotNull(convertible);
        assertEquals(7, convertible.getCarTypeID());
        assertEquals("Convertible", convertible.getType());
        assertEquals("Petrol", convertible.getFuelType());
        assertEquals(4, convertible.getNumberOfWheels());
        assertEquals(2, convertible.getNumberOfSeats()); // Convertibles typically have 2 seats
    }

    @Test
    void createMinivan() {
        // Test creating a minivan car type
        CarType minivan = CarTypeFactory.createMinivan(8);

        // Verify the minivan type was created properly
        assertNotNull(minivan);
        assertEquals(8, minivan.getCarTypeID());
        assertEquals("Minivan", minivan.getType());
        assertEquals("Petrol", minivan.getFuelType());
        assertEquals(4, minivan.getNumberOfWheels());
        assertEquals(8, minivan.getNumberOfSeats()); // Minivans have more seats
    }

    @Test
    void createElectric() {
        // Test creating an electric car type
        CarType electric = CarTypeFactory.createElectric(9);

        // Verify the electric type was created properly
        assertNotNull(electric);
        assertEquals(9, electric.getCarTypeID());
        assertEquals("Electric", electric.getType());
        assertEquals("Electric", electric.getFuelType());
        assertEquals(4, electric.getNumberOfWheels());
        assertEquals(5, electric.getNumberOfSeats());
    }

    @Test
    void createHybrid() {
        // Test creating a hybrid car type
        CarType hybrid = CarTypeFactory.createHybrid(10);

        // Verify the hybrid type was created properly
        assertNotNull(hybrid);
        assertEquals(10, hybrid.getCarTypeID());
        assertEquals("Hybrid", hybrid.getType());
        assertEquals("Hybrid", hybrid.getFuelType());
        assertEquals(4, hybrid.getNumberOfWheels());
        assertEquals(5, hybrid.getNumberOfSeats());
    }

    @Test
    void createMotorcycle() {
        // Test creating a motorcycle type
        CarType motorcycle = CarTypeFactory.createMotorcycle(11);

        // Verify the motorcycle type was created properly
        assertNotNull(motorcycle);
        assertEquals(11, motorcycle.getCarTypeID());
        assertEquals("Motorcycle", motorcycle.getType());
        assertEquals("Petrol", motorcycle.getFuelType());
        assertEquals(2, motorcycle.getNumberOfWheels());
        assertEquals(2, motorcycle.getNumberOfSeats());
    }

    @Test
    void copy() {
        // Create original car type
        CarType original = CarTypeFactory.createSUV(12);

        // Create copy with new ID
        CarType copied = CarTypeFactory.copy(original, 13);

        // Verify the copy was created properly
        assertNotNull(copied);
        assertEquals(13, copied.getCarTypeID()); // New ID
        assertEquals("SUV", copied.getType());
        assertEquals("Petrol", copied.getFuelType());
        assertEquals(4, copied.getNumberOfWheels());
        assertEquals(7, copied.getNumberOfSeats());

        // Test null error handling
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CarTypeFactory.copy(null, 14);
        });
        assertTrue(exception.getMessage().contains("null"));
    }
}