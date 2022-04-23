package ch.juventus.carrental.persistence;

import org.springframework.stereotype.Repository;

@Repository
public class CarRentalDatabase implements CarRepository {

    public String loadGreeting() {
        return "Hello from Car-Database";
    }

    //public Car getCarById(Long id) {
    //    new Car(....);
    //}


}
