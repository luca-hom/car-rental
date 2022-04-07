package ch.juventus.carrental.service;

import ch.juventus.carrental.persistence.HelloWorldDatabase;
import org.springframework.stereotype.Service;


@Service
public class HelloWorldService {

    private final HelloWorldDatabase helloWorldDatabase;

    public HelloWorldService(HelloWorldDatabase helloWorldDatabase) {
        this.helloWorldDatabase = helloWorldDatabase;
    }

    public String getGreeting() {
        //all logic happens here
        String greeting = helloWorldDatabase.loadGreeting();
        return greeting;
    }

}
