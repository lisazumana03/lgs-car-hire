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
import za.co.carhire.service.vehicle.ICarTypeService;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarTypeController.class)
public class CarTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICarTypeService carTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    private CarType testCarType;
    private CarType testCarType2;
    private CarType testCarType3;
    private Car testCar;

    @BeforeEach
    void setUp() {
        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Fortuner")
                .setBrand("Toyota")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(1500.00)
                .build();

        testCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setCar(testCar)
                .setType("SUV")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        testCarType2 = new CarType.Builder()
                .setCarTypeID(2)
                .setType("Sedan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();

        testCarType3 = new CarType.Builder()
                .setCarTypeID(3)
                .setType("Electric")
                .setFuelType("Electric")
                .setNumberOfWheels(4)
                .setNumberOfSeats(5)
                .build();
    }

    @Test
    void testCreateCarType_Success() throws Exception {
        when(carTypeService.create(any(CarType.class))).thenReturn(testCarType);

        mockMvc.perform(post("/cartype/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarType)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carTypeID").value(1))
                .andExpect(jsonPath("$.type").value("SUV"))
                .andExpect(jsonPath("$.fuelType").value("Petrol"))
                .andExpect(jsonPath("$.numberOfWheels").value(4))
                .andExpect(jsonPath("$.numberOfSeats").value(7));

        verify(carTypeService, times(1)).create(any(CarType.class));
    }

    @Test
    void testCreateCarType_WithoutCar() throws Exception {
        when(carTypeService.create(any(CarType.class))).thenReturn(testCarType2);

        mockMvc.perform(post("/cartype/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarType2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carTypeID").value(2))
                .andExpect(jsonPath("$.type").value("Sedan"))
                .andExpect(jsonPath("$.fuelType").value("Petrol"));

        verify(carTypeService, times(1)).create(any(CarType.class));
    }

    @Test
    void testReadCarType_Found() throws Exception {
        when(carTypeService.read(1)).thenReturn(testCarType);

        mockMvc.perform(get("/cartype/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carTypeID").value(1))
                .andExpect(jsonPath("$.type").value("SUV"))
                .andExpect(jsonPath("$.fuelType").value("Petrol"))
                .andExpect(jsonPath("$.numberOfWheels").value(4))
                .andExpect(jsonPath("$.numberOfSeats").value(7));

        verify(carTypeService, times(1)).read(1);
    }

    @Test
    void testReadCarType_NotFound() throws Exception {
        when(carTypeService.read(999)).thenReturn(null);

        mockMvc.perform(get("/cartype/read/999"))
                .andExpect(status().isNotFound());

        verify(carTypeService, times(1)).read(999);
    }

    @Test
    void testUpdateCarType_Success() throws Exception {
        CarType updatedCarType = new CarType.Builder()
                .setCarTypeID(1)
                .setCar(testCar)
                .setType("Luxury SUV")
                .setFuelType("Hybrid")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        when(carTypeService.update(any(CarType.class))).thenReturn(updatedCarType);

        mockMvc.perform(put("/cartype/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCarType)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carTypeID").value(1))
                .andExpect(jsonPath("$.type").value("Luxury SUV"))
                .andExpect(jsonPath("$.fuelType").value("Hybrid"));

        verify(carTypeService, times(1)).update(any(CarType.class));
    }

    @Test
    void testUpdateCarType_NotFound() throws Exception {
        when(carTypeService.update(any(CarType.class))).thenReturn(null);

        mockMvc.perform(put("/cartype/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCarType)))
                .andExpect(status().isNotFound());

        verify(carTypeService, times(1)).update(any(CarType.class));
    }

    @Test
    void testUpdateCarType_ChangeSeats() throws Exception {
        CarType updatedCarType = new CarType.Builder()
                .setCarTypeID(2)
                .setType("Sedan")
                .setFuelType("Petrol")
                .setNumberOfWheels(4)
                .setNumberOfSeats(4)
                .build();

        when(carTypeService.update(any(CarType.class))).thenReturn(updatedCarType);

        mockMvc.perform(put("/cartype/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCarType)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfSeats").value(4));

        verify(carTypeService, times(1)).update(any(CarType.class));
    }

    @Test
    void testDeleteCarType() throws Exception {
        doNothing().when(carTypeService).delete(1);

        mockMvc.perform(delete("/cartype/delete/1"))
                .andExpect(status().isNoContent());

        verify(carTypeService, times(1)).delete(1);
    }

    @Test
    void testDeleteCarType_NonExistent() throws Exception {
        doNothing().when(carTypeService).delete(999);

        mockMvc.perform(delete("/cartype/delete/999"))
                .andExpect(status().isNoContent());

        verify(carTypeService, times(1)).delete(999);
    }

    @Test
    void testGetAllCarTypes() throws Exception {
        Set<CarType> carTypes = new HashSet<>();
        carTypes.add(testCarType);
        carTypes.add(testCarType2);
        carTypes.add(testCarType3);

        when(carTypeService.getCarTypes()).thenReturn(carTypes);

        mockMvc.perform(get("/cartype/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        verify(carTypeService, times(1)).getCarTypes();
    }

    @Test
    void testGetAllCarTypes_EmptySet() throws Exception {
        when(carTypeService.getCarTypes()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/cartype/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(carTypeService, times(1)).getCarTypes();
    }

    @Test
    void testGetAllCarTypes_SingleElement() throws Exception {
        Set<CarType> carTypes = new HashSet<>();
        carTypes.add(testCarType);

        when(carTypeService.getCarTypes()).thenReturn(carTypes);

        mockMvc.perform(get("/cartype/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type").value("SUV"));

        verify(carTypeService, times(1)).getCarTypes();
    }

    @Test
    void testCorsHeaders() throws Exception {
        when(carTypeService.getCarTypes()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/cartype/all")
                        .header("Origin", "http://localhost:3046"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3046"));
    }

    @Test
    void testInvalidRequestBody() throws Exception {
        mockMvc.perform(post("/cartype/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEmptyRequestBody() throws Exception {
        mockMvc.perform(post("/cartype/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidPathVariable() throws Exception {
        mockMvc.perform(get("/cartype/read/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateMotorcycleType() throws Exception {
        CarType motorcycle = new CarType.Builder()
                .setCarTypeID(4)
                .setType("Motorcycle")
                .setFuelType("Petrol")
                .setNumberOfWheels(2) 
                .setNumberOfSeats(2)
                .build();

        when(carTypeService.create(any(CarType.class))).thenReturn(motorcycle);

        mockMvc.perform(post("/cartype/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(motorcycle)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("Motorcycle"))
                .andExpect(jsonPath("$.numberOfWheels").value(2));

        verify(carTypeService, times(1)).create(any(CarType.class));
    }

    @Test
    void testPartialUpdateCarType() throws Exception {
        CarType partialUpdate = new CarType.Builder()
                .setCarTypeID(1)
                .setType("SUV")
                .setFuelType("Diesel")
                .setNumberOfWheels(4)
                .setNumberOfSeats(7)
                .build();

        when(carTypeService.update(any(CarType.class))).thenReturn(partialUpdate);

        mockMvc.perform(put("/cartype/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partialUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fuelType").value("Diesel"))
                .andExpect(jsonPath("$.type").value("SUV"));

        verify(carTypeService, times(1)).update(any(CarType.class));
    }

    @Test
    void testContentType() throws Exception {
        when(carTypeService.getCarTypes()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/cartype/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}