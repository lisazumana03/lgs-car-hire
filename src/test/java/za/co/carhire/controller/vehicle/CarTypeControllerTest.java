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
                .setLicensePlate("ABC 123 GP")
                .setStatus(CarStatus.AVAILABLE)
                .setCondition(CarCondition.GOOD)
                .build();

        testCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setCategory(VehicleCategory.SEDAN)
                .setFuelType(FuelType.PETROL)
                .setTransmissionType(TransmissionType.AUTOMATIC)
                .setNumberOfSeats(5)
                .setNumberOfDoors(4)
                .setAirConditioned(true)
                .setLuggageCapacity(3)
                .setDescription("Comfortable mid-size sedan")
                .build();

        testCarTypeDTO = new CarTypeDTO.Builder()
                .setCarTypeID(1)
                .setCategory("SEDAN")
                .setFuelType("PETROL")
                .setTransmissionType("AUTOMATIC")
                .setNumberOfSeats(5)
                .setNumberOfDoors(4)
                .setAirConditioned(true)
                .setLuggageCapacity(3)
                .setDescription("Comfortable mid-size sedan")
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
                .andExpect(jsonPath("$.category").value("SEDAN"))
                .andExpect(jsonPath("$.fuelType").value("PETROL"))
                .andExpect(jsonPath("$.transmissionType").value("AUTOMATIC"))
                .andExpect(jsonPath("$.numberOfSeats").value(5))
                .andExpect(jsonPath("$.numberOfDoors").value(4));

        verify(carTypeService, times(1)).create(any(CarType.class));
    }

    @Test
    void testReadCarType_Found() throws Exception {
        when(carTypeService.read(1)).thenReturn(testCarType);

        mockMvc.perform(get("/api/cartype/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carTypeID").value(1))
                .andExpect(jsonPath("$.category").value("SEDAN"))
                .andExpect(jsonPath("$.fuelType").value("PETROL"));

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
        testCarTypeDTO.setCategory("SUV");
        CarType updatedCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setCategory(VehicleCategory.SUV)
                .setFuelType(FuelType.PETROL)
                .setTransmissionType(TransmissionType.AUTOMATIC)
                .setNumberOfSeats(7)
                .setNumberOfDoors(5)
                .setAirConditioned(true)
                .setLuggageCapacity(5)
                .setDescription("Spacious SUV")
                .build();

        when(carTypeService.read(1)).thenReturn(testCarType);
        when(carTypeService.update(any(CarType.class))).thenReturn(updatedCarType);

        mockMvc.perform(put("/api/cartype/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("SUV"));

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
                .setCategory(VehicleCategory.SUV)
                .setFuelType(FuelType.DIESEL)
                .setTransmissionType(TransmissionType.AUTOMATIC)
                .setNumberOfSeats(7)
                .setNumberOfDoors(5)
                .setAirConditioned(true)
                .setLuggageCapacity(5)
                .setDescription("Spacious SUV")
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
        when(carTypeService.getCarTypesByFuelType(FuelType.PETROL)).thenReturn(petrolTypes);

        mockMvc.perform(get("/api/cartype/fuel/PETROL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].fuelType").value("PETROL"));

        verify(carTypeService, times(1)).getCarTypesByFuelType(FuelType.PETROL);
    }

    @Test
    void testGetCarTypesByFuelType_EmptyResult() throws Exception {
        when(carTypeService.getCarTypesByFuelType(FuelType.CNG)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/cartype/fuel/CNG"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(carTypeService, times(1)).getCarTypesByFuelType(FuelType.CNG);
    }

    @Test
    void testGetCarTypesByFuelType_MultipleResults() throws Exception {
        CarType electricType = new CarType.Builder()
                .setCarTypeID(2)
                .setCategory(VehicleCategory.ELECTRIC)
                .setFuelType(FuelType.ELECTRIC)
                .setTransmissionType(TransmissionType.AUTOMATIC)
                .setNumberOfSeats(5)
                .setNumberOfDoors(4)
                .setAirConditioned(true)
                .setLuggageCapacity(3)
                .setDescription("Electric vehicle")
                .build();

        CarType hybridElectric = new CarType.Builder()
                .setCarTypeID(3)
                .setCategory(VehicleCategory.HYBRID)
                .setFuelType(FuelType.HYBRID)
                .setTransmissionType(TransmissionType.CVT)
                .setNumberOfSeats(5)
                .setNumberOfDoors(4)
                .setAirConditioned(true)
                .setLuggageCapacity(3)
                .setDescription("Hybrid vehicle")
                .build();

        List<CarType> electricTypes = Arrays.asList(electricType, hybridElectric);
        when(carTypeService.getCarTypesByFuelType(FuelType.ELECTRIC)).thenReturn(electricTypes);

        mockMvc.perform(get("/api/cartype/fuel/ELECTRIC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(carTypeService, times(1)).getCarTypesByFuelType(FuelType.ELECTRIC);
    }

    @Test
    void testCreateCarType_WithoutCar() throws Exception {
        CarType simpleCarType = new CarType.Builder()
                .setCarTypeID(10)
                .setCategory(VehicleCategory.COMPACT)
                .setFuelType(FuelType.HYBRID)
                .setTransmissionType(TransmissionType.AUTOMATIC)
                .setNumberOfSeats(4)
                .setNumberOfDoors(4)
                .setAirConditioned(true)
                .setLuggageCapacity(2)
                .setDescription("Compact hybrid")
                .build();

        CarTypeDTO simpleDTO = new CarTypeDTO.Builder()
                .setCarTypeID(10)
                .setCategory("COMPACT")
                .setFuelType("HYBRID")
                .setTransmissionType("AUTOMATIC")
                .setNumberOfSeats(4)
                .setNumberOfDoors(4)
                .setAirConditioned(true)
                .setLuggageCapacity(2)
                .setDescription("Compact hybrid")
                .build();

        when(carTypeService.create(any(CarType.class))).thenReturn(simpleCarType);

        mockMvc.perform(post("/api/cartype/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(simpleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carTypeID").value(10))
                .andExpect(jsonPath("$.category").value("COMPACT"))
                .andExpect(jsonPath("$.carID").doesNotExist());

        verify(carTypeService, times(1)).create(any(CarType.class));
    }

    @Test
    void testUpdateCarType_ChangeFuelType() throws Exception {
        testCarTypeDTO.setFuelType("DIESEL");
        CarType updatedCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setCategory(VehicleCategory.SEDAN)
                .setFuelType(FuelType.DIESEL)
                .setTransmissionType(TransmissionType.AUTOMATIC)
                .setNumberOfSeats(5)
                .setNumberOfDoors(4)
                .setAirConditioned(true)
                .setLuggageCapacity(3)
                .setDescription("Comfortable mid-size sedan")
                .build();

        when(carTypeService.read(1)).thenReturn(testCarType);
        when(carTypeService.update(any(CarType.class))).thenReturn(updatedCarType);

        mockMvc.perform(put("/api/cartype/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fuelType").value("DIESEL"))
                .andExpect(jsonPath("$.category").value("SEDAN"));

        verify(carTypeService, times(1)).update(any(CarType.class));
    }
}