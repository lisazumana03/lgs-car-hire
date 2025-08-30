package za.co.carhire.controller.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.co.carhire.domain.reservation.Insurance;
import za.co.carhire.domain.vehicle.Car;
import za.co.carhire.dto.reservation.InsuranceDTO;
import za.co.carhire.service.reservation.IInsuranceService;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InsuranceController.class)
public class InsuranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IInsuranceService insuranceService;

    @Autowired
    private ObjectMapper objectMapper;

    private Insurance testInsurance1;
    private Insurance testInsurance2;
    private InsuranceDTO testInsuranceDTO;
    private Car testCar;
    private Date testDate;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        testDate = dateFormat.parse("2025-08-30");

        testCar = new Car.Builder()
                .setCarID(1)
                .setModel("Fortuner")
                .setBrand("Toyota")
                .setYear(2024)
                .setAvailability(true)
                .setRentalPrice(1500.00)
                .build();

        testInsurance1 = new Insurance.Builder()
                .setInsuranceID(1)
                .setInsuranceStartDate(testDate)
                .setInsuranceCost(350.00)
                .setInsuranceProvider("CTU Insurance")
                .setStatus("ACTIVE")
                .setPolicyNumber(12345L)
                .setMechanic("John Doe")
                .build();

        testInsurance2 = new Insurance.Builder()
                .setInsuranceID(2)
                .setInsuranceStartDate(testDate)
                .setInsuranceCost(450.00)
                .setInsuranceProvider("SafeDrive")
                .setStatus("CANCELLED")
                .setPolicyNumber(67890L)
                .setMechanic("Jane Smith")
                .setCar(testCar)
                .build();

        testInsuranceDTO = new InsuranceDTO.Builder()
                .setInsuranceID(1)
                .setInsuranceStartDate(testDate)
                .setInsuranceCost(350.00)
                .setInsuranceProvider("CTU Insurance")
                .setStatus("ACTIVE")
                .setPolicyNumber(12345L)
                .setMechanic("John Doe")
                .build();
    }

    @Test
    void testCreateInsurance_Success() throws Exception {
        when(insuranceService.create(any(Insurance.class))).thenReturn(testInsurance1);

        mockMvc.perform(post("/api/insurance/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testInsuranceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.insuranceID").value(1))
                .andExpect(jsonPath("$.insuranceProvider").value("CTU Insurance"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.insuranceCost").value(350.00))
                .andExpect(jsonPath("$.policyNumber").value(12345))
                .andExpect(jsonPath("$.mechanic").value("John Doe"));

        verify(insuranceService, times(1)).create(any(Insurance.class));
    }

    @Test
    void testReadInsurance_Found() throws Exception {
        when(insuranceService.read(1)).thenReturn(testInsurance1);

        mockMvc.perform(get("/api/insurance/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insuranceID").value(1))
                .andExpect(jsonPath("$.insuranceProvider").value("CTU Insurance"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(insuranceService, times(1)).read(1);
    }

    @Test
    void testReadInsurance_NotFound() throws Exception {
        when(insuranceService.read(999)).thenReturn(null);

        mockMvc.perform(get("/api/insurance/read/999"))
                .andExpect(status().isNotFound());

        verify(insuranceService, times(1)).read(999);
    }

    @Test
    void testUpdateInsurance_Success() throws Exception {
        Insurance updatedInsurance = new Insurance.Builder()
                .copy(testInsurance1)
                .setInsuranceCost(400.00)
                .build();

        when(insuranceService.read(1)).thenReturn(testInsurance1);
        when(insuranceService.update(any(Insurance.class))).thenReturn(updatedInsurance);

        InsuranceDTO updateDTO = new InsuranceDTO.Builder()
                .setInsuranceID(1)
                .setInsuranceStartDate(testDate)
                .setInsuranceCost(400.00)
                .setInsuranceProvider("CTU Insurance")
                .setStatus("ACTIVE")
                .setPolicyNumber(12345L)
                .setMechanic("John Doe")
                .build();

        mockMvc.perform(put("/api/insurance/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insuranceID").value(1))
                .andExpect(jsonPath("$.insuranceCost").value(400.00));

        verify(insuranceService, times(1)).read(1);
        verify(insuranceService, times(1)).update(any(Insurance.class));
    }

    @Test
    void testUpdateInsurance_NotFound() throws Exception {
        when(insuranceService.read(999)).thenReturn(null);

        InsuranceDTO updateDTO = new InsuranceDTO.Builder()
                .setInsuranceID(999)
                .setInsuranceCost(400.00)
                .build();

        mockMvc.perform(put("/api/insurance/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());

        verify(insuranceService, times(1)).read(999);
        verify(insuranceService, never()).update(any());
    }

    @Test
    void testDeleteInsurance() throws Exception {
        doNothing().when(insuranceService).deleteInsurance(1);

        mockMvc.perform(delete("/api/insurance/delete/1"))
                .andExpect(status().isNoContent());

        verify(insuranceService, times(1)).deleteInsurance(1);
    }

    @Test
    void testGetAllInsurances() throws Exception {
        List<Insurance> insurances = Arrays.asList(testInsurance1, testInsurance2);
        when(insuranceService.getAll()).thenReturn(insurances);

        mockMvc.perform(get("/api/insurance/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].insuranceID").exists())
                .andExpect(jsonPath("$[1].insuranceID").exists());

        verify(insuranceService, times(1)).getAll();
    }

    @Test
    void testGetAllInsurances_Empty() throws Exception {
        when(insuranceService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/insurance/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(insuranceService, times(1)).getAll();
    }

    @Test
    void testCancelInsurance_Success() throws Exception {
        Insurance cancelledInsurance = new Insurance.Builder()
                .copy(testInsurance1)
                .setStatus("CANCELLED")
                .build();

        when(insuranceService.cancelInsurance(1)).thenReturn(cancelledInsurance);

        mockMvc.perform(put("/api/insurance/cancel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insuranceID").value(1))
                .andExpect(jsonPath("$.status").value("CANCELLED"));

        verify(insuranceService, times(1)).cancelInsurance(1);
    }

    @Test
    void testCancelInsurance_NotFound() throws Exception {
        when(insuranceService.cancelInsurance(999)).thenReturn(null);

        mockMvc.perform(put("/api/insurance/cancel/999"))
                .andExpect(status().isNotFound());

        verify(insuranceService, times(1)).cancelInsurance(999);
    }

    @Test
    void testAssignInsuranceToCar_Success() throws Exception {
        Insurance assignedInsurance = new Insurance.Builder()
                .copy(testInsurance1)
                .setCar(testCar)
                .build();

        when(insuranceService.assignInsuranceToCar(1, 1)).thenReturn(assignedInsurance);

        mockMvc.perform(put("/api/insurance/assign/1/to-car/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insuranceID").value(1))
                .andExpect(jsonPath("$.carID").value(1))
                .andExpect(jsonPath("$.carBrandModel").value("Toyota Fortuner"));

        verify(insuranceService, times(1)).assignInsuranceToCar(1, 1);
    }

    @Test
    void testAssignInsuranceToCar_NotFound() throws Exception {
        when(insuranceService.assignInsuranceToCar(999, 1)).thenReturn(null);

        mockMvc.perform(put("/api/insurance/assign/999/to-car/1"))
                .andExpect(status().isNotFound());

        verify(insuranceService, times(1)).assignInsuranceToCar(999, 1);
    }

    @Test
    void testGetInsurancesByStatus() throws Exception {
        List<Insurance> activeInsurances = Collections.singletonList(testInsurance1);
        when(insuranceService.getInsurancesByStatus("ACTIVE")).thenReturn(activeInsurances);

        mockMvc.perform(get("/api/insurance/by-status/ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));

        verify(insuranceService, times(1)).getInsurancesByStatus("ACTIVE");
    }

    @Test
    void testGetInsurancesByStatus_Empty() throws Exception {
        when(insuranceService.getInsurancesByStatus("EXPIRED")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/insurance/by-status/EXPIRED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(insuranceService, times(1)).getInsurancesByStatus("EXPIRED");
    }

    @Test
    void testGetInsurancesByProvider() throws Exception {
        List<Insurance> ctuInsurances = Collections.singletonList(testInsurance1);
        when(insuranceService.getInsurancesByProvider("CTU Insurance")).thenReturn(ctuInsurances);

        mockMvc.perform(get("/api/insurance/by-provider/CTU Insurance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].insuranceProvider").value("CTU Insurance"));

        verify(insuranceService, times(1)).getInsurancesByProvider("CTU Insurance");
    }

    @Test
    void testGetInsurancesByProvider_SpaceInName() throws Exception {
        List<Insurance> insurances = Collections.singletonList(testInsurance2);
        when(insuranceService.getInsurancesByProvider("SafeDrive")).thenReturn(insurances);

        mockMvc.perform(get("/api/insurance/by-provider/SafeDrive"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].insuranceProvider").value("SafeDrive"));

        verify(insuranceService, times(1)).getInsurancesByProvider("SafeDrive");
    }

    @Test
    void testGetActiveInsurances() throws Exception {
        List<Insurance> activeInsurances = Collections.singletonList(testInsurance1);
        when(insuranceService.getActiveInsurances()).thenReturn(activeInsurances);

        mockMvc.perform(get("/api/insurance/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));

        verify(insuranceService, times(1)).getActiveInsurances();
    }

    @Test
    void testGetActiveInsurances_MultipleResults() throws Exception {
        Insurance anotherActive = new Insurance.Builder()
                .setInsuranceID(3)
                .setInsuranceStartDate(testDate)
                .setInsuranceCost(500.00)
                .setInsuranceProvider("QuickInsure")
                .setStatus("ACTIVE")
                .setPolicyNumber(11111L)
                .setMechanic("Bob Builder")
                .build();

        List<Insurance> activeInsurances = Arrays.asList(testInsurance1, anotherActive);
        when(insuranceService.getActiveInsurances()).thenReturn(activeInsurances);

        mockMvc.perform(get("/api/insurance/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[1].status").value("ACTIVE"));

        verify(insuranceService, times(1)).getActiveInsurances();
    }

    @Test
    void testInvalidRequestBody() throws Exception {
        mockMvc.perform(post("/api/insurance/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEmptyRequestBody() throws Exception {
        mockMvc.perform(post("/api/insurance/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidPathVariable() throws Exception {
        mockMvc.perform(get("/api/insurance/read/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testContentType() throws Exception {
        when(insuranceService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/insurance/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testInsuranceWithCarRelationship() throws Exception {
        when(insuranceService.read(2)).thenReturn(testInsurance2);

        mockMvc.perform(get("/api/insurance/read/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insuranceID").value(2))
                .andExpect(jsonPath("$.carID").value(1))
                .andExpect(jsonPath("$.carBrandModel").value("Toyota Fortuner"));

        verify(insuranceService, times(1)).read(2);
    }

    @Test
    void testInsuranceWithoutCarRelationship() throws Exception {
        when(insuranceService.read(1)).thenReturn(testInsurance1);

        mockMvc.perform(get("/api/insurance/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insuranceID").value(1))
                .andExpect(jsonPath("$.carID").doesNotExist())
                .andExpect(jsonPath("$.carBrandModel").doesNotExist());

        verify(insuranceService, times(1)).read(1);
    }
}