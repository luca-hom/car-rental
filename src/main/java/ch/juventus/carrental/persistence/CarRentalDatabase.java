package ch.juventus.carrental.persistence;

import ch.juventus.carrental.service.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRentalDatabase implements CarRepository {

    public String loadGreeting() {
        return "Hello from Car-Database";
    }

    @Override
    public void writeCarToJsonFile(Car car, String path) {

        try {
            Long carId = 0L;
            List<Car> carList;
            ObjectMapper mapper = new ObjectMapper();
            //adds LocalDate to jackson
            mapper.registerModule(new JavaTimeModule());


            //creates File if it doesn't exist yet
            Path p = Paths.get(path);
            if (Files.notExists(p)) {
                Files.createFile(p);
                Files.writeString(p, "[]");
            }
            carList = mapper.readValue(new File(path), new TypeReference<ArrayList<Car>>() {});


            //if no Car is in List yet, index of car = 0
            if (carList.size() == 0) {
                carId = 0L;
            }
            else {
                carId = (carList.get(carList.size()-1).getId() + 1);
            }

            Car nextCar = new Car(
                    carId,
                    car.getName(),
                    car.getType(),
                    car.getGearShift(),
                    car.getSeats(),
                    car.getPricePerDay(),
                    car.getAirCondition(),
                    car.getRentals());

            carList.add(nextCar);


            mapper.writeValue(new File(path), carList);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



}
