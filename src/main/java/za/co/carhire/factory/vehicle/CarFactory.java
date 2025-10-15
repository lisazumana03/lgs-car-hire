package za.co.carhire.factory.vehicle;

// Imtiyaaz Waggie 219374759//
//date:10/05/2025 //
// Updated: 31/08/2025 - Added image URL support

import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;

public class CarFactory {

    public static Car createBasicCar(int carID, String model, String brand, int year, double rentalPrice) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setAvailability(true)
                .build();
    }

    public static Car createBasicCarWithImage(int carID, String model, String brand, int year,
                                              double rentalPrice, byte[] imageData, String imageName, String imageType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setImageData(imageData)
                .setImageName(imageName)
                .setImageType(imageType)
                .setAvailability(true)
                .build();
    }

    public static Car createCompleteCar(int carID, String model, String brand, int year,
                                        boolean availability, double rentalPrice,
                                        CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setAvailability(availability)
                .setRentalPrice(rentalPrice)
                .setCarType(carType)
                .build();
    }

    public static Car createCompleteCarWithImage(int carID, String model, String brand, int year,
                                                 boolean availability, double rentalPrice,
                                                 byte[] imageData, String imageName, String imageType,
                                                 CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setAvailability(availability)
                .setRentalPrice(rentalPrice)
                .setImageData(imageData)
                .setImageName(imageName)
                .setImageType(imageType)
                .setCarType(carType)
                .build();
    }

    public static Car createCarWithType(int carID, String model, String brand, int year,
                                        double rentalPrice, CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setCarType(carType)
                .setAvailability(true)
                .build();
    }

    public static Car createCarWithTypeAndImage(int carID, String model, String brand, int year,
                                                double rentalPrice, byte[] imageData, String imageName, String imageType,
                                                CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setImageData(imageData)
                .setImageName(imageName)
                .setImageType(imageType)
                .setCarType(carType)
                .setAvailability(true)
                .build();
    }


    public static Car createCarCopy(Car originalCar, int newCarID) {
        return new Car.Builder()
                .copy(originalCar)
                .setCarID(newCarID)
                .build();
    }

    public static Car createUnavailableCar(int carID, String model, String brand, int year,
                                           double rentalPrice, CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setCarType(carType)
                .setAvailability(false)
                .build();
    }

    public static Car createUnavailableCarWithImage(int carID, String model, String brand, int year,
                                                    double rentalPrice, byte[] imageData, String imageName, String imageType,
                                                    CarType carType) {
        return new Car.Builder()
                .setCarID(carID)
                .setModel(model)
                .setBrand(brand)
                .setYear(year)
                .setRentalPrice(rentalPrice)
                .setImageData(imageData)
                .setImageName(imageName)
                .setImageType(imageType)
                .setCarType(carType)
                .setAvailability(false)
                .build();
    }
}