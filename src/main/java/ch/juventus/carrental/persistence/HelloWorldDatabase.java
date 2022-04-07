package ch.juventus.carrental.persistence;

import org.springframework.stereotype.Repository;

@Repository
public class HelloWorldDatabase {

    public String loadGreeting() {
        return "HelloWorld from Database";
    }

    //public Car getCarById(Long id) {
    //    new Car(....);
    //}

}
