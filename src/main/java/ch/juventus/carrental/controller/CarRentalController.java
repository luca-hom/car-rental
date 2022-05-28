package ch.juventus.carrental.controller;

import ch.juventus.carrental.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

/**
 * This class acts as the controller class for the REST API
 */
//read out parameter, prepare them and call correct class(service)
@RestController
@CrossOrigin("http://localhost:4200") //disable if testing with YARC
public class CarRentalController {

    private final CarRentalService carRentalService;
    final Logger logger = LoggerFactory.getLogger(CarRentalController.class);

    public CarRentalController(DefaultCarRentalService carRentalService) {
        this.carRentalService = carRentalService;
    }


    @GetMapping("/api/v1/car/{id}")
    //http://localhost:8080/api/v1/car/124
    public ResponseEntity<String> carWithPathVariable(@PathVariable(value = "id") Long id) {
        logger.info("GET Request of car with id: {}.", id);


        String returnValue = carRentalService.getCarById(id);
        if (Objects.equals(returnValue, "null")) {

            logger.warn("car with id {} not found.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "car with this id not found");
        }

        logger.info("Successful GET Request of car with id: {}", id);
        return new ResponseEntity<String>(returnValue, HttpStatus.OK);
    }


    @PutMapping( "/api/v1/car/{id}")
    public ResponseEntity<String> postCarWithPathVariable(@PathVariable(value = "id")Long id, @RequestBody Car replaceCar) {
        logger.info("PUT Request of car with id {}.", id);

        if (!replaceCar.getRentals().isEmpty()) {
            logger.warn("rental list of car to change has be be empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rental list of this car cant be changed");
        }

        if (carRentalService.updateCarById(id, replaceCar)) {
            logger.info("Successful PUT Request of car with id {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping( "/api/v1/car/{id}")
    public ResponseEntity<String> deleteCarWithPathVariable(@PathVariable(value = "id")Long id) {
        logger.info("DELETE Request of car with id {}.", id);

        if (carRentalService.deleteCarById(id)) {
            logger.info("Successful DELETE Request of car with id {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }



    @GetMapping("/api/v1/cars")
    //http://localhost:8080/api/v1/cars?filter=1
    public ResponseEntity<String> carRentalWithRequestParam(@RequestParam(value = "filter", required = false) String filter) {

        String returnValue;

        if (filter != null) {
            logger.info("GET Request of filtered car-list");
            returnValue = carRentalService.getFilteredCars(filter);
        }

        else {
            logger.info("GET Request of car-list");
            returnValue = carRentalService.getCarList();
        }


        logger.info("Successful GET Request of car-list");
        return new ResponseEntity<String>(returnValue, HttpStatus.OK);
    }


    @PostMapping("/api/v1/car")
    public void registerNewCar(@RequestBody Car newCar) {
        logger.info("POST Request of a new car");

        carRentalService.createNewCar(newCar);
        logger.info("Successful POST Request of a new car");

    }


    @PostMapping("api/v1/car/{id}/rental")
    public ResponseEntity<Boolean> registerNewRental(@PathVariable (value = "id") Long id, @RequestBody Rental newRental) {
        logger.info("POST Request of a new rental for car with id {}", id);


        boolean status = carRentalService.createNewRental(id, newRental);

        if (status) {
            logger.info("Successful POST Request of a new rental");
            return new ResponseEntity<Boolean>(HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
