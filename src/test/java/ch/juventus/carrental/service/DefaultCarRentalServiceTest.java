package ch.juventus.carrental.service;

import ch.juventus.carrental.persistence.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.GenericArrayType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultCarRentalServiceTest {

    @Mock
    private CarRepository carRepository;
    private AutoCloseable autoCloseable;
    private DefaultCarRentalService defaultCarRentalService;

    @BeforeEach
    void init () {
        autoCloseable = MockitoAnnotations.openMocks(this);
        defaultCarRentalService = new DefaultCarRentalService(carRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testGetCarList() {

        assertEquals("[]", defaultCarRentalService.getCarList());

    }

    @Test
    void testGetCarById() {

        assertEquals("null", defaultCarRentalService.getCarById(0L));

    }

    @Test
    void testGetCarListFiltered() {

        String testFilterQuery = "{\"startDate\":null,\"endDate\":null,\"searchQuery\":\"test\",\"type\":null,\"gearShift\":null,\"minPricePerDay\":null,\"maxPricePerDay\":null,\"seats\":null,\"airCondition\":true}";

        assertEquals("[]", defaultCarRentalService.getFilteredCars(testFilterQuery));

    }

    @Test
    void testUpdateCar() {

        Car testCar = new Car("testCar", Type.CABRIO, GearShift.AUTOMATIC, 4, 100D, true);

        //assertTrue(defaultCarRentalService.updateCarById(0L, testCar));
    }









}