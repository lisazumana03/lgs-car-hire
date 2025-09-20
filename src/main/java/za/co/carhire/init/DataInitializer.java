package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.factory.vehicle.CarFactory;
import za.co.carhire.factory.vehicle.CarTypeFactory;
import za.co.carhire.service.vehicle.ICarService;
import za.co.carhire.service.vehicle.ICarTypeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ICarService carService;

    @Autowired
    private ICarTypeService carTypeService;

    @Value("${app.database.init.enabled:false}")
    private boolean initEnabled;

    @Value("${app.database.init.clear-existing:false}")
    private boolean clearExisting;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Check if initialization is enabled
        if (!initEnabled) {
            System.out.println("Database initialization is disabled. Set app.database.init.enabled=true to enable.");
            return;
        }

        // Check if data already exists
        Set<Car> existingCars = carService.getCars();
        Set<CarType> existingCarTypes = carTypeService.getCarTypes();

        if (!existingCars.isEmpty() && !clearExisting) {
            System.out.println("Database already contains " + existingCars.size() + " cars and " +
                    existingCarTypes.size() + " car types.");
            System.out.println("Skipping initialization. Set app.database.init.clear-existing=true to override.");
            return;
        }

        if (clearExisting && (!existingCars.isEmpty() || !existingCarTypes.isEmpty())) {
            System.out.println("Clearing existing data as requested...");
            // Delete car types first (they depend on cars)
            existingCarTypes.forEach(carType -> carTypeService.delete(carType.getCarTypeID()));
            // Then delete cars
            existingCars.forEach(car -> carService.delete(car.getCarID()));
            System.out.println("Existing data cleared.");
        }

        System.out.println("============================================");
        System.out.println("Initializing database with sample data...");
        System.out.println("============================================");

        // Create Cars first without CarType
        Map<String, List<Car>> carsByType = createCarsWithoutTypes();

        // Then create CarTypes and associate them with cars
        createCarTypesAndAssociate(carsByType);

        System.out.println("============================================");
        System.out.println("Database initialization completed successfully!");
        System.out.println("--------------------------------------------");
        System.out.println("Created " + carService.getCars().size() + " cars");
        System.out.println("Created " + carTypeService.getCarTypes().size() + " car types");
        System.out.println("--------------------------------------------");
        displaySummaryStatistics();
        System.out.println("============================================");
    }

    private Map<String, List<Car>> createCarsWithoutTypes() {
        Map<String, List<Car>> carsByType = new HashMap<>();

        // Economy Cars
        List<Car> economyCars = new ArrayList<>();
        economyCars.add(createAndSaveCar("Corolla", "Toyota", 2022, 350.0, true,
                "https://example.com/images/toyota-corolla.jpg"));
        economyCars.add(createAndSaveCar("Civic", "Honda", 2023, 380.0, true,
                "https://example.com/images/honda-civic.jpg"));
        economyCars.add(createAndSaveCar("Sentra", "Nissan", 2022, 320.0, true,
                "https://example.com/images/nissan-sentra.jpg"));
        economyCars.add(createAndSaveCar("Elantra", "Hyundai", 2023, 340.0, true,
                "https://example.com/images/hyundai-elantra.jpg"));
        carsByType.put("Economy", economyCars);

        // Sedan Cars
        List<Car> sedanCars = new ArrayList<>();
        sedanCars.add(createAndSaveCar("Camry", "Toyota", 2023, 450.0, true,
                "https://example.com/images/toyota-camry.jpg"));
        sedanCars.add(createAndSaveCar("Accord", "Honda", 2022, 480.0, true,
                "https://example.com/images/honda-accord.jpg"));
        sedanCars.add(createAndSaveCar("Altima", "Nissan", 2023, 420.0, false,
                "https://example.com/images/nissan-altima.jpg"));
        sedanCars.add(createAndSaveCar("Sonata", "Hyundai", 2022, 410.0, true,
                "https://example.com/images/hyundai-sonata.jpg"));
        carsByType.put("Sedan", sedanCars);

        // SUV Cars
        List<Car> suvCars = new ArrayList<>();
        suvCars.add(createAndSaveCar("RAV4", "Toyota", 2023, 550.0, true,
                "https://example.com/images/toyota-rav4.jpg"));
        suvCars.add(createAndSaveCar("CR-V", "Honda", 2022, 580.0, true,
                "https://example.com/images/honda-crv.jpg"));
        suvCars.add(createAndSaveCar("X5", "BMW", 2023, 850.0, true,
                "https://example.com/images/bmw-x5.jpg"));
        suvCars.add(createAndSaveCar("Explorer", "Ford", 2022, 620.0, false,
                "https://example.com/images/ford-explorer.jpg"));
        suvCars.add(createAndSaveCar("Grand Cherokee", "Jeep", 2023, 650.0, true,
                "https://example.com/images/jeep-grand-cherokee.jpg"));
        carsByType.put("SUV", suvCars);

        // Luxury Cars
        List<Car> luxuryCars = new ArrayList<>();
        luxuryCars.add(createAndSaveCar("S-Class", "Mercedes-Benz", 2023, 1200.0, true,
                "https://example.com/images/mercedes-s-class.jpg"));
        luxuryCars.add(createAndSaveCar("7 Series", "BMW", 2023, 1100.0, true,
                "https://example.com/images/bmw-7-series.jpg"));
        luxuryCars.add(createAndSaveCar("A8", "Audi", 2022, 1050.0, false,
                "https://example.com/images/audi-a8.jpg"));
        luxuryCars.add(createAndSaveCar("Continental", "Bentley", 2023, 2500.0, true,
                "https://example.com/images/bentley-continental.jpg"));
        carsByType.put("Luxury", luxuryCars);

        // Sports Cars
        List<Car> sportsCars = new ArrayList<>();
        sportsCars.add(createAndSaveCar("911", "Porsche", 2023, 1500.0, true,
                "https://example.com/images/porsche-911.jpg"));
        sportsCars.add(createAndSaveCar("Corvette", "Chevrolet", 2023, 900.0, true,
                "https://example.com/images/chevrolet-corvette.jpg"));
        sportsCars.add(createAndSaveCar("GT-R", "Nissan", 2022, 1100.0, false,
                "https://example.com/images/nissan-gtr.jpg"));
        sportsCars.add(createAndSaveCar("Supra", "Toyota", 2023, 850.0, true,
                "https://example.com/images/toyota-supra.jpg"));
        carsByType.put("Sports", sportsCars);

        // Convertible Cars
        List<Car> convertibleCars = new ArrayList<>();
        convertibleCars.add(createAndSaveCar("Miata", "Mazda", 2023, 600.0, true,
                "https://example.com/images/mazda-miata.jpg"));
        convertibleCars.add(createAndSaveCar("Z4", "BMW", 2022, 950.0, true,
                "https://example.com/images/bmw-z4.jpg"));
        convertibleCars.add(createAndSaveCar("Mustang Convertible", "Ford", 2023, 700.0, true,
                "https://example.com/images/ford-mustang-convertible.jpg"));
        carsByType.put("Convertible", convertibleCars);

        // Minivan Cars
        List<Car> minivanCars = new ArrayList<>();
        minivanCars.add(createAndSaveCar("Sienna", "Toyota", 2023, 580.0, true,
                "https://example.com/images/toyota-sienna.jpg"));
        minivanCars.add(createAndSaveCar("Odyssey", "Honda", 2022, 560.0, true,
                "https://example.com/images/honda-odyssey.jpg"));
        minivanCars.add(createAndSaveCar("Pacifica", "Chrysler", 2023, 590.0, false,
                "https://example.com/images/chrysler-pacifica.jpg"));
        carsByType.put("Minivan", minivanCars);

        // Electric Cars
        List<Car> electricCars = new ArrayList<>();
        electricCars.add(createAndSaveCar("Model 3", "Tesla", 2023, 800.0, true,
                "https://example.com/images/tesla-model3.jpg"));
        electricCars.add(createAndSaveCar("Model Y", "Tesla", 2023, 900.0, true,
                "https://example.com/images/tesla-modely.jpg"));
        electricCars.add(createAndSaveCar("ID.4", "Volkswagen", 2022, 650.0, true,
                "https://example.com/images/vw-id4.jpg"));
        electricCars.add(createAndSaveCar("Leaf", "Nissan", 2023, 450.0, true,
                "https://example.com/images/nissan-leaf.jpg"));
        electricCars.add(createAndSaveCar("Mach-E", "Ford", 2023, 750.0, false,
                "https://example.com/images/ford-mache.jpg"));
        carsByType.put("Electric", electricCars);

        // Hybrid Cars
        List<Car> hybridCars = new ArrayList<>();
        hybridCars.add(createAndSaveCar("Prius", "Toyota", 2023, 400.0, true,
                "https://example.com/images/toyota-prius.jpg"));
        hybridCars.add(createAndSaveCar("Insight", "Honda", 2022, 380.0, true,
                "https://example.com/images/honda-insight.jpg"));
        hybridCars.add(createAndSaveCar("Ioniq", "Hyundai", 2023, 390.0, true,
                "https://example.com/images/hyundai-ioniq.jpg"));
        hybridCars.add(createAndSaveCar("Camry Hybrid", "Toyota", 2023, 480.0, true,
                "https://example.com/images/toyota-camry-hybrid.jpg"));
        carsByType.put("Hybrid", hybridCars);

        return carsByType;
    }

    private Car createAndSaveCar(String model, String brand, int year, double rentalPrice,
                                 boolean available, String imageUrl) {
        Car car = CarFactory.createBasicCarWithImage(
                0, // ID will be auto-generated
                model,
                brand,
                year,
                rentalPrice,
                imageUrl
        );
        car.setAvailability(available);

        car = carService.create(car);
        System.out.println("Created car: " + brand + " " + model + " (" + year + ") - R" + rentalPrice + "/day");
        return car;
    }

    private void createCarTypesAndAssociate(Map<String, List<Car>> carsByType) {
        // Create CarTypes and associate with the first car of each type
        // Note: Due to the OneToOne relationship where CarType owns the foreign key,
        // each CarType can only be associated with one car

        if (!carsByType.get("Economy").isEmpty()) {
            Car firstEconomyCar = carsByType.get("Economy").get(0);
            CarType economy = CarTypeFactory.createEconomy(0);
            economy.setCar(firstEconomyCar);
            carTypeService.create(economy);

            // Update the car with its type
            firstEconomyCar.setCarType(economy);
            carService.update(firstEconomyCar);
        }

        if (!carsByType.get("Sedan").isEmpty()) {
            Car firstSedanCar = carsByType.get("Sedan").get(0);
            CarType sedan = CarTypeFactory.createSedan(0);
            sedan.setCar(firstSedanCar);
            carTypeService.create(sedan);

            firstSedanCar.setCarType(sedan);
            carService.update(firstSedanCar);
        }

        if (!carsByType.get("SUV").isEmpty()) {
            Car firstSuvCar = carsByType.get("SUV").get(0);
            CarType suv = CarTypeFactory.createSUV(0);
            suv.setCar(firstSuvCar);
            carTypeService.create(suv);

            firstSuvCar.setCarType(suv);
            carService.update(firstSuvCar);
        }

        if (!carsByType.get("Luxury").isEmpty()) {
            Car firstLuxuryCar = carsByType.get("Luxury").get(0);
            CarType luxury = CarTypeFactory.createLuxury(0);
            luxury.setCar(firstLuxuryCar);
            carTypeService.create(luxury);

            firstLuxuryCar.setCarType(luxury);
            carService.update(firstLuxuryCar);
        }

        if (!carsByType.get("Sports").isEmpty()) {
            Car firstSportsCar = carsByType.get("Sports").get(0);
            CarType sports = CarTypeFactory.createSports(0);
            sports.setCar(firstSportsCar);
            carTypeService.create(sports);

            firstSportsCar.setCarType(sports);
            carService.update(firstSportsCar);
        }

        if (!carsByType.get("Convertible").isEmpty()) {
            Car firstConvertibleCar = carsByType.get("Convertible").get(0);
            CarType convertible = CarTypeFactory.createConvertible(0);
            convertible.setCar(firstConvertibleCar);
            carTypeService.create(convertible);

            firstConvertibleCar.setCarType(convertible);
            carService.update(firstConvertibleCar);
        }

        if (!carsByType.get("Minivan").isEmpty()) {
            Car firstMinivanCar = carsByType.get("Minivan").get(0);
            CarType minivan = CarTypeFactory.createMinivan(0);
            minivan.setCar(firstMinivanCar);
            carTypeService.create(minivan);

            firstMinivanCar.setCarType(minivan);
            carService.update(firstMinivanCar);
        }

        if (!carsByType.get("Electric").isEmpty()) {
            Car firstElectricCar = carsByType.get("Electric").get(0);
            CarType electric = CarTypeFactory.createElectric(0);
            electric.setCar(firstElectricCar);
            carTypeService.create(electric);

            firstElectricCar.setCarType(electric);
            carService.update(firstElectricCar);
        }

        if (!carsByType.get("Hybrid").isEmpty()) {
            Car firstHybridCar = carsByType.get("Hybrid").get(0);
            CarType hybrid = CarTypeFactory.createHybrid(0);
            hybrid.setCar(firstHybridCar);
            carTypeService.create(hybrid);

            firstHybridCar.setCarType(hybrid);
            carService.update(firstHybridCar);
        }

        System.out.println("Created and associated car types");
    }

    private void displaySummaryStatistics() {
        Set<Car> allCars = carService.getCars();
        long availableCars = allCars.stream().filter(Car::isAvailability).count();
        long unavailableCars = allCars.size() - availableCars;

        System.out.println("Summary Statistics:");
        System.out.println("- Total Cars: " + allCars.size());
        System.out.println("- Available Cars: " + availableCars);
        System.out.println("- Unavailable Cars: " + unavailableCars);

        // Display cars by brand
        Map<String, Long> carsByBrand = new HashMap<>();
        allCars.forEach(car -> {
            carsByBrand.merge(car.getBrand(), 1L, Long::sum);
        });

        System.out.println("- Cars by Brand:");
        carsByBrand.forEach((brand, count) -> {
            System.out.println("  * " + brand + ": " + count + " cars");
        });
    }
}