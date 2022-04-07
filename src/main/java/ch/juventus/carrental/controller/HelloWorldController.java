package ch.juventus.carrental.controller;

import ch.juventus.carrental.service.HelloWorldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//read out parameter, prepare them and call correct class(service)
@RestController
@CrossOrigin("http://localhost:4200")
public class HelloWorldController {

    private final HelloWorldService helloWorldService;

    public HelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }


    @GetMapping("/api/v1/helloworld/{id}")

    //http://localhost:8080/api/v1/helloworld/124
    public ResponseEntity<String> helloWorldWithPathVariable(@PathVariable(value = "id") Long id) {
        String returnValue = "Hello world, id = " + id;
        return new ResponseEntity<String>(returnValue, HttpStatus.OK);
    }

    @GetMapping("/api/v1/helloworld")
    //http://localhost:8080/api/v1/helloworld?test=1
    public ResponseEntity<String> helloWorldWithRequestParam(@RequestParam(value = "test", required = false) String myParam) {
        String returnValue = helloWorldService.getGreeting();
        return new ResponseEntity<String>(returnValue, HttpStatus.OK);
    }



}
