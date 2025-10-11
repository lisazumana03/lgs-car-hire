package za.co.carhire.factory.vehicle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;

import static org.junit.jupiter.api.Assertions.*;

public class CarFactoryTest {

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
    }

    @Test
    void testCreateBasicCar() {
        Car car = CarFactory.createBasicCar(1, "Corolla", "Toyota", 2022, 500.0);

        assertNotNull(car);
        assertEquals(1, car.getCarID());
        assertEquals("Corolla", car.getModel());
        assertEquals("Toyota", car.getBrand());
        assertEquals(2022, car.getYear());
        assertEquals(500.0, car.getRentalPrice());
        assertTrue(car.isAvailability());
        assertNull(car.getImageData());
        assertNull(car.getCarType());
    }

    @Test
    void testCreateBasicCarWithImage() {
        byte[] imageData = "test-image-data".getBytes();
        String imageName = "car-image.jpg";
        String imageType = "image/jpeg";
        Car car = CarFactory.createBasicCarWithImage(2, "Civic", "Honda", 2023, 550.0, imageData, imageName, imageType);

        assertNotNull(car);
        assertEquals(2, car.getCarID());
        assertEquals("Civic", car.getModel());
        assertEquals("Honda", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals(550.0, car.getRentalPrice());
        assertNotNull(car.getImageData());
        assertEquals(imageName, car.getImageName());
        assertEquals(imageType, car.getImageType());
        assertTrue(car.isAvailability());
        assertNull(car.getCarType());
    }

    @Test
    void testCreateCompleteCar() {
        Car car = CarFactory.createCompleteCar(3, "Camry", "Toyota", 2021,
                false, 600.0, testCarType, null, null);

        assertNotNull(car);
        assertEquals(3, car.getCarID());
        assertEquals("Camry", car.getModel());
        assertEquals("Toyota", car.getBrand());
        assertEquals(2021, car.getYear());
        assertEquals(600.0, car.getRentalPrice());
        assertFalse(car.isAvailability());
        assertNull(car.getImageData());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateCompleteCarWithImage() {
        byte[] imageData = "complete-car-data".getBytes();
        String imageName = "complete-car.jpg";
        String imageType = "image/jpeg";
        Car car = CarFactory.createCompleteCarWithImage(4, "Accord", "Honda", 2022,
                true, 700.0, imageData, imageName, imageType, testCarType, null, null);

        assertNotNull(car);
        assertEquals(4, car.getCarID());
        assertEquals("Accord", car.getModel());
        assertEquals("Honda", car.getBrand());
        assertEquals(2022, car.getYear());
        assertEquals(700.0, car.getRentalPrice());
        assertTrue(car.isAvailability());
        assertNotNull(car.getImageData());
        assertEquals(imageName, car.getImageName());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateCarWithType() {
        Car car = CarFactory.createCarWithType(5, "3 Series", "BMW", 2023, 800.0, testCarType);

        assertNotNull(car);
        assertEquals(5, car.getCarID());
        assertEquals("3 Series", car.getModel());
        assertEquals("BMW", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals(800.0, car.getRentalPrice());
        assertTrue(car.isAvailability());
        assertNull(car.getImageData());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateCarWithTypeAndImage() {
        byte[] imageData = "bmw-image-data".getBytes();
        String imageName = "bmw.jpg";
        String imageType = "image/jpeg";
        Car car = CarFactory.createCarWithTypeAndImage(6, "X5", "BMW", 2023,
                1000.0, imageData, imageName, imageType, testCarType);

        assertNotNull(car);
        assertEquals(6, car.getCarID());
        assertEquals("X5", car.getModel());
        assertEquals("BMW", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals(1000.0, car.getRentalPrice());
        assertNotNull(car.getImageData());
        assertEquals(imageName, car.getImageName());
        assertTrue(car.isAvailability());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateCarCopy() {
        byte[] imageData = "tesla-image-data".getBytes();
        Car originalCar = CarFactory.createCompleteCarWithImage(9, "Model S", "Tesla", 2023,
                true, 1200.0, imageData, "tesla.jpg", "image/jpeg",
                testCarType, null, null);

        Car copiedCar = CarFactory.createCarCopy(originalCar, 10);

        assertNotNull(copiedCar);
        assertEquals(10, copiedCar.getCarID());
        assertEquals(originalCar.getModel(), copiedCar.getModel());
        assertEquals(originalCar.getBrand(), copiedCar.getBrand());
        assertEquals(originalCar.getYear(), copiedCar.getYear());
        assertEquals(originalCar.getRentalPrice(), copiedCar.getRentalPrice());
        assertEquals(originalCar.isAvailability(), copiedCar.isAvailability());
        assertEquals(originalCar.getImageName(), copiedCar.getImageName());
        assertEquals(originalCar.getCarType(), copiedCar.getCarType());
    }

    @Test
    void testCreateUnavailableCar() {
        Car car = CarFactory.createUnavailableCar(11, "Golf", "Volkswagen", 2021,
                450.0, testCarType);

        assertNotNull(car);
        assertEquals(11, car.getCarID());
        assertEquals("Golf", car.getModel());
        assertEquals("Volkswagen", car.getBrand());
        assertEquals(2021, car.getYear());
        assertEquals(450.0, car.getRentalPrice());
        assertFalse(car.isAvailability());
        assertNull(car.getImageData());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateUnavailableCarWithImage() {
        byte[] imageData = "vw-image-data".getBytes();
        String imageName = "vw.jpg";
        String imageType = "image/jpeg";
        Car car = CarFactory.createUnavailableCarWithImage(12, "Passat", "Volkswagen",
                2022, 550.0, imageData, imageName, imageType, testCarType);

        assertNotNull(car);
        assertEquals(12, car.getCarID());
        assertEquals("Passat", car.getModel());
        assertEquals("Volkswagen", car.getBrand());
        assertEquals(2022, car.getYear());
        assertEquals(550.0, car.getRentalPrice());
        assertFalse(car.isAvailability());
        assertNotNull(car.getImageData());
        assertEquals(imageName, car.getImageName());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateBasicCarWithSpecialCharacters() {
        Car car = CarFactory.createBasicCar(13, "CX-5", "Mazda", 2023, 599.99);

        assertNotNull(car);
        assertEquals("CX-5", car.getModel());
        assertEquals("Mazda", car.getBrand());
        assertEquals(599.99, car.getRentalPrice());
    }

    @Test
    void testCreateCarWithNullCarType() {
        Car car = CarFactory.createCarWithType(14, "Focus", "Ford", 2022, 400.0, null);

        assertNotNull(car);
        assertEquals(14, car.getCarID());
        assertEquals("Focus", car.getModel());
        assertNull(car.getCarType());
        assertTrue(car.isAvailability());
    }

    @Test
    void testCreateCompleteCarWithNullRelationships() {
        Car car = CarFactory.createCompleteCar(15, "Sentra", "Nissan", 2021,
                true, 350.0, testCarType, null, null);

        assertNotNull(car);
        assertEquals(15, car.getCarID());
        assertEquals("Sentra", car.getModel());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateMultipleCarsWithDifferentFactoryMethods() {
        Car basicCar = CarFactory.createBasicCar(16, "Yaris", "Toyota", 2023, 300.0);
        byte[] imageData = "fit-image-data".getBytes();
        Car carWithImage = CarFactory.createBasicCarWithImage(17, "Fit", "Honda", 2023, 320.0, imageData, "fit.jpg", "image/jpeg");
        Car carWithType = CarFactory.createCarWithType(18, "A3", "Audi", 2023, 650.0, testCarType);

        assertNotNull(basicCar);
        assertNotNull(carWithImage);
        assertNotNull(carWithType);

        assertNull(basicCar.getCarType());
        assertNull(basicCar.getImageData());

        assertNotNull(carWithImage.getImageData());
        assertNull(carWithImage.getCarType());

        assertNotNull(carWithType.getCarType());
        assertNull(carWithType.getImageData());
    }

    @Test
    void testAvailabilityDefaults() {
        Car basicCar = CarFactory.createBasicCar(19, "Altima", "Nissan", 2023, 450.0);
        Car carWithType = CarFactory.createCarWithType(20, "Maxima", "Nissan", 2023, 550.0, testCarType);
        Car unavailableCar = CarFactory.createUnavailableCar(21, "Sentra", "Nissan", 2023, 350.0, testCarType);

        assertTrue(basicCar.isAvailability());
        assertTrue(carWithType.isAvailability());
        assertFalse(unavailableCar.isAvailability());
    }
}