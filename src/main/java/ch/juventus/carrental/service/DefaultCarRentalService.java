package ch.juventus.carrental.service;

import ch.juventus.carrental.persistence.CarRepository;
import org.springframework.stereotype.Service;


@Service
public class DefaultCarRentalService implements CarRentalService {

    private final CarRepository carRepository;

    public DefaultCarRentalService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public String getGreeting() {
        //all logic happens here
        String greeting = carRepository.loadGreeting();
        return greeting;
    }





}
