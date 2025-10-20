package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.authentication.Role;
import za.co.carhire.domain.authentication.User;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.factory.vehicle.CarFactory;
import za.co.carhire.factory.vehicle.CarTypeFactory;
import za.co.carhire.repository.authentication.IUserRepository;
import za.co.carhire.service.vehicle.ICarService;
import za.co.carhire.service.vehicle.ICarTypeService;
import za.co.carhire.util.ImageDownloader;

import java.time.LocalDate;
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

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.database.init.enabled:false}")
    private boolean initEnabled;

    @Value("${app.database.init.clear-existing:false}")
    private boolean clearExisting;

    @Value("${app.database.init.download-images:true}")
    private boolean downloadImages;

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

        // Create Users
        createUsers();

        // Create Cars first without CarType
        Map<String, List<Car>> carsByType = createCarsWithoutTypes();

        // Then create CarTypes and associate them with cars
        createCarTypesAndAssociate(carsByType);

        System.out.println("============================================");
        System.out.println("Database initialization completed successfully!");
        System.out.println("--------------------------------------------");
        System.out.println("Created " + userRepository.count() + " users");
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
                "https://media.cdntoyota.co.za/toyotacms23/attachments/cmdwsn56u5h981oakxfbkvupg-corolla-cross-side-img.desktop.png"));
        economyCars.add(createAndSaveCar("Civic", "Honda", 2023, 380.0, true,
                "https://cdn.honda.co.za/main-03/general/civic-1-5-rs-cvt-2/featured/Civic_Models.png"));
        economyCars.add(createAndSaveCar("Sentra", "Nissan", 2022, 320.0, true,
                "https://www.nissanusa.com/content/dam/Nissan/us/vehicles/sentra/2025/gallery/exterior/2025-nissan-sentra-blue-driveway-front-building.jpg"));
        economyCars.add(createAndSaveCar("Elantra", "Hyundai", 2023, 340.0, true,
                "https://img.autotrader.co.za/20841671/Crop676x507"));
        carsByType.put("Economy", economyCars);

        // Sedan Cars
        List<Car> sedanCars = new ArrayList<>();
        sedanCars.add(createAndSaveCar("Camry", "Toyota", 2023, 450.0, true,
                "https://tmna.aemassets.toyota.com/is/image/toyota/toyota/jellies/max/2026/camry/nightshade/2558/3u5/36/5.png?fmt=png-alpha&wid=930&hei=328&qlt=90"));
        sedanCars.add(createAndSaveCar("Accord", "Honda", 2022, 480.0, true,
                "https://automobiles.honda.com/-/media/Honda-Automobiles/Vehicles/2025/accord-sedan/feature-blades/EXTERIOR--INTERIOR/Overview/MY25-accord-feat-blade-exterior-interior-overview-desktop-2x.jpg"));
        sedanCars.add(createAndSaveCar("Altima", "Nissan", 2023, 420.0, false,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/2024_Nissan_Altima_SR%2C_front_left%2C_05-05-2025.jpg/500px-2024_Nissan_Altima_SR%2C_front_left%2C_05-05-2025.jpg"));
        sedanCars.add(createAndSaveCar("Sonata", "Hyundai", 2022, 410.0, true,
                "https://s7d1.scene7.com/is/image/hyundai/2024-sonata-dn8-n-line-0362-gallery:16-9?wid=1440&hei=810&qlt=85,0&fmt=webp"));
        carsByType.put("Sedan", sedanCars);

        // SUV Cars
        List<Car> suvCars = new ArrayList<>();
        suvCars.add(createAndSaveCar("RAV4", "Toyota", 2023, 550.0, true,
                "https://media.cdntoyota.co.za/toyotacms23/attachments/cklg63bdl00250qnqz8cc32r8-0101010203-exterior-2.desktop.jpg"));
        suvCars.add(createAndSaveCar("CR-V", "Honda", 2022, 580.0, true,
                "https://cdn.honda.co.za/main-03/general/crv-1-5t-executive-cvt/featured/CRV_Models.png"));
        suvCars.add(createAndSaveCar("X5", "BMW", 2023, 850.0, true,
                "https://images.unsplash.com/photo-1696294586764-6baffd088b71?q=80&w=627&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        suvCars.add(createAndSaveCar("Explorer", "Ford", 2022, 620.0, false,
                "https://images.unsplash.com/photo-1672690536198-cf2ec44b73b6?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        suvCars.add(createAndSaveCar("Grand Cherokee", "Jeep", 2023, 650.0, true,
                "https://images.unsplash.com/photo-1511527844068-006b95d162c2?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        carsByType.put("SUV", suvCars);

        // Luxury Cars
        List<Car> luxuryCars = new ArrayList<>();
        luxuryCars.add(createAndSaveCar("S-Class", "Mercedes-Benz", 2023, 1200.0, true,
                "https://images.unsplash.com/photo-1680446983373-853872fb667a?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        luxuryCars.add(createAndSaveCar("7 Series", "BMW", 2023, 1100.0, true,
                "https://images.unsplash.com/photo-1523983388277-336a66bf9bcd?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        luxuryCars.add(createAndSaveCar("A8", "Audi", 2022, 1050.0, false,
                "https://images.unsplash.com/photo-1540066019607-e5f69323a8dc?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        luxuryCars.add(createAndSaveCar("Continental", "Bentley", 2023, 2500.0, true,
                "https://images.unsplash.com/photo-1637950634698-2e27e3d6f3db?q=80&w=786&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        carsByType.put("Luxury", luxuryCars);

        // Sports Cars
        List<Car> sportsCars = new ArrayList<>();
        sportsCars.add(createAndSaveCar("911", "Porsche", 2023, 1500.0, true,
                "https://images.unsplash.com/photo-1593353798398-6024b7444bb6?q=80&w=764&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        sportsCars.add(createAndSaveCar("Corvette", "Chevrolet", 2023, 900.0, true,
                "https://images.unsplash.com/photo-1617255148661-afb26d4f933e?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        sportsCars.add(createAndSaveCar("GT-R", "Nissan", 2022, 1100.0, false,
                "https://images.unsplash.com/photo-1609964729554-a02fb2a04830?q=80&w=765&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        sportsCars.add(createAndSaveCar("Supra", "Toyota", 2023, 850.0, true,
                "https://images.unsplash.com/photo-1603811478698-0b1d6256f79a?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        carsByType.put("Sports", sportsCars);

        // Convertible Cars
        List<Car> convertibleCars = new ArrayList<>();
        convertibleCars.add(createAndSaveCar("Miata", "Mazda", 2023, 600.0, true,
                "https://images.unsplash.com/photo-1603739297343-21e40a7e127d?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        convertibleCars.add(createAndSaveCar("Z4", "BMW", 2022, 950.0, true,
                "https://images.unsplash.com/photo-1612610683796-3b7d3a65df3d?q=80&w=765&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        convertibleCars.add(createAndSaveCar("Mustang Convertible", "Ford", 2023, 700.0, true,
                "https://images.unsplash.com/photo-1547744152-14d985cb937f?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        carsByType.put("Convertible", convertibleCars);

        // Minivan Cars
        List<Car> minivanCars = new ArrayList<>();
        minivanCars.add(createAndSaveCar("Sienna", "Toyota", 2023, 580.0, true,
                "https://images.unsplash.com/photo-1638618164682-12b986ec2a75?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        minivanCars.add(createAndSaveCar("Odyssey", "Honda", 2022, 560.0, true,
                "https://images.unsplash.com/photo-1578659258511-4a4e7dee7344?q=80&w=1331&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        minivanCars.add(createAndSaveCar("Pacifica", "Chrysler", 2023, 590.0, false,
                "https://www.chrysler.com/content/dam/fca-brands/na/chrysler/en_us/2026/pacifica/hybrid/overview/desktop/my26-chrysler-pacifica-hybrid-overview-hero-hybrid101-desktop.jpg"));
        carsByType.put("Minivan", minivanCars);

        // Electric Cars
        List<Car> electricCars = new ArrayList<>();
        electricCars.add(createAndSaveCar("Model 3", "Tesla", 2023, 800.0, true,
                "https://images.unsplash.com/photo-1585011664466-b7bbe92f34ef?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        electricCars.add(createAndSaveCar("Model Y", "Tesla", 2023, 900.0, true,
                "https://images.unsplash.com/photo-1600661653561-629509216228?q=80&w=1169&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        electricCars.add(createAndSaveCar("ID.4", "Volkswagen", 2022, 650.0, true,
                "https://images.unsplash.com/photo-1572811298797-9eecadf6cb24?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        carsByType.put("Electric", electricCars);

        // Hybrid Cars
        List<Car> hybridCars = new ArrayList<>();
        hybridCars.add(createAndSaveCar("Prius", "Toyota", 2023, 400.0, true,
                "https://images.unsplash.com/photo-1638618164682-12b986ec2a75?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        hybridCars.add(createAndSaveCar("Insight", "Honda", 2022, 380.0, true,
                "https://upload.wikimedia.org/wikipedia/commons/e/e7/2019-2020_Honda_Insight_EX_1.5_6AA-ZE4_%2820220910%29.jpg"));
        carsByType.put("Hybrid", hybridCars);

        return carsByType;
    }

    private Car createAndSaveCar(String model, String brand, int year, double rentalPrice,
                                 boolean available, String imageUrl) {
        Car car;

        // Download and store image if enabled and URL is provided
        if (downloadImages && imageUrl != null && !imageUrl.trim().isEmpty()) {
            System.out.println("Downloading image for " + brand + " " + model + "...");

            byte[] imageData = ImageDownloader.downloadImageWithRetry(imageUrl, 3);

            if (imageData != null) {
                String imageType = ImageDownloader.getImageType(imageUrl);
                String imageName = ImageDownloader.getImageName(imageUrl, brand, model);

                car = CarFactory.createBasicCarWithImage(
                        0, // ID will be auto-generated
                        model,
                        brand,
                        year,
                        rentalPrice,
                        imageData,
                        imageName,
                        imageType
                );

                System.out.println("  ✓ Image downloaded successfully (" + imageData.length + " bytes)");
            } else {
                // If image download fails, create car without image
                System.out.println("  ✗ Failed to download image, creating car without image");
                car = CarFactory.createBasicCar(
                        0,
                        model,
                        brand,
                        year,
                        rentalPrice
                );
            }
        } else {
            // Create car without image if download is disabled or no URL provided
            car = CarFactory.createBasicCar(
                    0,
                    model,
                    brand,
                    year,
                    rentalPrice
            );
        }

        car.setAvailability(available);
        car = carService.create(car);

        System.out.println("Created car: " + brand + " " + model + " (" + year + ") - R" + rentalPrice + "/day");
        return car;
    }

    private void createCarTypesAndAssociate(Map<String, List<Car>> carsByType) {
        // Create CarTypes and associate with ALL cars of each type
        // Now using ManyToOne relationship - multiple cars can share the same CarType

        if (!carsByType.get("Economy").isEmpty()) {
            CarType economy = CarTypeFactory.createEconomy(0);
            economy = carTypeService.create(economy);

            for (Car car : carsByType.get("Economy")) {
                car.setCarType(economy);
                carService.update(car);
            }
        }

        if (!carsByType.get("Sedan").isEmpty()) {
            CarType sedan = CarTypeFactory.createSedan(0);
            sedan = carTypeService.create(sedan);

            for (Car car : carsByType.get("Sedan")) {
                car.setCarType(sedan);
                carService.update(car);
            }
        }

        if (!carsByType.get("SUV").isEmpty()) {
            CarType suv = CarTypeFactory.createSUV(0);
            suv = carTypeService.create(suv);

            for (Car car : carsByType.get("SUV")) {
                car.setCarType(suv);
                carService.update(car);
            }
        }

        if (!carsByType.get("Luxury").isEmpty()) {
            CarType luxury = CarTypeFactory.createLuxury(0);
            luxury = carTypeService.create(luxury);

            for (Car car : carsByType.get("Luxury")) {
                car.setCarType(luxury);
                carService.update(car);
            }
        }

        if (!carsByType.get("Sports").isEmpty()) {
            CarType sports = CarTypeFactory.createSports(0);
            sports = carTypeService.create(sports);

            for (Car car : carsByType.get("Sports")) {
                car.setCarType(sports);
                carService.update(car);
            }
        }

        if (!carsByType.get("Convertible").isEmpty()) {
            CarType convertible = CarTypeFactory.createConvertible(0);
            convertible = carTypeService.create(convertible);

            for (Car car : carsByType.get("Convertible")) {
                car.setCarType(convertible);
                carService.update(car);
            }
        }

        if (!carsByType.get("Minivan").isEmpty()) {
            CarType minivan = CarTypeFactory.createMinivan(0);
            minivan = carTypeService.create(minivan);

            for (Car car : carsByType.get("Minivan")) {
                car.setCarType(minivan);
                carService.update(car);
            }
        }

        if (!carsByType.get("Electric").isEmpty()) {
            CarType electric = CarTypeFactory.createElectric(0);
            electric = carTypeService.create(electric);

            for (Car car : carsByType.get("Electric")) {
                car.setCarType(electric);
                carService.update(car);
            }
        }

        if (!carsByType.get("Hybrid").isEmpty()) {
            CarType hybrid = CarTypeFactory.createHybrid(0);
            hybrid = carTypeService.create(hybrid);

            for (Car car : carsByType.get("Hybrid")) {
                car.setCarType(hybrid);
                carService.update(car);
            }
        }

        System.out.println("Created and associated car types to all cars");
    }

    private void createUsers() {
        System.out.println("\n--- Creating Users ---");

        // Create Admin User
        User admin = new User.Builder()
                .setIdNumber(9001015800082L)
                .setFirstName("Admin")
                .setLastName("User")
                .setEmail("admin@carhire.co.za")
                .setDateOfBirth(LocalDate.of(1990, 1, 15))
                .setPhoneNumber("0211234567")
                .setPassword(passwordEncoder.encode("admin123"))
                .setRole(Role.ADMIN)
                .build();

        admin = userRepository.save(admin);
        System.out.println("Created admin user: " + admin.getEmail() + " (Role: " + admin.getRole() + ")");

        // Create Customer User
        User customer = new User.Builder()
                .setIdNumber(9505201234088L)
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("customer@example.com")
                .setDateOfBirth(LocalDate.of(1995, 5, 20))
                .setPhoneNumber("0829876543")
                .setPassword(passwordEncoder.encode("customer123"))
                .setRole(Role.CUSTOMER)
                .build();

        customer = userRepository.save(customer);
        System.out.println("Created customer user: " + customer.getEmail() + " (Role: " + customer.getRole() + ")");

        System.out.println("--- Users Created Successfully ---\n");
    }

    private void displaySummaryStatistics() {
        Set<Car> allCars = carService.getCars();
        long availableCars = allCars.stream().filter(Car::isAvailability).count();
        long unavailableCars = allCars.size() - availableCars;
        long carsWithImages = allCars.stream()
                .filter(car -> car.getImageData() != null && car.getImageData().length > 0)
                .count();

        System.out.println("Summary Statistics:");
        System.out.println("- Total Cars: " + allCars.size());
        System.out.println("- Available Cars: " + availableCars);
        System.out.println("- Unavailable Cars: " + unavailableCars);
        System.out.println("- Cars with Images: " + carsWithImages + " (" +
                (allCars.size() > 0 ? (carsWithImages * 100 / allCars.size()) : 0) + "%)");

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