package za.co.carhire.factory.vehicle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.carhire.domain.vehicle.*;

import static org.junit.jupiter.api.Assertions.*;

public class CarFactoryTest {

    private CarType testCarType;

    @BeforeEach
    void setUp() {
        testCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setCategory(VehicleCategory.SEDAN)
                .setFuelType(FuelType.PETROL)
                .setTransmissionType(TransmissionType.AUTOMATIC)
                .setNumberOfSeats(5)
                .setNumberOfDoors(4)
                .setAirConditioned(true)
                .setLuggageCapacity(3)
                .setDescription("Comfortable mid-size sedan for testing")
                .build();
    }

    @Test
    void testCreateBasicCar() {
        Car car = CarFactory.createBasicCar(1, "Corolla", "Toyota", 2022, "ABC 123 GP");

        assertNotNull(car);
        assertEquals(1, car.getCarID());
        assertEquals("Corolla", car.getModel());
        assertEquals("Toyota", car.getBrand());
        assertEquals(2022, car.getYear());
        assertEquals("ABC 123 GP", car.getLicensePlate());
        assertEquals(CarStatus.AVAILABLE, car.getStatus());
        assertEquals(CarCondition.GOOD, car.getCondition());
        assertEquals(0, car.getMileage());
        assertNull(car.getImageData());
        assertNull(car.getCarType());
    }

