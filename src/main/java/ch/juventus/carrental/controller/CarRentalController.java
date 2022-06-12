package ch.juventus.carrental.controller;

import ch.juventus.carrental.service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    @Operation(summary = "Get a car by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the car",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Car.class))}),
            @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content)})
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

    @Operation(summary = "Replace a car by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the car",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class))}),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Rental list of car to replace has to be empty",
                    content = @Content)})
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

    @Operation(summary = "Delete a car by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the car and deleted it",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Car not found",
                    content = @Content)})
    @DeleteMapping( "/api/v1/car/{id}")
    public ResponseEntity<String> deleteCarWithPathVariable(@PathVariable(value = "id")Long id) {
        logger.info("DELETE Request of car with id {}.", id);

        if (carRentalService.deleteCarById(id)) {
            logger.info("Successful DELETE Request of car with id {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @Operation(summary = "Get all cars, if filter is applied filter the list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the car list",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema (implementation = Car.class)))})})
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

    @Operation(summary = "Add a new car to the list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the car",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Car.class))})})
    @PostMapping("/api/v1/car")
    public void registerNewCar(@RequestBody Car newCar) {
        logger.info("POST Request of a new car");

        carRentalService.createNewCar(newCar);
        logger.info("Successful POST Request of a new car");
    }

    @Operation(summary = "Add a new rental to a car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the Rental",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Rental.class))}),
            @ApiResponse(responseCode = "400", description = "Car with this id not found",
                    content = @Content)})
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
