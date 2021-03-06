package ch.juventus.carrental.persistence;

import ch.juventus.carrental.service.Car;
import ch.juventus.carrental.service.Rental;
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
import java.util.stream.IntStream;

/**
 * This class is the default database-class. Manipulation and readings of the database (json-file) is being made here.
 *
 */

@Repository
public class CarRentalDatabase implements CarRepository {


    private final ObjectMapper mapper = new ObjectMapper(); //<-- reuse instance of ObjectMapper!!
    public CarRentalDatabase() {
        mapper.registerModule(new JavaTimeModule());
    }


    /**
     * This method writes the car object to the json file. If there is no json file
     * at the given path yet, it creates one. The index of the car is calculated.
     *
     * @param car the car without id that has to be written into the json file
     * @param path the path to the json file
     */
    public void writeCarToJsonFile(Car car, String path) {

        try {
            Long carId = 0L;
            List<Car> carList;


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
                    car.getAirCondition());


            carList.add(nextCar);


            mapper.writeValue(new File(path), carList);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * This method writes a rental object to a json file with the given path.
     * It calculates the totalPrice of the rental object in this method.
     * It uses the replaceCarToJsonFile method to write the rental into the car.
     *
     *
     * @param path the path to the json file
     * @param id id of the car
     * @param rental rental object that has to be written
     * @return if the given id is valid it returns true, else false
     */

    public boolean writeRentalToCar(String path, Long id, Rental rental) {

        List<Car> carList = this.getCarListFromJsonFile(path);

        //check if id is valid
        if (carList.stream().anyMatch(car -> car.getId().equals(id))) {


            Car replaceCar = carList.get(IntStream.range(0, carList.size())
                    .filter(car -> carList.get(car).getId().equals(id))
                    .findFirst()
                    .getAsInt());



            //calc totalPrice = PricePerDay * (EndDate-StartDate)
            //TODO: maybe put in service class
            rental.setTotalPrice(replaceCar.getPricePerDay() * rental.getEndDate().compareTo(rental.getStartDate()));

            //if rentals list of this car is null, begin list
            if (replaceCar.getRentals()==null) {
               ArrayList<Rental> rentals = new ArrayList<>();
               rentals.add(rental);
               replaceCar.setRentals(rentals);
               this.replaceCarToJsonFile(path, id, replaceCar);
            }

            //if rentals list isn't empty just append to list
            else {
                ArrayList<Rental> rentals = replaceCar.getRentals();
                rentals.add(rental);
                this.replaceCarToJsonFile(path, id, replaceCar);

            }

            return true;


        }

        return false;

    }


    /**
     * This method reads out all rentals of the car with the given id.
     *
     *
     * @param path the path to the json file
     * @param id the id of the car
     * @return a list of every rental of the given car
     */

    public List<Rental> readRentalsOfCar(String path, Long id) {

        this.checkIfCarIdIsValid(id, path);

        List<Car> carList = this.getCarListFromJsonFile(path);

        Car readCar = carList.get(IntStream.range(0, carList.size())
                .filter(car -> carList.get(car).getId().equals(id))
                .findFirst()
                .getAsInt());

        if (readCar.getRentals()==null) {
            return new ArrayList<>();
        }

        return readCar.getRentals();


    }

    /**
     *
     * This method checks if the id of a car is valid in the given json file.
     *
     * @param id id of the car
     * @param path path to the json file
     *
     * @throws IllegalArgumentException if the method hasn't found any car with this id
     *
     */

    public void checkIfCarIdIsValid (Long id, String path) {

        List<Car> carList = this.getCarListFromJsonFile(path);

        if (carList.stream().anyMatch(car -> car.getId().equals(id))) {
            return;
        }

        throw new IllegalArgumentException("no valid id");


    }


    /**
     * This method replaces a car in the given json file.
     *
     * @param path path to the json file
     * @param id id of the car to be replaced
     * @param replaceCar car object to replace
     */

    public void replaceCarToJsonFile(String path, Long id, Car replaceCar) {

        try {
            this.checkIfCarIdIsValid(id, path);

            List<Car> carList = this.getCarListFromJsonFile(path);

            //replace car with id read of json file
            carList.set(IntStream.range(0, carList.size())
                    .filter(car -> carList.get(car).getId().equals(id))
                    .findFirst()
                    .getAsInt(), replaceCar);

            mapper.writeValue(new File(path), carList);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * This method deletes the car from the given json file
     *
     * @param id id of the car that has to be deleted
     * @param path path of the json file
     */

    public void deleteCarFromJsonFile(Long id, String path) {

        try {
            List<Car> carList = this.getCarListFromJsonFile(path);

            carList.remove(IntStream.range(0, carList.size())
                    .filter(car -> carList.get(car).getId().equals(id))
                    .findFirst()
                    .getAsInt());
            mapper.writeValue(new File(path), carList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * This method reads out the json file with the given path and returns the
     * full car list
     *
     * @param path path to the json file
     * @return List of all cars in the json file
     */

    public List<Car> getCarListFromJsonFile(String path) {

        try {
            Path p = Paths.get(path);
            if (Files.notExists(p)) {
              throw new IllegalArgumentException("path to json file must be valid / file doesnt exist yet");
            }

            List<Car> carList;
            carList = mapper.readValue(new File(path), new TypeReference<ArrayList<Car>>() {});

            return carList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }




}
