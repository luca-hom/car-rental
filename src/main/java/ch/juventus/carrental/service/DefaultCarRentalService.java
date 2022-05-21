package ch.juventus.carrental.service;

import ch.juventus.carrental.persistence.CarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


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


    public String getCarList() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            ByteArrayOutputStream out = new ByteArrayOutputStream();


            List<Car> carList = carRepository.getCarListFromJsonFile("src/main/resources/cars.json");
            System.out.println(carList);
            mapper.writeValue(out, carList);
            return out.toString();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void createNewCar(Car newCar) {

        carRepository.writeCarToJsonFile(newCar, "src/main/resources/cars.json");

    }




}
