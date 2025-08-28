package za.co.carhire.controller.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.domain.vehicle.CarType;
import za.co.carhire.service.vehicle.ICarService;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    private Car testCar;
    private Car testCar2;
    private CarType testCarType;

    @BeforeEach
    void setUp() {
        testCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setType("SUV")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Fortuner")
                .setBrand("Toyota")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(1500.00)
                .setCarType(testCarType)
                .build();

        testCar2 = new Car.Builder()
                .setCarID(2)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2023)
                .setAvailability(false)
                .setRentalPrice(800.00)
                .build();
    }

    @Test
    void testCreateCar_Success() throws Exception {
        when(carService.create(any(Car.class))).thenReturn(testCar);

        mockMvc.perform(post("/car/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCar)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carID").value(1))
                .andExpect(jsonPath("$.model").value("Fortuner"))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.year").value(2024))
                .andExpect(jsonPath("$.availability").value(true))
                .andExpect(jsonPath("$.rentalPrice").value(1500.00));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testReadCar_Found() throws Exception {
        when(carService.read(1)).thenReturn(testCar);

        mockMvc.perform(get("/car/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carID").value(1))
                .andExpect(jsonPath("$.model").value("Fortuner"))
                .andExpect(jsonPath("$.brand").value("Toyota"));

        verify(carService, times(1)).read(1);
    }

    @Test
    void testReadCar_NotFound() throws Exception {
        when(carService.read(999)).thenReturn(null);

        mockMvc.perform(get("/car/read/999"))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).read(999);
    }

    @Test
    void testUpdateCar_Success() throws Exception {
        Car updatedCar = new Car.Builder()
                .copy(testCar)
                .setRentalPrice(1600.00)
                .build();

        when(carService.update(any(Car.class))).thenReturn(updatedCar);

        mockMvc.perform(put("/car/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carID").value(1))
                .andExpect(jsonPath("$.rentalPrice").value(1600.00));

        verify(carService, times(1)).update(any(Car.class));
    }

    @Test
    void testUpdateCar_NotFound() throws Exception {
        when(carService.update(any(Car.class))).thenReturn(null);

        mockMvc.perform(put("/car/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCar)))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).update(any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        doNothing().when(carService).delete(1);

        mockMvc.perform(delete("/car/delete/1"))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).delete(1);
    }

    @Test
    void testGetAllCars() throws Exception {
        Set<Car> cars = new HashSet<>();
        cars.add(testCar);
        cars.add(testCar2);

        when(carService.getCars()).thenReturn(cars);

        mockMvc.perform(get("/car/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(carService, times(1)).getCars();
    }

    @Test
    void testGetAllCars_EmptySet() throws Exception {
        when(carService.getCars()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/car/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(carService, times(1)).getCars();
    }

    @Test
    void testGetCarsByBrand() throws Exception {
        List<Car> toyotaCars = Arrays.asList(testCar, testCar2);

        when(carService.getCarsByBrand("Toyota")).thenReturn(toyotaCars);

        mockMvc.perform(get("/car/brand/Toyota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[1].brand").value("Toyota"));

        verify(carService, times(1)).getCarsByBrand("Toyota");
    }

    @Test
    void testGetCarsByBrand_EmptyList() throws Exception {
        when(carService.getCarsByBrand("BMW")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/car/brand/BMW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(carService, times(1)).getCarsByBrand("BMW");
    }

    @Test
    void testGetAvailableCars() throws Exception {
        List<Car> availableCars = Collections.singletonList(testCar);

        when(carService.getAvailableCars()).thenReturn(availableCars);

        mockMvc.perform(get("/car/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].availability").value(true));

        verify(carService, times(1)).getAvailableCars();
    }

    @Test
    void testUpdateAvailability_Success() throws Exception {
        Car updatedCar = new Car.Builder()
                .copy(testCar)
                .setAvailability(false)
                .build();

        when(carService.updateAvailability(1, false)).thenReturn(updatedCar);

        mockMvc.perform(put("/car/availability/1")
                        .param("available", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carID").value(1))
                .andExpect(jsonPath("$.availability").value(false));

        verify(carService, times(1)).updateAvailability(1, false);
    }

    @Test
    void testUpdateAvailability_NotFound() throws Exception {
        when(carService.updateAvailability(999, true)).thenReturn(null);

        mockMvc.perform(put("/car/availability/999")
                        .param("available", "true"))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).updateAvailability(999, true);
    }

    @Test
    void testGetCarsByPriceRange() throws Exception {
        List<Car> carsInRange = Arrays.asList(testCar, testCar2);

        when(carService.getCarsByPriceRange(500.0, 2000.0)).thenReturn(carsInRange);

        mockMvc.perform(get("/car/price-range")
                        .param("minPrice", "500.0")
                        .param("maxPrice", "2000.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(carService, times(1)).getCarsByPriceRange(500.0, 2000.0);
    }

    @Test
    void testGetCarsByPriceRange_EmptyResult() throws Exception {
        when(carService.getCarsByPriceRange(5000.0, 10000.0)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/car/price-range")
                        .param("minPrice", "5000.0")
                        .param("maxPrice", "10000.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(carService, times(1)).getCarsByPriceRange(5000.0, 10000.0);
    }

    @Test
    void testGetCarsByYear() throws Exception {
        List<Car> cars2024 = Collections.singletonList(testCar);

        when(carService.getCarsByYear(2024)).thenReturn(cars2024);

        mockMvc.perform(get("/car/year/2024"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].year").value(2024));

        verify(carService, times(1)).getCarsByYear(2024);
    }

    @Test
    void testGetCarsByYear_NoResults() throws Exception {
        when(carService.getCarsByYear(2020)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/car/year/2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(carService, times(1)).getCarsByYear(2020);
    }

    @Test
    void testCorsHeaders() throws Exception {
        when(carService.getCars()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/car/all")
                        .header("Origin", "http://localhost:3046"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3046"));
    }

    @Test
    void testInvalidRequestBody() throws Exception {
        mockMvc.perform(post("/car/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMissingRequestParam() throws Exception {
        mockMvc.perform(get("/car/price-range")
                        .param("minPrice", "500.0"))
                .andExpect(status().isBadRequest());
    }
}