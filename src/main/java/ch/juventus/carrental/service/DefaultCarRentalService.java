package ch.juventus.carrental.service;

import ch.juventus.carrental.persistence.CarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DefaultCarRentalService implements CarRentalService {

    private final CarRepository carRepository;

    private final ObjectMapper mapper = new ObjectMapper(); //<-- reuse instance of ObjectMapper!!

    public DefaultCarRentalService(CarRepository carRepository) {
        this.carRepository = carRepository;
        mapper.registerModule(new JavaTimeModule());
    }

    public String getGreeting() {
        //all logic happens here
        String greeting = carRepository.loadGreeting();
        return greeting;
    }


    public String getCarList() {

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();


            List<Car> carList = carRepository.getCarListFromJsonFile("src/main/resources/cars.json");
            mapper.writeValue(out, carList);
            return out.toString();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public String getCarById(Long id) {
        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            List<Car> carList = carRepository.getCarListFromJsonFile("src/main/resources/cars.json");


            mapper.writeValue(out,

            carList.stream().filter(car -> id.equals(car.getId())).findFirst().orElse(null)
            );

            return out.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public String getFilteredCars(String filterQuery) {

        try {
            ObjectNode node = mapper.readValue(filterQuery, ObjectNode.class);

            if (node.has("startDate")
                    && node.has("endDate")
                    && node.has("searchQuery")
                    && node.has("type")
                    && node.has("gearShift")
                    && node.has("minPricePerDay")
                    && node.has("maxPricePerDay")
                    && node.has("seats")
                    && node.has("airCondition")) {


                List<Car> carList = carRepository.getCarListFromJsonFile("src/main/resources/cars.json");
                ByteArrayOutputStream out = new ByteArrayOutputStream();





                List<Car> filteredList = carList
                        .stream()
                        .filter(car -> car.getName().toLowerCase().contains(node.get("searchQuery").asText().toLowerCase())
                                //&&car.getType().equals(node.findValues("type"))
                                //TODO: all other Filters

                        )
                        .collect(Collectors.toList());



                mapper.writeValue(out, filteredList);
                return out.toString();
            }

            else {return "NO VALID FILTERQUERY";}



        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



    public void createNewCar(Car newCar) {

        carRepository.writeCarToJsonFile(newCar, "src/main/resources/cars.json");

    }




}
