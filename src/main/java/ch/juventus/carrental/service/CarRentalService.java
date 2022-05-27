package ch.juventus.carrental.service;

import java.io.IOException;

public interface CarRentalService {
    String getGreeting();
    String getCarList();
    String getCarById(Long id);
    boolean updateCarById(Long id, Car car);
    boolean deleteCarById(Long id);
    String getFilteredCars(String filterQuery);
    void createNewCar(Car newCar);

    boolean createNewRental(Long id, Rental rental);




}
