package ch.juventus.carrental.controller;

import ch.juventus.carrental.service.Car;
import ch.juventus.carrental.service.DefaultCarRentalService;
import ch.juventus.carrental.service.Rental;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;


//read out parameter, prepare them and call correct class(service)
@RestController
@CrossOrigin("http://localhost:4200") //disable if testing with YARC
public class CarRentalController {

    private final DefaultCarRentalService defaultCarRentalService;

    public CarRentalController(DefaultCarRentalService defaultCarRentalService) {
        this.defaultCarRentalService = defaultCarRentalService;
    }


    @GetMapping("/api/v1/car/{id}")
    //http://localhost:8080/api/v1/car/124
    public ResponseEntity<String> carWithPathVariable(@PathVariable(value = "id") Long id) {


        String returnValue = defaultCarRentalService.getCarById(id);
        if (Objects.equals(returnValue, "null")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "car with this id not found");
        }

        return new ResponseEntity<String>(returnValue, HttpStatus.OK);
    }

    @GetMapping("/api/v1/cars")
    //http://localhost:8080/api/v1/cars?filter=1
    public ResponseEntity<String> carRentalWithRequestParam(@RequestParam(value = "filter", required = false) String filter) {
        String returnValue = "test";

        if (filter != null) {
            returnValue = defaultCarRentalService.getFilteredCars(filter);

            if (Objects.equals(returnValue, "NO VALID FILTERQUERY")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }

        else {
            returnValue = defaultCarRentalService.getCarList();
        }


        return new ResponseEntity<String>(returnValue, HttpStatus.OK);
    }


    @PostMapping("/api/v1/car")
    public void registerNewCar(@RequestBody Car newCar) {

        defaultCarRentalService.createNewCar(newCar);

    }


    @PostMapping("api/v1/car/{id}/rental")
    public ResponseEntity<Boolean> registerNewRental(@PathVariable (value = "id") Long id, @RequestBody Rental newRental) {


        boolean status = defaultCarRentalService.createNewRental(id, newRental);

        if (status) {

            return new ResponseEntity<Boolean>(HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