    @Test
    void testCreateBasicCarWithImage() {
        byte[] imageData = "test-image-data".getBytes();
        String imageName = "car-image.jpg";
        String imageType = "image/jpeg";
        Car car = CarFactory.createBasicCarWithImage(2, "Civic", "Honda", 2023, "DEF 456 GP", imageData, imageName, imageType);

        assertNotNull(car);
        assertEquals(2, car.getCarID());
        assertEquals("Civic", car.getModel());
        assertEquals("Honda", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals("DEF 456 GP", car.getLicensePlate());
        assertNotNull(car.getImageData());
        assertEquals(imageName, car.getImageName());
        assertEquals(imageType, car.getImageType());
        assertEquals(CarStatus.AVAILABLE, car.getStatus());
        assertNull(car.getCarType());
    }

    @Test
    void testCreateCompleteCar() {
        Car car = CarFactory.createCompleteCar(3, "Camry", "Toyota", 2021,
                "GHI 789 GP", "TOY21123456789012", "Silver", 45000,
                CarStatus.RENTED, CarCondition.GOOD, testCarType, null);

        assertNotNull(car);
        assertEquals(3, car.getCarID());
        assertEquals("Camry", car.getModel());
        assertEquals("Toyota", car.getBrand());
        assertEquals(2021, car.getYear());
        assertEquals("GHI 789 GP", car.getLicensePlate());
        assertEquals("TOY21123456789012", car.getVin());
        assertEquals("Silver", car.getColor());
        assertEquals(45000, car.getMileage());
        assertEquals(CarStatus.RENTED, car.getStatus());
        assertEquals(CarCondition.GOOD, car.getCondition());
        assertNull(car.getImageData());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateCompleteCarWithImage() {
        byte[] imageData = "complete-car-data".getBytes();
        String imageName = "complete-car.jpg";
        String imageType = "image/jpeg";
        Car car = CarFactory.createCompleteCarWithImage(4, "Accord", "Honda", 2022,
                "JKL 012 GP", "HON22234567890123", "Blue", 30000,
                CarStatus.AVAILABLE, CarCondition.EXCELLENT,
                imageData, imageName, imageType, testCarType, null);

        assertNotNull(car);
        assertEquals(4, car.getCarID());
        assertEquals("Accord", car.getModel());
        assertEquals("Honda", car.getBrand());
        assertEquals(2022, car.getYear());
        assertEquals("JKL 012 GP", car.getLicensePlate());
        assertEquals("HON22234567890123", car.getVin());
        assertEquals("Blue", car.getColor());
        assertEquals(30000, car.getMileage());
        assertEquals(CarStatus.AVAILABLE, car.getStatus());
        assertEquals(CarCondition.EXCELLENT, car.getCondition());
        assertNotNull(car.getImageData());
        assertEquals(imageName, car.getImageName());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateCarWithType() {
        Car car = CarFactory.createCarWithType(5, "3 Series", "BMW", 2023, "MNO 345 GP", testCarType);

        assertNotNull(car);
        assertEquals(5, car.getCarID());
        assertEquals("3 Series", car.getModel());
        assertEquals("BMW", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals("MNO 345 GP", car.getLicensePlate());
        assertEquals(CarStatus.AVAILABLE, car.getStatus());
        assertEquals(CarCondition.GOOD, car.getCondition());
        assertNull(car.getImageData());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateCarWithTypeAndImage() {
        byte[] imageData = "bmw-image-data".getBytes();
        String imageName = "bmw.jpg";
        String imageType = "image/jpeg";
        Car car = CarFactory.createCarWithTypeAndImage(6, "X5", "BMW", 2023,
                "PQR 678 GP", imageData, imageName, imageType, testCarType);

        assertNotNull(car);
        assertEquals(6, car.getCarID());
        assertEquals("X5", car.getModel());
        assertEquals("BMW", car.getBrand());
        assertEquals(2023, car.getYear());
        assertEquals("PQR 678 GP", car.getLicensePlate());
        assertNotNull(car.getImageData());
        assertEquals(imageName, car.getImageName());
        assertEquals(CarStatus.AVAILABLE, car.getStatus());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateCarCopy() {
        byte[] imageData = "tesla-image-data".getBytes();
        Car originalCar = CarFactory.createCompleteCarWithImage(9, "Model S", "Tesla", 2023,
                "STU 901 GP", "TES23345678901234", "Red", 5000,
                CarStatus.AVAILABLE, CarCondition.EXCELLENT,
                imageData, "tesla.jpg", "image/jpeg", testCarType, null);

        Car copiedCar = CarFactory.createCarCopy(originalCar, 10);

        assertNotNull(copiedCar);
        assertEquals(10, copiedCar.getCarID());
        assertEquals(originalCar.getModel(), copiedCar.getModel());
        assertEquals(originalCar.getBrand(), copiedCar.getBrand());
        assertEquals(originalCar.getYear(), copiedCar.getYear());
        assertEquals(originalCar.getLicensePlate(), copiedCar.getLicensePlate());
        assertEquals(originalCar.getVin(), copiedCar.getVin());
        assertEquals(originalCar.getColor(), copiedCar.getColor());
        assertEquals(originalCar.getMileage(), copiedCar.getMileage());
        assertEquals(originalCar.getStatus(), copiedCar.getStatus());
        assertEquals(originalCar.getCondition(), copiedCar.getCondition());
        assertEquals(originalCar.getImageName(), copiedCar.getImageName());
        assertEquals(originalCar.getCarType(), copiedCar.getCarType());
    }

    @Test
    void testCreateCarWithStatus() {
        Car car = CarFactory.createCarWithStatus(11, "Golf", "Volkswagen", 2021,
                "VWX 234 GP", testCarType, CarStatus.MAINTENANCE);

        assertNotNull(car);
        assertEquals(11, car.getCarID());
        assertEquals("Golf", car.getModel());
        assertEquals("Volkswagen", car.getBrand());
        assertEquals(2021, car.getYear());
        assertEquals("VWX 234 GP", car.getLicensePlate());
        assertEquals(CarStatus.MAINTENANCE, car.getStatus());
        assertEquals(CarCondition.GOOD, car.getCondition());
        assertNull(car.getImageData());
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateCarWithCondition() {
        Car car = CarFactory.createCarWithCondition(12, "Passat", "Volkswagen",
                2022, "YZA 567 GP", testCarType, CarCondition.NEEDS_SERVICE);

        assertNotNull(car);
        assertEquals(12, car.getCarID());
        assertEquals("Passat", car.getModel());
        assertEquals("Volkswagen", car.getBrand());
        assertEquals(2022, car.getYear());
        assertEquals("YZA 567 GP", car.getLicensePlate());
        assertEquals(CarStatus.AVAILABLE, car.getStatus());
        assertEquals(CarCondition.NEEDS_SERVICE, car.getCondition());
        assertNotNull(car);
        assertEquals(testCarType, car.getCarType());
    }

    @Test
    void testCreateBasicCarWithSpecialCharacters() {
        Car car = CarFactory.createBasicCar(13, "CX-5", "Mazda", 2023, "BCD 890 GP");

        assertNotNull(car);
        assertEquals("CX-5", car.getModel());
        assertEquals("Mazda", car.getBrand());
        assertEquals("BCD 890 GP", car.getLicensePlate());
    }

    @Test
    void testCreateCarWithNullCarType() {
        Car car = CarFactory.createCarWithType(14, "Focus", "Ford", 2022, "EFG 123 GP", null);

        assertNotNull(car);
        assertEquals(14, car.getCarID());
        assertEquals("Focus", car.getModel());
        assertNull(car.getCarType());
        assertEquals(CarStatus.AVAILABLE, car.getStatus());
    }

    @Test
    void testCreateMultipleCarsWithDifferentFactoryMethods() {
        Car basicCar = CarFactory.createBasicCar(16, "Yaris", "Toyota", 2023, "HIJ 456 GP");
        byte[] imageData = "fit-image-data".getBytes();
        Car carWithImage = CarFactory.createBasicCarWithImage(17, "Fit", "Honda", 2023, "KLM 789 GP", imageData, "fit.jpg", "image/jpeg");
        Car carWithType = CarFactory.createCarWithType(18, "A3", "Audi", 2023, "NOP 012 GP", testCarType);

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
    void testStatusDefaults() {
        Car basicCar = CarFactory.createBasicCar(19, "Altima", "Nissan", 2023, "QRS 345 GP");
        Car carWithType = CarFactory.createCarWithType(20, "Maxima", "Nissan", 2023, "TUV 678 GP", testCarType);
        Car maintenanceCar = CarFactory.createCarWithStatus(21, "Sentra", "Nissan", 2023, "WXY 901 GP", testCarType, CarStatus.MAINTENANCE);

        assertEquals(CarStatus.AVAILABLE, basicCar.getStatus());
        assertEquals(CarStatus.AVAILABLE, carWithType.getStatus());
        assertEquals(CarStatus.MAINTENANCE, maintenanceCar.getStatus());
    }

    @Test
    void testAllCarStatuses() {
        Car availableCar = CarFactory.createCarWithStatus(22, "Car1", "Brand", 2023, "AAA 111 GP", testCarType, CarStatus.AVAILABLE);
        Car rentedCar = CarFactory.createCarWithStatus(23, "Car2", "Brand", 2023, "BBB 222 GP", testCarType, CarStatus.RENTED);
        Car maintenanceCar = CarFactory.createCarWithStatus(24, "Car3", "Brand", 2023, "CCC 333 GP", testCarType, CarStatus.MAINTENANCE);
        Car outOfServiceCar = CarFactory.createCarWithStatus(25, "Car4", "Brand", 2023, "DDD 444 GP", testCarType, CarStatus.OUT_OF_SERVICE);
        Car reservedCar = CarFactory.createCarWithStatus(26, "Car5", "Brand", 2023, "EEE 555 GP", testCarType, CarStatus.RESERVED);

        assertEquals(CarStatus.AVAILABLE, availableCar.getStatus());
        assertEquals(CarStatus.RENTED, rentedCar.getStatus());
        assertEquals(CarStatus.MAINTENANCE, maintenanceCar.getStatus());
        assertEquals(CarStatus.OUT_OF_SERVICE, outOfServiceCar.getStatus());
        assertEquals(CarStatus.RESERVED, reservedCar.getStatus());
    }
}
