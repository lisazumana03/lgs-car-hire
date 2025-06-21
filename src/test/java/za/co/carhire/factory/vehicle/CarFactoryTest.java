package za.co.carhire.factory.vehicle;

import org.junit.jupiter.api.Test;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.reservation.Booking;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;

import static org.junit.jupiter.api.Assertions.*;
class CarFactoryTest {

    @Test
    void createBasicCar() {
        // Test creating a basic car
        Car car = CarFactory.createBasicCar(1, "Corolla", "Toyota", 2023, 250.0);

        // Verify the car was created properly
        assertNotNull(car);
        assertEquals(1, car.getCarID());
        assertEquals("Corolla", car.getModel());
        assertEquals("Toyota", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals(250.0, car.getRentalPrice());
        assertTrue(car.isAvailability());
    }

    @Test
    void createCompleteCar() {

        CarType carType = CarTypeFactory.createSUV(1);
        Insurance insurance = new Insurance();
        Booking booking = new Booking();
        Car car = CarFactory.createCompleteCar(2, "X5", "BMW", 2023,
                true, 600.0, carType, insurance, booking);


        assertNotNull(car);
        assertEquals(2, car.getCarID());
        assertEquals("X5", car.getModel());
        assertEquals("BMW", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals(600.0, car.getRentalPrice());
        assertTrue(car.isAvailability());


        assertEquals(carType, car.getCarType());
        assertEquals(insurance, car.getInsurance());
        assertEquals(booking, car.getBooking());
    }


    @Test
    void createCarWithType() {
        // Create car type
        CarType carType = CarTypeFactory.createSUV(3);

        // Create car with type
        Car car = CarFactory.createCarWithType(3, "X3", "BMW", 2023, 500.0, carType);

        // Verify the car was created properly
        assertNotNull(car);
        assertEquals(3, car.getCarID());
        assertEquals("X3", car.getModel());
        assertEquals("BMW", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals(500.0, car.getRentalPrice());
        assertTrue(car.isAvailability());

        // Verify relationship with car type
        assertEquals(carType, car.getCarType());
    }
    @Test
    void createCarWithInsurance() {
        // Create dependencies
        CarType carType = CarTypeFactory.createSedan(4);
        Insurance insurance = new Insurance();

        // Create car with insurance
        Car car = CarFactory.createCarWithInsurance(4, "Civic", "Honda", 2023,
                300.0, carType, insurance);

        // Verify the car was created properly
        assertNotNull(car);
        assertEquals(4, car.getCarID());
        assertEquals("Civic", car.getModel());
        assertEquals("Honda", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals(300.0, car.getRentalPrice());
        assertTrue(car.isAvailability());

        // Verify relationships
        assertEquals(carType, car.getCarType());
        assertEquals(insurance, car.getInsurance());
        assertNull(car.getBooking());
    }


    @Test
    void createCarCopy() {
        // Create original car
        Car originalCar = CarFactory.createBasicCar(5, "Camry", "Toyota", 2023, 350.0);



        // Create copy with new ID
        Car copiedCar = CarFactory.createCarCopy(originalCar, 6);

        // Verify the copy was created properly
        assertNotNull(copiedCar);
        assertEquals(6, copiedCar.getCarID()); // New ID
        assertEquals("Camry", copiedCar.getModel());
        assertEquals("Toyota", copiedCar.getBrand());
        assertEquals(2023, copiedCar.getYear());
        assertEquals(350.0, copiedCar.getRentalPrice());
        assertTrue(copiedCar.isAvailability());
    }

    @Test
    void createUnavailableCar() {
        // Create car type
        CarType carType = new CarTypeFactory().createSports(7);

        // Create unavailable car
        Car car = CarFactory.createUnavailableCar(7, "911", "Porsche", 2023, 1000.0, carType);

        // Verify the car was created properly
        assertNotNull(car);
        assertEquals(7, car.getCarID());
        assertEquals("911", car.getModel());
        assertEquals("Porsche", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals(1000.0, car.getRentalPrice());
        assertFalse(car.isAvailability()); // Should be unavailable

        // Verify relationship with car type
        assertEquals(carType, car.getCarType());
    }
}