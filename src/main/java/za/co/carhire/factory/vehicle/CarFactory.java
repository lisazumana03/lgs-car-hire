package za.co.carhire.factory.vehicle;

// Imtiyaaz Waggie 219374759//
//date:10/05/2025 //
// Updated: 31/08/2025 - Added image URL support
// Updated: 15/10/2025 - Refactored for new Car fields

import za.co.carhire.domain.reservation.Location;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarCondition;
import za.co.carhire.domain.vehicle.CarStatus;
import za.co.carhire.domain.vehicle.CarType;

public class CarFactory {

    public static Car createBasicCar(int carID, String model, String brand, int year, String licensePlate) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setLicensePlate(licensePlate)
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(CarCondition.GOOD)
                .setMileage(0)
                .build();
    }

    public static Car createBasicCarWithImage(int carID, String model, String brand, int year,
                                              String licensePlate, byte[] imageData, String imageName, String imageType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setLicensePlate(licensePlate)
                .setImageData(imageData)
                .setImageName(imageName)
                .setImageType(imageType)
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(CarCondition.GOOD)
                .setMileage(0)
                .build();
    }

    public static Car createCompleteCar(int carID, String model, String brand, int year,
                                        String licensePlate, String vin, String color, int mileage,
                                        CarStatus status, CarCondition condition,
                                        CarType carType, Location currentLocation) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setLicensePlate(licensePlate)
                .setVin(vin)
                .setColor(color)
                .setMileage(mileage)
                .setStatus(status)
                .setCondition(condition)
                .setCarType(carType)
                .setCurrentLocation(currentLocation)
                .build();
    }

    public static Car createCompleteCarWithImage(int carID, String model, String brand, int year,
                                                 String licensePlate, String vin, String color, int mileage,
                                                 CarStatus status, CarCondition condition,
                                                 byte[] imageData, String imageName, String imageType,
                                                 CarType carType, Location currentLocation) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setLicensePlate(licensePlate)
                .setVin(vin)
                .setColor(color)
                .setMileage(mileage)
                .setStatus(status)
                .setCondition(condition)
                .setImageData(imageData)
                .setImageName(imageName)
                .setImageType(imageType)
                .setCarType(carType)
                .setCurrentLocation(currentLocation)
                .build();
    }

    public static Car createCarWithType(int carID, String model, String brand, int year,
                                        String licensePlate, CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setLicensePlate(licensePlate)
                .setCarType(carType)
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(CarCondition.GOOD)
                .setMileage(0)
                .build();
    }

    public static Car createCarWithTypeAndImage(int carID, String model, String brand, int year,
                                                String licensePlate, byte[] imageData, String imageName, String imageType,
                                                CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setLicensePlate(licensePlate)
                .setImageData(imageData)
                .setImageName(imageName)
                .setImageType(imageType)
                .setCarType(carType)
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(CarCondition.GOOD)
                .setMileage(0)
                .build();
    }

    public static Car createCarCopy(Car originalCar, int newCarID) {
        return new Car.Builder()
                .copy(originalCar)
                .setCarID(newCarID)
                .build();
    }

    public static Car createCarWithStatus(int carID, String model, String brand, int year,
                                          String licensePlate, CarType carType, CarStatus status) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setLicensePlate(licensePlate)
                .setCarType(carType)
                .setStatus(status)
                .setCondition(CarCondition.GOOD)
                .setMileage(0)
                .build();
    }

    public static Car createCarWithCondition(int carID, String model, String brand, int year,
                                             String licensePlate, CarType carType, CarCondition condition) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setLicensePlate(licensePlate)
                .setCarType(carType)
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(condition)
                .setMileage(0)
                .build();
    }
}
