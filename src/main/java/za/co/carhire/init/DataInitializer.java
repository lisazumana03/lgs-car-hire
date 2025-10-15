package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarCondition;
import za.co.carhire.domain.vehicle.CarStatus;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.domain.vehicle.PricingRule;
import za.co.carhire.factory.vehicle.CarFactory;
import za.co.carhire.factory.vehicle.CarTypeFactory;
import za.co.carhire.factory.vehicle.PricingRuleFactory;
import za.co.carhire.service.vehicle.ICarService;
import za.co.carhire.service.vehicle.ICarTypeService;
import za.co.carhire.service.vehicle.IPricingRuleService;

import java.util.*;

@Component
@Order(2)
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ICarService carService;

    @Autowired
    private ICarTypeService carTypeService;

    @Autowired
    private IPricingRuleService pricingRuleService;

    @Value("${app.database.init.enabled:false}")
    private boolean initEnabled;

    @Value("${app.database.init.clear-existing:false}")
    private boolean clearExisting;

    private static final String[] COLORS = {"White", "Black", "Silver", "Gray", "Blue", "Red", "Green", "Yellow"};
    private static final Random RANDOM = new Random();
    private int licenseCounter = 1000;

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
            existingCars.forEach(car -> carService.delete(car.getCarID()));
            existingCarTypes.forEach(carType -> carTypeService.delete(carType.getCarTypeID()));
            System.out.println("Existing data cleared.");
        }

        System.out.println("============================================");
        System.out.println("Initializing vehicle database with sample data...");
        System.out.println("============================================");

        // Create CarTypes and PricingRules first
        Map<String, CarType> carTypeMap = createCarTypesWithPricing();

        // Then create Cars
        createCarsWithTypes(carTypeMap);

        System.out.println("============================================");
        System.out.println("Database initialization completed successfully!");
        System.out.println("--------------------------------------------");
        System.out.println("Created " + carService.getCars().size() + " cars");
        System.out.println("Created " + carTypeService.getCarTypes().size() + " car types");
        System.out.println("Created " + pricingRuleService.getAllPricingRules().size() + " pricing rules");
        System.out.println("--------------------------------------------");
        displaySummaryStatistics();
        System.out.println("============================================");
    }

    private Map<String, CarType> createCarTypesWithPricing() {
        Map<String, CarType> carTypeMap = new HashMap<>();

        // Economy
        CarType economy = CarTypeFactory.createEconomy(0);
        economy = carTypeService.create(economy);
        PricingRule economyPricing = PricingRuleFactory.createEconomyPricing(0, economy);
        pricingRuleService.create(economyPricing);
        carTypeMap.put("Economy", economy);
        System.out.println("Created car type: Economy with pricing (R350/day)");

        // Compact
        CarType compact = CarTypeFactory.createCompact(0);
        compact = carTypeService.create(compact);
        PricingRule compactPricing = PricingRuleFactory.createCompactPricing(0, compact);
        pricingRuleService.create(compactPricing);
        carTypeMap.put("Compact", compact);
        System.out.println("Created car type: Compact with pricing (R380/day)");

        // Sedan
        CarType sedan = CarTypeFactory.createSedan(0);
        sedan = carTypeService.create(sedan);
        PricingRule sedanPricing = PricingRuleFactory.createSedanPricing(0, sedan);
        pricingRuleService.create(sedanPricing);
        carTypeMap.put("Sedan", sedan);
        System.out.println("Created car type: Sedan with pricing (R450/day)");

        // SUV
        CarType suv = CarTypeFactory.createSUV(0);
        suv = carTypeService.create(suv);
        PricingRule suvPricing = PricingRuleFactory.createSUVPricing(0, suv);
        pricingRuleService.create(suvPricing);
        carTypeMap.put("SUV", suv);
        System.out.println("Created car type: SUV with pricing (R600/day)");

        // Luxury
        CarType luxury = CarTypeFactory.createLuxury(0);
        luxury = carTypeService.create(luxury);
        PricingRule luxuryPricing = PricingRuleFactory.createLuxuryPricing(0, luxury);
        pricingRuleService.create(luxuryPricing);
        carTypeMap.put("Luxury", luxury);
        System.out.println("Created car type: Luxury with pricing (R1200/day)");

        // Sports
        CarType sports = CarTypeFactory.createSports(0);
        sports = carTypeService.create(sports);
        PricingRule sportsPricing = PricingRuleFactory.createSportsPricing(0, sports);
        pricingRuleService.create(sportsPricing);
        carTypeMap.put("Sports", sports);
        System.out.println("Created car type: Sports with pricing (R1000/day)");

        // Convertible
        CarType convertible = CarTypeFactory.createConvertible(0);
        convertible = carTypeService.create(convertible);
        PricingRule convertiblePricing = PricingRuleFactory.createConvertiblePricing(0, convertible);
        pricingRuleService.create(convertiblePricing);
        carTypeMap.put("Convertible", convertible);
        System.out.println("Created car type: Convertible with pricing (R700/day)");

        // Minivan
        CarType minivan = CarTypeFactory.createMinivan(0);
        minivan = carTypeService.create(minivan);
        PricingRule minivanPricing = PricingRuleFactory.createMinivanPricing(0, minivan);
        pricingRuleService.create(minivanPricing);
        carTypeMap.put("Minivan", minivan);
        System.out.println("Created car type: Minivan with pricing (R580/day)");

        // Electric
        CarType electric = CarTypeFactory.createElectric(0);
        electric = carTypeService.create(electric);
        PricingRule electricPricing = PricingRuleFactory.createElectricPricing(0, electric);
        pricingRuleService.create(electricPricing);
        carTypeMap.put("Electric", electric);
        System.out.println("Created car type: Electric with pricing (R750/day)");

        // Hybrid
        CarType hybrid = CarTypeFactory.createHybrid(0);
        hybrid = carTypeService.create(hybrid);
        PricingRule hybridPricing = PricingRuleFactory.createHybridPricing(0, hybrid);
        pricingRuleService.create(hybridPricing);
        carTypeMap.put("Hybrid", hybrid);
        System.out.println("Created car type: Hybrid with pricing (R400/day)");

        return carTypeMap;
    }

    private void createCarsWithTypes(Map<String, CarType> carTypeMap) {
        // Economy Cars
        createCar("Corolla", "Toyota", 2022, carTypeMap.get("Economy"), CarStatus.AVAILABLE);
        createCar("Sentra", "Nissan", 2022, carTypeMap.get("Economy"), CarStatus.AVAILABLE);
        createCar("Elantra", "Hyundai", 2023, carTypeMap.get("Economy"), CarStatus.AVAILABLE);

        // Compact Cars
        createCar("Civic", "Honda", 2023, carTypeMap.get("Compact"), CarStatus.AVAILABLE);
        createCar("Golf", "Volkswagen", 2023, carTypeMap.get("Compact"), CarStatus.AVAILABLE);
        createCar("Mazda3", "Mazda", 2022, carTypeMap.get("Compact"), CarStatus.AVAILABLE);

        // Sedan Cars
        createCar("Camry", "Toyota", 2023, carTypeMap.get("Sedan"), CarStatus.AVAILABLE);
        createCar("Accord", "Honda", 2022, carTypeMap.get("Sedan"), CarStatus.AVAILABLE);
        createCar("Altima", "Nissan", 2023, carTypeMap.get("Sedan"), CarStatus.RENTED);
        createCar("Sonata", "Hyundai", 2022, carTypeMap.get("Sedan"), CarStatus.AVAILABLE);

        // SUV Cars
        createCar("RAV4", "Toyota", 2023, carTypeMap.get("SUV"), CarStatus.AVAILABLE);
        createCar("CR-V", "Honda", 2022, carTypeMap.get("SUV"), CarStatus.AVAILABLE);
        createCar("X5", "BMW", 2023, carTypeMap.get("SUV"), CarStatus.AVAILABLE);
        createCar("Explorer", "Ford", 2022, carTypeMap.get("SUV"), CarStatus.MAINTENANCE);
        createCar("Grand Cherokee", "Jeep", 2023, carTypeMap.get("SUV"), CarStatus.AVAILABLE);

        // Luxury Cars
        createCar("S-Class", "Mercedes-Benz", 2023, carTypeMap.get("Luxury"), CarStatus.AVAILABLE);
        createCar("7 Series", "BMW", 2023, carTypeMap.get("Luxury"), CarStatus.AVAILABLE);
        createCar("A8", "Audi", 2022, carTypeMap.get("Luxury"), CarStatus.RESERVED);
        createCar("Continental", "Bentley", 2023, carTypeMap.get("Luxury"), CarStatus.AVAILABLE);

        // Sports Cars
        createCar("911", "Porsche", 2023, carTypeMap.get("Sports"), CarStatus.AVAILABLE);
        createCar("Corvette", "Chevrolet", 2023, carTypeMap.get("Sports"), CarStatus.AVAILABLE);
        createCar("GT-R", "Nissan", 2022, carTypeMap.get("Sports"), CarStatus.RENTED);
        createCar("Supra", "Toyota", 2023, carTypeMap.get("Sports"), CarStatus.AVAILABLE);

        // Convertible Cars
        createCar("Miata", "Mazda", 2023, carTypeMap.get("Convertible"), CarStatus.AVAILABLE);
        createCar("Z4", "BMW", 2022, carTypeMap.get("Convertible"), CarStatus.AVAILABLE);
        createCar("Mustang Convertible", "Ford", 2023, carTypeMap.get("Convertible"), CarStatus.AVAILABLE);

        // Minivan Cars
        createCar("Sienna", "Toyota", 2023, carTypeMap.get("Minivan"), CarStatus.AVAILABLE);
        createCar("Odyssey", "Honda", 2022, carTypeMap.get("Minivan"), CarStatus.AVAILABLE);
        createCar("Pacifica", "Chrysler", 2023, carTypeMap.get("Minivan"), CarStatus.OUT_OF_SERVICE);

        // Electric Cars
        createCar("Model 3", "Tesla", 2023, carTypeMap.get("Electric"), CarStatus.AVAILABLE);
        createCar("Model Y", "Tesla", 2023, carTypeMap.get("Electric"), CarStatus.AVAILABLE);
        createCar("ID.4", "Volkswagen", 2022, carTypeMap.get("Electric"), CarStatus.AVAILABLE);

        // Hybrid Cars
        createCar("Prius", "Toyota", 2023, carTypeMap.get("Hybrid"), CarStatus.AVAILABLE);
        createCar("Insight", "Honda", 2022, carTypeMap.get("Hybrid"), CarStatus.AVAILABLE);
    }

    private void createCar(String model, String brand, int year, CarType carType, CarStatus status) {
        String licensePlate = generateLicensePlate();
        String vin = generateVIN(brand, year);
        String color = COLORS[RANDOM.nextInt(COLORS.length)];
        int mileage = generateMileage(year);
        CarCondition condition = determineCondition(mileage);

        Car car = CarFactory.createCompleteCar(
                0, // ID will be auto-generated
                model,
                brand,
                year,
                licensePlate,
                vin,
                color,
                mileage,
                status,
                condition,
                carType,
                null // No location for now
        );

        car = carService.create(car);
        System.out.println("Created car: " + brand + " " + model + " (" + year + ") - " +
                          licensePlate + " - " + color + " - Status: " + status);
    }

    private String generateLicensePlate() {
        // South African format: ABC 123 GP (3 letters, 3 numbers, 2 letters)
        String letters1 = randomLetters(3);
        String numbers = String.format("%03d", licenseCounter++);
        String letters2 = "GP"; // Gauteng Province
        return letters1 + " " + numbers + " " + letters2;
    }

    private String generateVIN(String brand, int year) {
        // Simplified VIN generation (17 characters)
        String manufacturer = brand.substring(0, Math.min(3, brand.length())).toUpperCase();
        String yearCode = String.valueOf(year).substring(2);
        String random = String.format("%012d", RANDOM.nextInt(1000000000));
        return manufacturer + yearCode + random;
    }

    private String randomLetters(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            char c = (char) ('A' + RANDOM.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }

    private int generateMileage(int year) {
        int age = 2025 - year;
        // Average 15,000 km per year with some variation
        int baseMileage = age * 15000;
        int variation = RANDOM.nextInt(10000) - 5000;
        return Math.max(0, baseMileage + variation);
    }

    private CarCondition determineCondition(int mileage) {
        if (mileage < 20000) return CarCondition.EXCELLENT;
        if (mileage < 50000) return CarCondition.GOOD;
        if (mileage < 100000) return CarCondition.FAIR;
        return CarCondition.NEEDS_SERVICE;
    }

    private void displaySummaryStatistics() {
        Set<Car> allCars = carService.getCars();
        long availableCars = allCars.stream()
                .filter(car -> car.getStatus() == CarStatus.AVAILABLE)
                .count();
        long rentedCars = allCars.stream()
                .filter(car -> car.getStatus() == CarStatus.RENTED)
                .count();
        long maintenanceCars = allCars.stream()
                .filter(car -> car.getStatus() == CarStatus.MAINTENANCE)
                .count();

        System.out.println("Summary Statistics:");
        System.out.println("- Total Cars: " + allCars.size());
        System.out.println("- Available Cars: " + availableCars);
        System.out.println("- Rented Cars: " + rentedCars);
        System.out.println("- In Maintenance: " + maintenanceCars);

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
