package ch.juventus.carrental.service;

import ch.juventus.carrental.persistence.CarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Comparator;
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



    public boolean updateCarById(Long id, Car car) {

        try {

            carRepository.checkIfCarIdIsValid(id, "src/main/resources/cars.json");

            carRepository.replaceCarToJsonFile("src/main/resources/cars.json", id, car);

            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }

    }

    public boolean deleteCarById(Long id) {

        try {

            carRepository.checkIfCarIdIsValid(id, "src/main/resources/cars.json");

            carRepository.deleteCarFromJsonFile(id, "src/main/resources/cars.json");

            return true;

        }

        catch (IllegalArgumentException e) {
            return false;
        }

    }



    public String getFilteredCars(String filterQuery) {

        try {

            CarFilterDto filterDto = mapper.readValue(filterQuery, CarFilterDto.class);

            List<Car> carList = carRepository.getCarListFromJsonFile("src/main/resources/cars.json");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            List<Car> filteredList;

            filteredList = carList.stream().filter(car -> doesCarMatch(car, filterDto))
                    .collect(Collectors.toList());

            filteredList.sort(Comparator.comparing(Car::getPricePerDay));

            mapper.writeValue(out, filteredList);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public boolean doesCarMatch(Car car, CarFilterDto dto) {


        List<Rental> rentalList = car.getRentals();
        if (rentalList != null && !rentalList.isEmpty()) {


            if (dto.getEndDate() != null && (rentalList.stream().anyMatch
                    (rental -> (rental.getStartDate().datesUntil(rental.getEndDate()).toList().contains(dto.getEndDate()))))) {

                return false;
            }

            if (dto.getStartDate() != null && (rentalList.stream().anyMatch
                    (rental -> (rental.getStartDate().datesUntil(rental.getEndDate()).toList().contains(dto.getStartDate()))))) {

                return false;
            }


        }



        if (dto.getSearchQuery() != null && !(car.getName().toLowerCase().contains(dto.getSearchQuery().toLowerCase()))) {
            return false;
        }

        if (dto.getType() != null && !(dto.getType().contains(car.getType()))) {
            return false;
        }

        if (dto.getGearShift() != null && (dto.getGearShift() != car.getGearShift())) {
            return false;
        }

        if (dto.getMinPricePerDay() != null && !(dto.getMinPricePerDay() <= car.getPricePerDay())) {
            return false;
        }

        if (dto.getMaxPricePerDay() != null && !(dto.getMaxPricePerDay() >= car.getPricePerDay())) {
            return false;
        }


        if (dto.getSeats() != null && !(dto.getSeats().contains(car.getSeats()))) {
            return false;
        }

        if (dto.getAirCondition() != null && (dto.getAirCondition() != car.getAirCondition())) {
            return false;
        }

        return true;

    }



    public void createNewCar(Car newCar) {

        carRepository.writeCarToJsonFile(newCar, "src/main/resources/cars.json");

    }



    public boolean createNewRental(Long id, Rental rental) {


        List<Rental> rentalList = carRepository.readRentalsOfCar("src/main/resources/cars.json", id);


        if (rentalList.stream().anyMatch(r -> (r.getStartDate().datesUntil(r.getEndDate()).toList().contains(rental.getStartDate())))) {
            return false;
        }

        if (rentalList.stream().anyMatch(r -> (r.getStartDate().datesUntil(r.getEndDate()).toList().contains(rental.getEndDate())))) {
            return false;
        }

        if (rental.getStartDate()==null || rental.getEndDate()==null) {

            return false;

        }

        carRepository.writeRentalToCar("src/main/resources/cars.json", id, rental);
        return true;


    }



}
