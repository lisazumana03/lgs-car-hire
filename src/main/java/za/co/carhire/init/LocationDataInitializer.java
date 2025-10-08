package za.co.carhire.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import za.co.carhire.domain.reservation.Location;
import za.co.carhire.service.reservation.ILocationService;

import java.util.ArrayList;
import java.util.List;

/*
    Imtiyaaz Waggie 219374759
    Date: 09/10/2025
*/

@Component
@Order(4) // Run after InsuranceDataInitializer (Order 3)
public class LocationDataInitializer implements CommandLineRunner {

    @Autowired
    private ILocationService locationService;

    @Value("${app.init.create-sample-locations:true}")
    private boolean createSampleLocations;

    @Override
    public void run(String... args) throws Exception {
        if (!createSampleLocations) {
            System.out.println("Sample location creation is disabled.");
            return;
        }

        // Check if locations already exist
        if (!locationService.getLocations().isEmpty()) {
            System.out.println("Locations already exist. Skipping sample data creation.");
            return;
        }

        System.out.println("============================================");
        System.out.println("Creating sample location data...");
        System.out.println("============================================");

        List<Location> locations = createSampleLocations();

        System.out.println("Sample location data created successfully!");
        System.out.println("Total Locations: " + locations.size());
        System.out.println("============================================");
    }

    private List<Location> createSampleLocations() {
        List<Location> locations = new ArrayList<>();

        // Cape Town International Airport
        Location capeTownAirport = new Location.Builder()
                .setLocationName("Cape Town International Airport")
                .setStreetNumber(1)
                .setStreetName("Matroosfontein")
                .setCity("Cape Town")
                .setProvinceOrState("Western Cape")
                .setCountry("South Africa")
                .setPostalCode("7490")
                .build();
        locations.add(locationService.create(capeTownAirport));
        System.out.println("  ✓ Created location: Cape Town International Airport");

        // V&A Waterfront
        Location waterfront = new Location.Builder()
                .setLocationName("V&A Waterfront Branch")
                .setStreetNumber(19)
                .setStreetName("Dock Road")
                .setCity("Cape Town")
                .setProvinceOrState("Western Cape")
                .setCountry("South Africa")
                .setPostalCode("8001")
                .build();
        locations.add(locationService.create(waterfront));
        System.out.println("  ✓ Created location: V&A Waterfront Branch");

        // Johannesburg OR Tambo Airport
        Location jnbAirport = new Location.Builder()
                .setLocationName("OR Tambo International Airport")
                .setStreetNumber(1)
                .setStreetName("Jones Road")
                .setCity("Johannesburg")
                .setProvinceOrState("Gauteng")
                .setCountry("South Africa")
                .setPostalCode("1627")
                .build();
        locations.add(locationService.create(jnbAirport));
        System.out.println("  ✓ Created location: OR Tambo International Airport");

        // Sandton City
        Location sandton = new Location.Builder()
                .setLocationName("Sandton City Branch")
                .setStreetNumber(83)
                .setStreetName("Rivonia Road")
                .setCity("Sandton")
                .setProvinceOrState("Gauteng")
                .setCountry("South Africa")
                .setPostalCode("2196")
                .build();
        locations.add(locationService.create(sandton));
        System.out.println("  ✓ Created location: Sandton City Branch");

        // Durban King Shaka Airport
        Location durbanAirport = new Location.Builder()
                .setLocationName("King Shaka International Airport")
                .setStreetNumber(1)
                .setStreetName("King Shaka Drive")
                .setCity("La Mercy")
                .setProvinceOrState("KwaZulu-Natal")
                .setCountry("South Africa")
                .setPostalCode("4405")
                .build();
        locations.add(locationService.create(durbanAirport));
        System.out.println("  ✓ Created location: King Shaka International Airport");

        // Durban Beachfront
        Location durbanBeach = new Location.Builder()
                .setLocationName("Durban Beachfront Branch")
                .setStreetNumber(145)
                .setStreetName("Marine Parade")
                .setCity("Durban")
                .setProvinceOrState("KwaZulu-Natal")
                .setCountry("South Africa")
                .setPostalCode("4001")
                .build();
        locations.add(locationService.create(durbanBeach));
        System.out.println("  ✓ Created location: Durban Beachfront Branch");

        // Pretoria Hatfield
        Location hatfield = new Location.Builder()
                .setLocationName("Hatfield Plaza Branch")
                .setStreetNumber(1122)
                .setStreetName("Burnett Street")
                .setCity("Pretoria")
                .setProvinceOrState("Gauteng")
                .setCountry("South Africa")
                .setPostalCode("0028")
                .build();
        locations.add(locationService.create(hatfield));
        System.out.println("  ✓ Created location: Hatfield Plaza Branch");

        // Stellenbosch Branch
        Location stellenbosch = new Location.Builder()
                .setLocationName("Stellenbosch Town Branch")
                .setStreetNumber(52)
                .setStreetName("Dorp Street")
                .setCity("Stellenbosch")
                .setProvinceOrState("Western Cape")
                .setCountry("South Africa")
                .setPostalCode("7600")
                .build();
        locations.add(locationService.create(stellenbosch));
        System.out.println("  ✓ Created location: Stellenbosch Town Branch");

        return locations;
    }
}
