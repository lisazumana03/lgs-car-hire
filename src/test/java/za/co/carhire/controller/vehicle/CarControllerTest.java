package za.co.carhire.controller.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.dto.vehicle.CarDTO;
import za.co.carhire.service.vehicle.ICarService;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
@Import(CarControllerTest.TestConfig.class)
public class CarControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public ICarService carService() {
            return Mockito.mock(ICarService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ICarService carService;

    private Car testCar;
    private CarDTO testCarDTO;
    private CarType testCarType;

    @BeforeEach
    void setUp() {
        // Reset the mock before each test
        Mockito.reset(carService);

        testCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setType("Sedan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2022)
                .setAvailability(true)
                .setRentalPrice(500.0)
                .setImageUrl("http://example.com/image.jpg")
                .setCarType(testCarType)
                .build();

        testCarDTO = new CarDTO.Builder()
                .setCarID(1)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2022)
                .setAvailability(true)
                .setRentalPrice(500.0)
                .setImageUrl("http://example.com/image.jpg")
                .setCarTypeID(1)
                .setCarTypeName("Sedan")
                .setCarTypeFuelType("Petrol")
                .setCarTypeNumberOfWheels(4)
                .setCarTypeNumberOfSeats(5)
                .build();
    }

    @Test
    void testCreateCar_Success() throws Exception {
        when(carService.create(any(Car.class))).thenReturn(testCar);

        mockMvc.perform(post("/api/car/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carID").value(1))
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.year").value(2022))
                .andExpect(jsonPath("$.availability").value(true))
                .andExpect(jsonPath("$.rentalPrice").value(500.0));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testReadCar_Found() throws Exception {
        when(carService.read(1)).thenReturn(testCar);

        mockMvc.perform(get("/api/car/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carID").value(1))
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.brand").value("Toyota"));

        verify(carService, times(1)).read(1);
    }

    @Test
    void testReadCar_NotFound() throws Exception {
        when(carService.read(999)).thenReturn(null);

        mockMvc.perform(get("/api/car/read/999"))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).read(999);
    }

    @Test
    void testUpdateCar_Success() throws Exception {
        testCarDTO.setModel("Camry");
        Car updatedCar = new Car.Builder()
                .copy(testCar)
                .setModel("Camry")
                .build();

        when(carService.read(1)).thenReturn(testCar);
        when(carService.update(any(Car.class))).thenReturn(updatedCar);

        mockMvc.perform(put("/api/car/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Camry"));

        verify(carService, times(1)).read(1);
        verify(carService, times(1)).update(any(Car.class));
    }

    @Test
    void testUpdateCar_NotFound() throws Exception {
        when(carService.read(999)).thenReturn(null);

        testCarDTO.setCarID(999);
        mockMvc.perform(put("/api/car/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarDTO)))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).read(999);
        verify(carService, never()).update(any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        doNothing().when(carService).delete(1);

        mockMvc.perform(delete("/api/car/delete/1"))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).delete(1);
    }

    @Test
    void testGetAllCars() throws Exception {
        Set<Car> cars = new HashSet<>();
        cars.add(testCar);
        Car car2 = new Car.Builder()
                .setCarID(2)
                .setModel("Civic")
                .setBrand("Honda")
                .setYear(2023)
                .setAvailability(true)
                .setRentalPrice(550.0)
                .build();
        cars.add(car2);

        when(carService.getCars()).thenReturn(cars);

        mockMvc.perform(get("/api/car/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].carID", containsInAnyOrder(1, 2)));

        verify(carService, times(1)).getCars();
    }

    @Test
    void testGetCarsByBrand() throws Exception {
        List<Car> toyotaCars = Arrays.asList(testCar);
        when(carService.getCarsByBrand("Toyota")).thenReturn(toyotaCars);

        mockMvc.perform(get("/api/car/brand/Toyota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brand").value("Toyota"));

        verify(carService, times(1)).getCarsByBrand("Toyota");
    }

    @Test
    void testGetAvailableCars() throws Exception {
        List<Car> availableCars = Arrays.asList(testCar);
        when(carService.getAvailableCars()).thenReturn(availableCars);

        mockMvc.perform(get("/api/car/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].availability").value(true));

        verify(carService, times(1)).getAvailableCars();
    }

    @Test
    void testUpdateAvailability_Success() throws Exception {
        testCar.setAvailability(false);
        when(carService.updateAvailability(1, false)).thenReturn(testCar);

        mockMvc.perform(put("/api/car/availability/1")
                        .param("available", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availability").value(false));

        verify(carService, times(1)).updateAvailability(1, false);
    }

    @Test
    void testUpdateAvailability_NotFound() throws Exception {
        when(carService.updateAvailability(999, false)).thenReturn(null);

        mockMvc.perform(put("/api/car/availability/999")
                        .param("available", "false"))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).updateAvailability(999, false);
    }

    @Test
    void testGetCarsByPriceRange() throws Exception {
        List<Car> carsInRange = Arrays.asList(testCar);
        when(carService.getCarsByPriceRange(400.0, 600.0)).thenReturn(carsInRange);

        mockMvc.perform(get("/api/car/price-range")
                        .param("minPrice", "400.0")
                        .param("maxPrice", "600.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].rentalPrice").value(500.0));

        verify(carService, times(1)).getCarsByPriceRange(400.0, 600.0);
    }

    @Test
    void testGetCarsByYear() throws Exception {
        List<Car> cars2022 = Arrays.asList(testCar);
        when(carService.getCarsByYear(2022)).thenReturn(cars2022);

        mockMvc.perform(get("/api/car/year/2022"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].year").value(2022));

        verify(carService, times(1)).getCarsByYear(2022);
    }

    @Test
    void testGetCarsByPriceRange_EmptyResult() throws Exception {
        when(carService.getCarsByPriceRange(1000.0, 2000.0)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/car/price-range")
                        .param("minPrice", "1000.0")
                        .param("maxPrice", "2000.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(carService, times(1)).getCarsByPriceRange(1000.0, 2000.0);
    }

    @Test
    void testGetAvailableCars_MultipleResults() throws Exception {
        Car car2 = new Car.Builder()
                .setCarID(2)
                .setModel("Civic")
                .setBrand("Honda")
                .setYear(2023)
                .setAvailability(true)
                .setRentalPrice(550.0)
                .build();

        List<Car> availableCars = Arrays.asList(testCar, car2);
        when(carService.getAvailableCars()).thenReturn(availableCars);

        mockMvc.perform(get("/api/car/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].availability", everyItem(equalTo(true))));

        verify(carService, times(1)).getAvailableCars();
    }
}