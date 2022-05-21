package ch.juventus.carrental.persistence;

import ch.juventus.carrental.service.Car;

import java.util.List;

public interface CarRepository {

    String loadGreeting();
    void writeCarToJsonFile(Car car, String path);
    List<Car> getCarListFromJsonFile(String path);


}
