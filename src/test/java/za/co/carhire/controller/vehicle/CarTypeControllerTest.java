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
import za.co.carhire.dto.vehicle.CarTypeDTO;
import za.co.carhire.service.vehicle.ICarTypeService;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarTypeController.class)
@Import(CarTypeControllerTest.TestConfig.class)
public class CarTypeControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public ICarTypeService carTypeService() {
            return Mockito.mock(ICarTypeService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ICarTypeService carTypeService;

    private CarType testCarType;
    private CarTypeDTO testCarTypeDTO;
    private Car testCar;

    @BeforeEach
    void setUp() {
        // Reset the mock before each test
        Mockito.reset(carTypeService);

        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Corolla")
                .setBrand("Toyota")
                .setYear(2022)
                .setAvailability(true)
                .setRentalPrice(500.0)
                .build();

        testCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setType("Sedan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .setCar(testCar)
                .build();

        testCarTypeDTO = new CarTypeDTO.Builder()
                .setCarTypeID(1)
                .setType("Sedan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .setCarID(1)
                .setCarBrandModel("Toyota Corolla")
                .build();
    }

    @Test
    void testCreateCarType_Success() throws Exception {
        when(carTypeService.create(any(CarType.class))).thenReturn(testCarType);

        mockMvc.perform(post("/api/cartype/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarTypeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carTypeID").value(1))
                .andExpect(jsonPath("$.type").value("Sedan"))
                .andExpect(jsonPath("$.fuelType").value("Petrol"))
                .andExpect(jsonPath("$.numberOfWheels").value(4))
                .andExpect(jsonPath("$.numberOfSeats").value(5));

        verify(carTypeService, times(1)).create(any(CarType.class));
    }

    @Test
    void testReadCarType_Found() throws Exception {
        when(carTypeService.read(1)).thenReturn(testCarType);

        mockMvc.perform(get("/api/cartype/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carTypeID").value(1))
                .andExpect(jsonPath("$.type").value("Sedan"))
                .andExpect(jsonPath("$.fuelType").value("Petrol"));

        verify(carTypeService, times(1)).read(1);
    }

    @Test
    void testReadCarType_NotFound() throws Exception {
        when(carTypeService.read(999)).thenReturn(null);

        mockMvc.perform(get("/api/cartype/read/999"))
                .andExpect(status().isNotFound());

        verify(carTypeService, times(1)).read(999);
    }

    @Test
    void testUpdateCarType_Success() throws Exception {
        testCarTypeDTO.setType("SUV");
        CarType updatedCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setType("SUV")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .setCar(testCar)
                .build();

        when(carTypeService.read(1)).thenReturn(testCarType);
        when(carTypeService.update(any(CarType.class))).thenReturn(updatedCarType);

        mockMvc.perform(put("/api/cartype/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("SUV"));

        verify(carTypeService, times(1)).read(1);
        verify(carTypeService, times(1)).update(any(CarType.class));
    }

    @Test
    void testUpdateCarType_NotFound() throws Exception {
        when(carTypeService.read(999)).thenReturn(null);

        testCarTypeDTO.setCarTypeID(999);
        mockMvc.perform(put("/api/cartype/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarTypeDTO)))
                .andExpect(status().isNotFound());

        verify(carTypeService, times(1)).read(999);
        verify(carTypeService, never()).update(any(CarType.class));
    }

    @Test
    void testDeleteCarType() throws Exception {
        doNothing().when(carTypeService).delete(1);

        mockMvc.perform(delete("/api/cartype/delete/1"))
                .andExpect(status().isNoContent());

        verify(carTypeService, times(1)).delete(1);
    }

    @Test
    void testGetAllCarTypes() throws Exception {
        Set<CarType> carTypes = new HashSet<>();
        carTypes.add(testCarType);

        CarType suvType = new CarType.Builder()
                .setCarTypeID(2)
                .setType("SUV")
                .setFuelType("Diesel")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();
        carTypes.add(suvType);

        when(carTypeService.getCarTypes()).thenReturn(carTypes);

        mockMvc.perform(get("/api/cartype/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].carTypeID", containsInAnyOrder(1, 2)));

        verify(carTypeService, times(1)).getCarTypes();
    }

    @Test
    void testGetCarTypesByFuelType() throws Exception {
        List<CarType> petrolTypes = Arrays.asList(testCarType);
        when(carTypeService.getCarTypesByFuelType("Petrol")).thenReturn(petrolTypes);

        mockMvc.perform(get("/api/cartype/fuel/Petrol"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].fuelType").value("Petrol"));

        verify(carTypeService, times(1)).getCarTypesByFuelType("Petrol");
    }

    @Test
    void testGetCarTypesByFuelType_EmptyResult() throws Exception {
        when(carTypeService.getCarTypesByFuelType("Hydrogen")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/cartype/fuel/Hydrogen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(carTypeService, times(1)).getCarTypesByFuelType("Hydrogen");
    }

    @Test
    void testGetCarTypesByFuelType_MultipleResults() throws Exception {
        CarType electricType = new CarType.Builder()
                .setCarTypeID(2)
                .setType("Electric")
                .setFuelType("Electric")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        CarType hybridElectric = new CarType.Builder()
                .setCarTypeID(3)
                .setType("Hybrid")
                .setFuelType("Electric")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        List<CarType> electricTypes = Arrays.asList(electricType, hybridElectric);
        when(carTypeService.getCarTypesByFuelType("Electric")).thenReturn(electricTypes);

        mockMvc.perform(get("/api/cartype/fuel/Electric"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].fuelType", everyItem(equalTo("Electric"))));

        verify(carTypeService, times(1)).getCarTypesByFuelType("Electric");
    }

    @Test
    void testCreateCarType_WithoutCar() throws Exception {
        CarType simpleCarType = new CarType.Builder()
                .setCarTypeID(10)
                .setType("Compact")
                .setFuelType("Hybrid")
                .setNumberOfWheels(4)
                .setNumberOfSeats(4)
                .build();

        CarTypeDTO simpleDTO = new CarTypeDTO.Builder()
                .setCarTypeID(10)
                .setType("Compact")
                .setFuelType("Hybrid")
                .setNumberOfWheels(4)
                .setNumberOfSeats(4)
                .build();

        when(carTypeService.create(any(CarType.class))).thenReturn(simpleCarType);

        mockMvc.perform(post("/api/cartype/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(simpleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carTypeID").value(10))
                .andExpect(jsonPath("$.type").value("Compact"))
                .andExpect(jsonPath("$.carID").doesNotExist());

        verify(carTypeService, times(1)).create(any(CarType.class));
    }

    @Test
    void testUpdateCarType_ChangeFuelType() throws Exception {
        testCarTypeDTO.setFuelType("Diesel");
        CarType updatedCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setType("Sedan")
                .setFuelType("Diesel")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .setCar(testCar)
                .build();

        when(carTypeService.read(1)).thenReturn(testCarType);
        when(carTypeService.update(any(CarType.class))).thenReturn(updatedCarType);

        mockMvc.perform(put("/api/cartype/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fuelType").value("Diesel"))
                .andExpect(jsonPath("$.type").value("Sedan"));

        verify(carTypeService, times(1)).update(any(CarType.class));
    }
}