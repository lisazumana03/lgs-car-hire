package za.co.carhire.controller.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LocationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static Location location;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/location";
    }

    @BeforeEach
    void setUp() {
        int locationID = 1;
        String locationName = "Eden Rentals";
        int streetNumber = 1;
        String streetName = "Main Road";
        String cityOrTown = "Wynberg, Cape Town";
        String provinceOrState = "Western Cape";
        String country = "South Africa";
        String postalCode = "12345";
        String fullAddress = "";

        List<Booking> pickUpBookings = new ArrayList<>();
        List<Booking> dropOffBookings = new ArrayList<>();

        location = LocationFactory.createLocation(locationID, locationName, streetNumber, streetName, cityOrTown, provinceOrState, country, postalCode, pickUpBookings, dropOffBookings);
    }

    @Test
    void create() {
        String url = getBaseUrl() + "/create";
        System.out.println("Post data: " + location);

        ResponseEntity<Location> response = restTemplate.postForEntity(url, location, Location.class);

        System.out.println("Response: " + response.getBody());

        assertNotNull(response.getBody());
        assertEquals(location.getLocationID(), response.getBody().getLocationID());
    }

    @Test
    void read() {
        String url = getBaseUrl() + "/read/" + location.getLocationID();
        ResponseEntity<Location> response = restTemplate.getForEntity(url, Location.class);

        assertNotNull(response.getBody());
        assertEquals(location.getLocationID(), response.getBody().getLocationID());
    }

    @Test
    void update() {
        String url = getBaseUrl() + "/update";
        ResponseEntity<Location> response = restTemplate.postForEntity(url, location, Location.class);

        assertNotNull(response.getBody());
        assertEquals(location.getLocationID(), response.getBody().getLocationID());
    }

    @Test
    void delete() {
        String url = "delete/"+location.getLocationID();
        System.out.println("URL: "+url);
        restTemplate.delete(url);
    }
}
