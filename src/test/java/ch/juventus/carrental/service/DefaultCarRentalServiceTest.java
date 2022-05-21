package ch.juventus.carrental.service;

import ch.juventus.carrental.persistence.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

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
    void getCarList() {

        System.out.println(defaultCarRentalService.getCarList());

        //TODO write actual Test lol



    }
}