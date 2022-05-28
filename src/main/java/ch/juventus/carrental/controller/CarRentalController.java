package ch.juventus.carrental.controller;

import ch.juventus.carrental.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;


//read out parameter, prepare them and call correct class(service)
@RestController
@CrossOrigin("http://localhost:4200") //disable if testing with YARC
public class CarRentalController {

    private final CarRentalService carRentalService;

    public CarRentalController(DefaultCarRentalService carRentalService) {
        this.carRentalService = carRentalService;
    }


    @GetMapping("/api/v1/car/{id}")
    //http://localhost:8080/api/v1/car/124
    public ResponseEntity<String> carWithPathVariable(@PathVariable(value = "id") Long id) {


        String returnValue = carRentalService.getCarById(id);
        if (Objects.equals(returnValue, "null")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "car with this id not found");
        }

        return new ResponseEntity<String>(returnValue, HttpStatus.OK);
    }


    @PutMapping( "/api/v1/car/{id}")
    public ResponseEntity<String> postCarWithPathVariable(@PathVariable(value = "id")Long id, @RequestBody Car replaceCar) {

        if (!replaceCar.getRentals().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rental list of this car cant be changed");
        }

        if (carRentalService.updateCarById(id, replaceCar)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping( "/api/v1/car/{id}")
    public ResponseEntity<String> deleteCarWithPathVariable(@PathVariable(value = "id")Long id) {

        if (carRentalService.deleteCarById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }



    @GetMapping("/api/v1/cars")
    //http://localhost:8080/api/v1/cars?filter=1
    public ResponseEntity<String> carRentalWithRequestParam(@RequestParam(value = "filter", required = false) String filter) {
        String returnValue;

        if (filter != null) {
            returnValue = carRentalService.getFilteredCars(filter);

        }

        else {
            returnValue = carRentalService.getCarList();
        }


        return new ResponseEntity<String>(returnValue, HttpStatus.OK);
    }


    @PostMapping("/api/v1/car")
    public void registerNewCar(@RequestBody Car newCar) {

        carRentalService.createNewCar(newCar);

    }


    @PostMapping("api/v1/car/{id}/rental")
    public ResponseEntity<Boolean> registerNewRental(@PathVariable (value = "id") Long id, @RequestBody Rental newRental) {


        boolean status = carRentalService.createNewRental(id, newRental);

        if (status) {

            return new ResponseEntity<Boolean>(HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
