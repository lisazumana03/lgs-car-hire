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
import za.co.carhire.domain.vehicle.*;
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
                .setCategory(VehicleCategory.SEDAN)
                .setFuelType(FuelType.PETROL)
                .setTransmissionType(TransmissionType.AUTOMATIC)
                .setNumberOfSeats(5)
                .setNumberOfDoors(4)
                .setAirConditioned(true)
                .setLuggageCapacity(3)
                .setDescription("Test sedan for unit tests")
                .build();

        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2022)
                .setLicensePlate("ABC 123 GP")
                .setVin("TOY22123456789012")
                .setColor("White")
                .setMileage(45000)
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(CarCondition.GOOD)
                .setImageData("test-image-data".getBytes())
                .setImageName("image.jpg")
                .setImageType("image/jpeg")
                .setCarType(testCarType)
                .build();

        testCarDTO = new CarDTO.Builder()
                .setCarID(1)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2022)
                .setLicensePlate("ABC 123 GP")
                .setVin("TOY22123456789012")
                .setColor("White")
                .setMileage(45000)
                .setStatus("AVAILABLE")
                .setCondition("GOOD")
                .setImageBase64("dGVzdC1pbWFnZS1kYXRh")
                .setImageName("image.jpg")
                .setImageType("image/jpeg")
                .setCarTypeID(1)
                .setCarTypeCategory("SEDAN")
                .setCarTypeFuelType("PETROL")
                .setCarTypeTransmissionType("AUTOMATIC")
                .setCarTypeNumberOfSeats(5)
                .setCarTypeNumberOfDoors(4)
                .setCarTypeAirConditioned(true)
                .setCarTypeLuggageCapacity(3)
                .setCarTypeDescription("Test sedan for unit tests")
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
                .andExpect(jsonPath("$.licensePlate").value("ABC 123 GP"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));

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
                .setLicensePlate("DEF 456 GP")
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(CarCondition.GOOD)
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
                .andExpect(jsonPath("$[0].status").value("AVAILABLE"));

        verify(carService, times(1)).getAvailableCars();
    }

    @Test
    void testUpdateStatus_Success() throws Exception {
        testCar.setStatus(CarStatus.MAINTENANCE);
        when(carService.updateStatus(1, CarStatus.MAINTENANCE)).thenReturn(testCar);

        mockMvc.perform(put("/api/car/status/1")
                        .param("status", "MAINTENANCE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("MAINTENANCE"));

        verify(carService, times(1)).updateStatus(1, CarStatus.MAINTENANCE);
    }

    @Test
    void testUpdateStatus_NotFound() throws Exception {
        when(carService.updateStatus(999, CarStatus.MAINTENANCE)).thenReturn(null);

        mockMvc.perform(put("/api/car/status/999")
                        .param("status", "MAINTENANCE"))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).updateStatus(999, CarStatus.MAINTENANCE);
    }

    @Test
    void testUpdateMileage_Success() throws Exception {
        testCar.setMileage(55000);
        when(carService.updateMileage(1, 55000)).thenReturn(testCar);

        mockMvc.perform(put("/api/car/mileage/1")
                        .param("mileage", "55000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mileage").value(55000));

        verify(carService, times(1)).updateMileage(1, 55000);
    }

    @Test
    void testUpdateMileage_NotFound() throws Exception {
        when(carService.updateMileage(999, 55000)).thenReturn(null);

        mockMvc.perform(put("/api/car/mileage/999")
                        .param("mileage", "55000"))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).updateMileage(999, 55000);
    }

    @Test
    void testGetCarsByStatus() throws Exception {
        List<Car> availableCars = Arrays.asList(testCar);
        when(carService.getCarsByStatus(CarStatus.AVAILABLE)).thenReturn(availableCars);

        mockMvc.perform(get("/api/car/status/AVAILABLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status").value("AVAILABLE"));

        verify(carService, times(1)).getCarsByStatus(CarStatus.AVAILABLE);
    }

    @Test
    void testGetCarByLicensePlate_Found() throws Exception {
        when(carService.getCarByLicensePlate("ABC 123 GP")).thenReturn(testCar);

        mockMvc.perform(get("/api/car/license-plate/ABC 123 GP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate").value("ABC 123 GP"))
                .andExpect(jsonPath("$.model").value("Corolla"));

        verify(carService, times(1)).getCarByLicensePlate("ABC 123 GP");
    }

    @Test
    void testGetCarByLicensePlate_NotFound() throws Exception {
        when(carService.getCarByLicensePlate("XYZ 999 GP")).thenReturn(null);

        mockMvc.perform(get("/api/car/license-plate/XYZ 999 GP"))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).getCarByLicensePlate("XYZ 999 GP");
    }

    @Test
    void testGetCarByVin_Found() throws Exception {
        when(carService.getCarByVin("TOY22123456789012")).thenReturn(testCar);

        mockMvc.perform(get("/api/car/vin/TOY22123456789012"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vin").value("TOY22123456789012"))
                .andExpect(jsonPath("$.model").value("Corolla"));

        verify(carService, times(1)).getCarByVin("TOY22123456789012");
    }

    @Test
    void testGetCarByVin_NotFound() throws Exception {
        when(carService.getCarByVin("INVALID123456789")).thenReturn(null);

        mockMvc.perform(get("/api/car/vin/INVALID123456789"))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).getCarByVin("INVALID123456789");
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
    void testGetCarsByYear_EmptyResult() throws Exception {
        when(carService.getCarsByYear(2020)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/car/year/2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(carService, times(1)).getCarsByYear(2020);
    }

    @Test
    void testGetAvailableCars_MultipleResults() throws Exception {
        Car car2 = new Car.Builder()
                .setCarID(2)
                .setModel("Civic")
                .setBrand("Honda")
                .setYear(2023)
                .setLicensePlate("DEF 456 GP")
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(CarCondition.GOOD)
                .build();

        List<Car> availableCars = Arrays.asList(testCar, car2);
        when(carService.getAvailableCars()).thenReturn(availableCars);

        mockMvc.perform(get("/api/car/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].status", everyItem(equalTo("AVAILABLE"))));

        verify(carService, times(1)).getAvailableCars();
    }
}