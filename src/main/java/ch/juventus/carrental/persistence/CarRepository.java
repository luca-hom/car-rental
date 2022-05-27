package ch.juventus.carrental.persistence;

import ch.juventus.carrental.service.Car;
import ch.juventus.carrental.service.Rental;

import java.util.List;

public interface CarRepository {

    String loadGreeting();
    void writeCarToJsonFile(Car car, String path);
    List<Car> getCarListFromJsonFile(String path);
    boolean writeRentalToCar(String path, Long id, Rental rental);
    List<Rental> readRentalsOfCar(String path, Long id);

    void replaceCarToJsonFile(String path, Long id, Car replaceCar);

    void checkIfCarIdIsValid (Long id, String path);

    void deleteCarFromJsonFile(Long id, String path);


}
