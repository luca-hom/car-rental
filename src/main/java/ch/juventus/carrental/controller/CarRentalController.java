package ch.juventus.carrental.controller;

import ch.juventus.carrental.service.DefaultCarRentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//read out parameter, prepare them and call correct class(service)
@RestController
@CrossOrigin("http://localhost:4200")
public class CarRentalController {

    private final DefaultCarRentalService defaultCarRentalService;

    public CarRentalController(DefaultCarRentalService defaultCarRentalService) {
        this.defaultCarRentalService = defaultCarRentalService;
    }


    @GetMapping("/api/v1/car/{id}")

    //http://localhost:8080/api/v1/car/124
    public ResponseEntity<String> carWithPathVariable(@PathVariable(value = "id") Long id) {
        String returnValue = "Car " + id;
        return new ResponseEntity<String>(returnValue, HttpStatus.OK);
    }

    @GetMapping("/api/v1/cars")
    //http://localhost:8080/api/v1/helloworld?test=1
    public ResponseEntity<String> carRentalWithRequestParam(@RequestParam(value = "test", required = false) String myParam) {
        String returnValue = defaultCarRentalService.getGreeting();
        return new ResponseEntity<String>(returnValue, HttpStatus.OK);
    }









}
