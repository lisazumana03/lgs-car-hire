package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.repository.reservation.ILocationRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(3)
public class LocationDataInitializer implements CommandLineRunner {

    @Autowired
    private ILocationRepository locationRepository;

    @Value("${app.database.init.locations.enabled:true}")
    private boolean initEnabled;

    @Value("${app.database.init.locations.clear-existing:false}")
    private boolean clearExisting;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (!initEnabled) {
            System.out.println("Location initialization is disabled. Set app.database.init.locations.enabled=true to enable.");
            return;
        }

        List<Location> existingLocations = locationRepository.findAll();

        if (!existingLocations.isEmpty() && !clearExisting) {
            System.out.println("Database already contains " + existingLocations.size() + " locations.");
            System.out.println("Skipping location initialization. Set app.database.init.locations.clear-existing=true to override.");
            return;
        }

        if (clearExisting && !existingLocations.isEmpty()) {
            System.out.println("Clearing existing locations as requested...");
            locationRepository.deleteAll();
            System.out.println("Existing locations cleared.");
        }

        System.out.println("============================================");
        System.out.println("Initializing location data...");
        System.out.println("============================================");

        createLocations();

        System.out.println("============================================");
        System.out.println("Location initialization completed successfully!");
        System.out.println("--------------------------------------------");
        System.out.println("Created " + locationRepository.count() + " locations");
        System.out.println("--------------------------------------------");
        displayLocationSummary();
        System.out.println("============================================");
    }

    private void createLocations() {
        List<Location> locations = new ArrayList<>();

        // Gauteng Province Locations
        locations.add(createLocation(
            "OR Tambo International Airport",
            1, "O.R. Tambo Airport Road",
            "Johannesburg", "Gauteng", "South Africa", "1627"
        ));

        locations.add(createLocation(
            "Sandton City",
            83, "Rivonia Road",
            "Sandton", "Gauteng", "South Africa", "2196"
        ));

        locations.add(createLocation(
            "Rosebank Mall",
            50, "Bath Avenue",
            "Rosebank", "Gauteng", "South Africa", "2196"
        ));

        locations.add(createLocation(
            "Pretoria CBD",
            123, "Church Street",
            "Pretoria", "Gauteng", "South Africa", "0002"
        ));

        locations.add(createLocation(
            "Mall of Africa",
            1, "Lone Creek Crescent",
            "Midrand", "Gauteng", "South Africa", "1685"
        ));

        // Western Cape Province Locations
        locations.add(createLocation(
            "Cape Town International Airport",
            1, "Airport Approach Road",
            "Cape Town", "Western Cape", "South Africa", "7490"
        ));

        locations.add(createLocation(
            "V&A Waterfront",
            19, "Dock Road",
            "Cape Town", "Western Cape", "South Africa", "8001"
        ));

        locations.add(createLocation(
            "Century City",
            5, "Century Boulevard",
            "Cape Town", "Western Cape", "South Africa", "7441"
        ));

        locations.add(createLocation(
            "Stellenbosch Town Centre",
            52, "Dorp Street",
            "Stellenbosch", "Western Cape", "South Africa", "7600"
        ));

        // KwaZulu-Natal Province Locations
        locations.add(createLocation(
            "King Shaka International Airport",
            1, "King Shaka Drive",
            "Durban", "KwaZulu-Natal", "South Africa", "4399"
        ));

        locations.add(createLocation(
            "Gateway Theatre of Shopping",
            1, "Palm Boulevard",
            "Umhlanga", "KwaZulu-Natal", "South Africa", "4319"
        ));

        locations.add(createLocation(
            "Durban Beachfront",
            200, "OR Tambo Parade",
            "Durban", "KwaZulu-Natal", "South Africa", "4001"
        ));

        // Eastern Cape Province Locations
        locations.add(createLocation(
            "Port Elizabeth Airport",
            1, "Allister Miller Drive",
            "Port Elizabeth", "Eastern Cape", "South Africa", "6001"
        ));

        locations.add(createLocation(
            "Greenacres Shopping Centre",
            1, "Heugh Road",
            "Port Elizabeth", "Eastern Cape", "South Africa", "6045"
        ));

        // Mpumalanga Province Locations
        locations.add(createLocation(
            "Kruger Mpumalanga International Airport",
            1, "Airport Road",
            "Mbombela", "Mpumalanga", "South Africa", "1200"
        ));

        locations.add(createLocation(
            "Nelspruit City Centre",
            45, "Brown Street",
            "Mbombela", "Mpumalanga", "South Africa", "1200"
        ));

        // Save all locations
        System.out.println("Creating locations across South Africa...");
        for (Location location : locations) {
            Location saved = locationRepository.save(location);
            System.out.println("✓ Created location: " + saved.getLocationName() +
                             " (" + saved.getCityOrTown() + ", " + saved.getProvinceOrState() + ")");
        }

        System.out.println("\n" + locations.size() + " locations created successfully.");
    }

    private Location createLocation(String name, int streetNum, String streetName,
                                   String city, String province, String country, String postalCode) {
        return new Location.Builder()
                .setLocationName(name.toLowerCase().trim()) // Normalized name
                .setStreetNumber(streetNum)
                .setStreetName(streetName)
                .setCity(city)
                .setProvinceOrState(province)
                .setCountry(country)
                .setPostalCode(postalCode)
                .build();
    }

    private void displayLocationSummary() {
        List<Location> allLocations = locationRepository.findAll();

        // Group by province
        System.out.println("Locations by Province:");
        allLocations.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    Location::getProvinceOrState,
                    java.util.stream.Collectors.counting()
                ))
                .forEach((province, count) ->
                    System.out.println("  * " + province + ": " + count + " location(s)")
                );

        System.out.println();
        System.out.println("Popular Pickup/Dropoff Locations:");
        System.out.println("  * OR Tambo International Airport (Johannesburg)");
        System.out.println("  * Cape Town International Airport");
        System.out.println("  * Sandton City (Johannesburg)");
        System.out.println("  * V&A Waterfront (Cape Town)");
        System.out.println("  * King Shaka International Airport (Durban)");
    }
}
