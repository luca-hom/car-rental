package ch.juventus.carrental.persistence;

import ch.juventus.carrental.service.Car;

public interface CarRepository {

    String loadGreeting();
    void writeCarToJsonFile(Car car, String path);


}
