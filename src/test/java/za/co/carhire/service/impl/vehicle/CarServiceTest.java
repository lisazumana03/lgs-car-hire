//package za.co.carhire.service.impl.vehicle;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import za.co.carhire.domain.vehicle.Car;
//import za.co.carhire.factory.vehicle.CarFactory;
//import za.co.carhire.repository.vehicle.ICarRepository;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
///*
//Imtiyaaz Waggie 219374759
//Date: 25/05/2025
//*/
//
//class CarServiceTest {
//
//    @Mock
//    private ICarRepository carRepository;
//
//    @InjectMocks
//    private CarService carService;
//
//    private Car testCar;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        testCar = CarFactory.createBasicCar(1, "Camry", "Toyota", 2023, 450.0);
//    }
//
//    @Test
//    void getCars() {
//        when(carRepository.findAll()).thenReturn(Arrays.asList(testCar));
//        assertEquals(1, carService.getCars().size());
//    }
//
//    @Test
//    void getCarsByBrand() {
//        when(carRepository.findByBrand("Toyota")).thenReturn(Optional.of(testCar));
//        assertEquals(1, carService.getCarsByBrand("Toyota").size());
//    }
//
//    @Test
//    void create() {
//        when(carRepository.save(testCar)).thenReturn(testCar);
//        assertEquals(testCar, carService.create(testCar));
//    }
//
//    @Test
//    void read() {
//        when(carRepository.findById(1)).thenReturn(Optional.of(testCar));
//        assertEquals(testCar, carService.read(1));
//    }
//
//    @Test
//    void update() {
//        when(carRepository.existsById(1)).thenReturn(true);
//        when(carRepository.save(testCar)).thenReturn(testCar);
//        assertEquals(testCar, carService.update(testCar));
//    }
//
//    @Test
//    void delete() {
//        carService.delete(1);
//        verify(carRepository).deleteById(1);
//    }
//}